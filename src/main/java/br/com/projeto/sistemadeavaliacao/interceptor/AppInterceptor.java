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
		if (uri.endsWith("loginForm") || uri.endsWith("efetuaLogin") || uri.endsWith(".css") ||  uri.endsWith(".js") || uri.endsWith(".png")
				|| uri.endsWith(".gif") || uri.endsWith(".jpg")) {
			return true;
		}
		
		if (handler instanceof HandlerMethod) {
			HandlerMethod metodo = (HandlerMethod) handler;
			if (uri.startsWith("/")) {

				if (metodo.getMethodAnnotation(SecretariaAnnotation.class) != null) {

					if (session.getAttribute("nivel") == TipoUsuario.SECRETARIA) {

						return true;
					}

					if (TipoUsuario.SECRETARIA == null) {
						response.sendError(HttpStatus.UNAUTHORIZED.value());
					} else {
						response.sendError(HttpStatus.FORBIDDEN.value());
					}
					return false;

				} else if (metodo.getMethodAnnotation(DocenteAnnotation.class) != null) {

					if (session.getAttribute("nivel") == TipoUsuario.DOCENCIA) {

						return true;
					}

					if (TipoUsuario.DOCENCIA == null) {
						response.sendError(HttpStatus.UNAUTHORIZED.value());
					} else {
						response.sendError(HttpStatus.FORBIDDEN.value());
					}
					return false;

				} else if (metodo.getMethodAnnotation(DiretorAnnotation.class) != null) {

					if (session.getAttribute("nivel") == TipoUsuario.DIRETOR) {

						return true;
					}

					if (TipoUsuario.DIRETOR == null) {
						response.sendError(HttpStatus.UNAUTHORIZED.value());
					} else {
						response.sendError(HttpStatus.FORBIDDEN.value());
					}
					return false;

				}

				return true;

			} else {

				if (metodo.getMethodAnnotation(PublicoAnnotation.class) != null) {
					return true;
				}
				if (session.getAttribute("usuarioLogado") != null) {
					return true;
				}
				response.sendRedirect("/");
				return false;

			}

		}
		return false;

	}
	
}
