$(document).ready(function(){

    $('#select-list').on('change', function(){

        var selectValor = $(this).val();
        $('#daddy').childrens('div').hide();
    })

})