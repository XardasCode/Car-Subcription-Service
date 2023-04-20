///////////////year//////////////////

jQuery(($) => {
    $('.catalog__year').on('click', '.catalog__year-head', function () {
        if ($(this).hasClass('open')) {
            $(this).removeClass('open');
            $(this).next().fadeOut();
        } else {
            $('.catalog__year-head').removeClass('open');
            $('.catalog__year-list').fadeOut();
            $(this).addClass('open');
            $(this).next().fadeIn();
        }
    });

    $('.catalog__year').on('click', '.catalog__year-item', function () {
        $('.catalog__year-head').removeClass('open');
        $(this).parent().fadeOut();
        $(this).parent().prev().text($(this).text());
        $(this).parent().prev().prev().val($(this).text());
    });

    $(document).click(function (e) {
        if (!$(e.target).closest('.catalog__year').length) {
            $('.catalog__year-head').removeClass('open');
            $('.catalog__year-list').fadeOut();
        }
    });
});

///////////////fuel//////////////////

jQuery(($) => {
    $('.catalog__fuel').on('click', '.catalog__fuel-head', function () {
        if ($(this).hasClass('open')) {
            $(this).removeClass('open');
            $(this).next().fadeOut();
        } else {
            $('.catalog__fuel-head').removeClass('open');
            $('.catalog__fuel-list').fadeOut();
            $(this).addClass('open');
            $(this).next().fadeIn();
        }
    });

    $('.catalog__fuel').on('click', '.catalog__fuel-item', function () {
        $('.catalog__fuel-head').removeClass('open');
        $(this).parent().fadeOut();
        $(this).parent().prev().text($(this).text());
        $(this).parent().prev().prev().val($(this).text());
    });

    $(document).click(function (e) {
        if (!$(e.target).closest('.catalog__fuel').length) {
            $('.catalog__fuel-head').removeClass('open');
            $('.catalog__fuel-list').fadeOut();
        }
    });
});

///////////////color//////////////////

jQuery(($) => {
    $('.catalog__color').on('click', '.catalog__color-head', function () {
        if ($(this).hasClass('open')) {
            $(this).removeClass('open');
            $(this).next().fadeOut();
        } else {
            $('.catalog__color-head').removeClass('open');
            $('.catalog__color-list').fadeOut();
            $(this).addClass('open');
            $(this).next().fadeIn();
        }
    });

    $('.catalog__color').on('click', '.catalog__color-item', function () {
        $('.catalog__color-head').removeClass('open');
        $(this).parent().fadeOut();
        $(this).parent().prev().text($(this).text());
        $(this).parent().prev().prev().val($(this).text());
    });

    $(document).click(function (e) {
        if (!$(e.target).closest('.catalog__color').length) {
            $('.catalog__color-head').removeClass('open');
            $('.catalog__color-list').fadeOut();
        }
    });
});

///////////////price//////////////////

jQuery(($) => {
    $('.catalog__price').on('click', '.catalog__price-head', function () {
        if ($(this).hasClass('open')) {
            $(this).removeClass('open');
            $(this).next().fadeOut();
        } else {
            $('.catalog__price-head').removeClass('open');
            $('.catalog__price-list').fadeOut();
            $(this).addClass('open');
            $(this).next().fadeIn();
        }
    });

    $('.catalog__price').on('click', '.catalog__price-item', function () {
        $('.catalog__price-head').removeClass('open');
        $(this).parent().fadeOut();
        $(this).parent().prev().text($(this).text());
        $(this).parent().prev().prev().val($(this).text());
    });

    $(document).click(function (e) {
        if (!$(e.target).closest('.catalog__price').length) {
            $('.catalog__price-head').removeClass('open');
            $('.catalog__price-list').fadeOut();
        }
    });
});

///////////////brand//////////////////

jQuery(($) => {
    $('.catalog__brand').on('click', '.catalog__brand-head', function () {
        if ($(this).hasClass('open')) {
            $(this).removeClass('open');
            $(this).next().fadeOut();
        } else {
            $('.catalog__brand-head').removeClass('open');
            $('.catalog__brand-list').fadeOut();
            $(this).addClass('open');
            $(this).next().fadeIn();
        }
    });

    $('.catalog__brand').on('click', '.catalog__brand-item', function () {
        $('.catalog__brand-head').removeClass('open');
        $(this).parent().fadeOut();
        $(this).parent().prev().text($(this).text());
        $(this).parent().prev().prev().val($(this).text());
    });

    $(document).click(function (e) {
        if (!$(e.target).closest('.catalog__brand').length) {
            $('.catalog__brand-head').removeClass('open');
            $('.catalog__brand-list').fadeOut();
        }
    });
});

///////////////search//////////////////

