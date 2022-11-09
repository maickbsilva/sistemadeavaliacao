$('#exb').on('click', function(){
    $('menu').slideToggle('slow');
});

$('.slc').each(function(){
    $(this).on('click', function(){
        $('slc').slideToggle('slow');
    });
});