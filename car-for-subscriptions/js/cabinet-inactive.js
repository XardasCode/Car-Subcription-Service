/////Auto/////

jQuery(($) => {
    $('.inactive__auto').on('click', '.inactive__head-auto', function () {
        if ($(this).hasClass('open')) {
            $(this).removeClass('open');
            $(this).next().fadeOut();
        } else {
            $('.inactive__head-auto').removeClass('open');
            $('.inactive__list-auto').fadeOut();
            $(this).addClass('open');
            $(this).next().fadeIn();
        }
    });

    $('.inactive__auto').on('click', '.inactive__item-auto', function () {
        $('.inactive__head-auto').removeClass('open');
        $(this).parent().fadeOut();
        $(this).parent().prev().text($(this).text());
        $(this).parent().prev().prev().val($(this).text());
    });

    $(document).click(function (e) {
        if (!$(e.target).closest('.inactive__auto').length) {
            $('.inactive__head-auto').removeClass('open');
            $('.inactive__list-auto').fadeOut();
        }
    });
});

/////Term/////

jQuery(($) => {
    $('.inactive__term').on('click', '.inactive__head-term', function () {
        if ($(this).hasClass('open')) {
            $(this).removeClass('open');
            $(this).next().fadeOut();
        } else {
            $('.inactive__head-term').removeClass('open');
            $('.inactive__list-term').fadeOut();
            $(this).addClass('open');
            $(this).next().fadeIn();
        }
    });

    $('.inactive__term').on('click', '.inactive__item-term', function () {
        $('.inactive__head-term').removeClass('open');
        $(this).parent().fadeOut();
        $(this).parent().prev().text($(this).text());
        $(this).parent().prev().prev().val($(this).text());
    });

    $(document).click(function (e) {
        if (!$(e.target).closest('.inactive__term').length) {
            $('.inactive__head-term').removeClass('open');
            $('.inactive__list-term').fadeOut();
        }
    });
});

/////Color/////

jQuery(($) => {
    $('.inactive__color').on('click', '.inactive__head-color', function () {
        if ($(this).hasClass('open')) {
            $(this).removeClass('open');
            $(this).next().fadeOut();
        } else {
            $('.inactive__head-color').removeClass('open');
            $('.inactive__list-color').fadeOut();
            $(this).addClass('open');
            $(this).next().fadeIn();
        }
    });

    $('.inactive__color').on('click', '.inactive__item-color', function () {
        $('.inactive__head-color').removeClass('open');
        $(this).parent().fadeOut();
        $(this).parent().prev().text($(this).text());
        $(this).parent().prev().prev().val($(this).text());
    });

    $(document).click(function (e) {
        if (!$(e.target).closest('.inactive__color').length) {
            $('.inactive__head-color').removeClass('open');
            $('.inactive__list-color').fadeOut();
        }
    });
});


/////Validate number/////

const validator = new JustValidate('#form');

validator.addField('#passport', [
  {
    rule: 'minNumber',
    value: 13,
  },
  {
    rule: 'maxNumber',
    value: 14,
    errorMassage: "Поле має містити максимум 14 символів",
  },
  {
    rule: 'number',
    errorMassage: "Значення повинне бути числом",
  },
  {
    rule: 'required',
    errorMassage: "Заповніть обов'язкове поле",
  },
]);

const validator2 = new JustValidate2('#form');

validator2.addField('#ipn', [
  {
    rule: 'minNumber',
    value: 10,
  },
  {
    rule: 'maxNumber',
    value: 10,
    errorMassage: "Поле має містити максимум 10 символів",
  },
]);
