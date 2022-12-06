package br.com.projeto.sistemadeavaliacao.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.com.projeto.sistemadeavaliacao.annotation.DiretorAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.DocenteAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.PublicoAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.SecretariaAnnotation;
import br.com.projeto.sistemadeavaliacao.model.TipoUsuario;

@Component
public class AppInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String uri = request.getRequestURI();

        HttpSession session = request.getSession();

        if (uri.startsWith("/error")) {
            System.out.println("URI");
            return true;
        }

        /* if para Funcionalidade geral com interceptor */
        if (uri.endsWith("loginForm") || uri.endsWith("efetuaLogin") || uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png")
                || uri.endsWith(".gif") || uri.endsWith(".jpg")) {
            return true;
        }

        if (handler instanceof HandlerMethod) {
            HandlerMethod metodo = (HandlerMethod) handler;

            if (metodo.getMethodAnnotation(DiretorAnnotation.class) != null && session.getAttribute("diretoriaLogado") != null) {
                return true;
            }

            if (metodo.getMethodAnnotation(SecretariaAnnotation.class) != null && session.getAttribute("secretariaLogado") != null) {
                return true;
            }

            if (metodo.getMethodAnnotation(DocenteAnnotation.class) != null && session.getAttribute("docenciaLogado") != null) {
                return true;
            }

            if (metodo.getMethodAnnotation(PublicoAnnotation.class) != null) {
                return true;
            }

            // redireciona para a pagina inicial
            response.sendRedirect("/acessoNegado");
            return false;

        }

        return true;

    }

}
