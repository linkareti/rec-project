/*!
 * Application
 */
var Application = (function() {

	return {

		disableReturnOnFields : function(e) {
			$(document).on("keydown", "input", function(event) {
				if (event.keyCode == 13) {
					event.preventDefault();
					return false;
				}
			});
		},

		toggleClass : function(element) {
			var icon = $(element).find('i.fa');
			if (icon) {
				var isExpanded = icon.attr("class").indexOf("fa-chevron-up") != -1;
				if (isExpanded) {
					icon.toggleClass('fa-chevron-up fa-chevron-down', 200);
				} else {
					icon.toggleClass('fa-chevron-down fa-chevron-up', 200);
				}
			}
		},
	};
})();

PrimeFaces.locales['pt'] = {
	closeText : 'Fechar',
	prevText : 'Anterior',
	nextText : 'Seguinte',
	monthNames : [ 'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
			'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro' ],
	monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago',
			'Set', 'Out', 'Nov', 'Dez' ],
	dayNames : [ 'Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta',
			'Sábado' ],
	dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb' ],
	dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S', 'S' ],
	weekHeader : 'Semana',
	firstDay : 1,
	isRTL : false,
	showMonthAfterYear : false,
	yearSuffix : '',
	timeOnlyTitle : 'Só Horas',
	timeText : 'Tempo',
	hourText : 'Hora',
	minuteText : 'Minuto',
	secondText : 'Segundo',
	currentText : 'Hoje',
	ampm : false,
	month : 'Mês',
	week : 'Semana',
	day : 'Dia',
	allDayText : 'Dia completo',
	messages : { // optional for Client Side Validation
		'javax.faces.component.UIInput.CONVERSION' : 'Ocorreu um erro de conversão',
		'javax.faces.component.UIInput.REQUIRED' : 'Preenchimento obrigatório',
		'javax.faces.component.UIInput.UPDATE' : 'Ocorreu um erro ao processar a informação submetida',
		'javax.faces.component.UISelectOne.INVALID' : 'Valor não é válido',
		'javax.faces.component.UISelectMany.INVALID' : 'Valor não é válido',
		'javax.faces.converter.BigDecimalConverter.DECIMAL' : '"{0}" deverá ser um nº decimal',
		'javax.faces.converter.BigDecimalConverter.DECIMAL_detail' : '"{0}" deve ser um nº decimal consistindo de zero ou mais digitos e que pode ser seguido por um ponto decimal.  Exemplo\: {1}',
		'javax.faces.converter.BigIntegerConverter.BIGINTEGER' : '"{0}" deverá ser um nº consistindo de um ou mais digitos',
		'javax.faces.converter.BigIntegerConverter.BIGINTEGER_detail' : '"{0}" deverá ser um nº consistindo de um ou mais digitos. Exemplo\: {1}',
		'javax.faces.converter.BooleanConverter.BOOLEAN' : '{1}: "{0}" deve ser "verdadeiro" ou "falso"',
		'javax.faces.converter.BooleanConverter.BOOLEAN_detail' : '{1}: "{0}" deve ser "verdadeiro" ou "falso". Outro valor diferente de "verdadeiro" será interpretado como "falso"',
		'javax.faces.converter.ByteConverter.BYTE' : '"{0}" deve ser um nº entre 0 e 255',
		'javax.faces.converter.ByteConverter.BYTE_detail' : '"{0}" deve ser um nº entre 0 e 255.  Exemplo\: {1}',
		'javax.faces.converter.CharacterConverter.CHARACTER' : '"{0}" deve ser um caracter válido',
		'javax.faces.converter.CharacterConverter.CHARACTER_detail' : '"{0}" deve ser um caracter ASCII válido',
		'javax.faces.converter.DateTimeConverter.DATE' : '"{0}" não  interpretado como data',
		'javax.faces.converter.DateTimeConverter.DATE_detail' : 'Data inválida',
		'javax.faces.converter.DateTimeConverter.TIME' : '"{0}" não foi interpretado como tempo',
		'javax.faces.converter.DateTimeConverter.TIME_detail' : '"{0}" não foi interpretado como tempo. Exemplo: {1}',
		'javax.faces.converter.DateTimeConverter.DATETIME' : '"{0}" não foi interpretado como data e hora',
		'javax.faces.converter.DateTimeConverter.DATETIME_detail' : '"{0}" não foi interpretado como data e hora. Exemplo: {1}',
		'javax.faces.converter.DateTimeConverter.PATTERN_TYPE' : '{1}: um "padrão" ou "tipo" deve ser especificado para converter o valor "{0}"',
		'javax.faces.converter.DoubleConverter.DOUBLE' : '"{0}" tem de ser um número constituído por um ou mais digitos',
		'javax.faces.converter.DoubleConverter.DOUBLE_detail' : '"{0}" deve ser um nº entre 4.9E-324 e 1.7976931348623157E308  Exemplo\: {1}',
		'javax.faces.converter.EnumConverter.ENUM' : '"{0}" tem de ser convertível para um enumerado',
		'javax.faces.converter.EnumConverter.ENUM_detail' : '"{0}" "{0}" tem de ser convertível para um enumerado a partir da constante "{1}".',
		'javax.faces.converter.EnumConverter.ENUM_NO_CLASS' : '{1}: "{0}" must be convertible to an enum from the enum, but no enum class provided.',
		'javax.faces.converter.EnumConverter.ENUM_NO_CLASS_detail' : '{1}: "{0}" "{0}" tem de ser convertível para um enumerado mas nenhuma classe de enumerado foi indicada',
		'javax.faces.converter.FloatConverter.FLOAT' : '"{0}" tem de ser um número constituído por um ou mais digitos',
		'javax.faces.converter.FloatConverter.FLOAT_detail' : '"{0}" deve ser um nº entre 1.4E-45 e 3.4028235E38  Exemplo\: {1}',
		'javax.faces.converter.IntegerConverter.INTEGER' : '"{0}" must be a number consisting of one or more digits.',
		'javax.faces.converter.IntegerConverter.INTEGER_detail' : '"{0}" deve ser um valor numérico. Exemplo\: {1}',
		'javax.faces.converter.LongConverter.LONG' : '"{0}" must be a number consisting of one or more digits.',
		'javax.faces.converter.LongConverter.LONG_detail' : '"{0}" deve ser um valor numérico. Exemplo\: {1}',
		'javax.faces.converter.NumberConverter.CURRENCY' : '"{0}" could not be understood as a currency value.',
		'javax.faces.converter.NumberConverter.CURRENCY_detail' : '"{0}" could not be understood as a currency value. Exemplo: {1}',
		'javax.faces.converter.NumberConverter.PERCENT' : '"{0}" could not be understood as a percentage.',
		'javax.faces.converter.NumberConverter.PERCENT_detail' : '"{0}" could not be understood as a percentage. Exemplo: {1}',
		'javax.faces.converter.NumberConverter.NUMBER' : '"{0}" não é um nº.',
		'javax.faces.converter.NumberConverter.NUMBER_detail' : '"{0}" não é um nº. Exemplo\: {1}',
		'javax.faces.converter.NumberConverter.PATTERN' : '"{0}" is not a number pattern.',
		'javax.faces.converter.NumberConverter.PATTERN_detail' : '"{0}" is not a number pattern. Exemplo: {1}',
		'javax.faces.converter.ShortConverter.SHORT' : '"{0}" deve ser um nº que consista de um ou mais digitos.',
		'javax.faces.converter.ShortConverter.SHORT_detail' : '"{0}" deve ser um nº entre -32768 e 32767 Exemplo\: {1}',
		'javax.faces.converter.STRING' : 'não é possivel converter "{0}" para texto.',
		'javax.faces.validator.DoubleRangeValidator.MAXIMUM' : 'o valor é maior que o máximo permitido de "{0}"',
		'javax.faces.validator.DoubleRangeValidator.MINIMUM' : 'o valor é menor que o minimo permidito de "{0}"',
		'javax.faces.validator.DoubleRangeValidator.NOT_IN_RANGE' : 'o valor especificado não está entre os valores expectáveis de {0} e {1}.',
		'javax.faces.validator.DoubleRangeValidator.TYPE' : 'o valor não é do tipo correcto',
		'javax.faces.validator.LengthValidator.MAXIMUM' : 'o tamanho é maior que o máximo permitido de "{0}"',
		'javax.faces.validator.LengthValidator.MINIMUM' : 'o tamanho é menor que o minimo permitido de "{0}"',
		'javax.faces.validator.LongRangeValidator.MAXIMUM' : 'o valor é maior que o máximo permitido de "{0}"',
		'javax.faces.validator.LongRangeValidator.MINIMUM' : 'o valor é menor que o minimo permitido de "{0}"',
		'javax.faces.validator.LongRangeValidator.NOT_IN_RANGE' : 'o valor especificado não está entre os valores expectáveis de {0} e {1}',
		'javax.faces.validator.LongRangeValidator.TYPE' : 'o valor não é do tipo apropriado',
		'javax.faces.validator.NOT_IN_RANGE' : 'o valor especificado não está entre os valores expectáveis de {0} e {1}',
		'javax.faces.validator.RegexValidator.PATTERN_NOT_SET' : 'a expressão regular tem de ser especificada',
		'javax.faces.validator.RegexValidator.PATTERN_NOT_SET_detail' : 'a expressão regular não pode estar vazia',
		'javax.faces.validator.RegexValidator.NOT_MATCHED' : 'a expressão regular não coincide',
		'javax.faces.validator.RegexValidator.NOT_MATCHED_detail' : 'a expressão regular "{0}" não coincide',
		'javax.faces.validator.RegexValidator.MATCH_EXCEPTION' : 'erro na expressão regular',
		'javax.faces.validator.RegexValidator.MATCH_EXCEPTION_detail' : 'erro na expressão regular, "{0}"',
		'javax.faces.validator.BeanValidator.MESSAGE' : '{0}',
		'javax.validation.constraints.AssertFalse.message' : 'tem de ser falso',
		'javax.validation.constraints.AssertTrue.message' : 'tem de ser verdadeiro',
		'javax.validation.constraints.DecimalMax.message' : 'tem de ser menor ou igual a {0}',
		'javax.validation.constraints.DecimalMin.message' : 'tem de ser maior ou igual a {0}',
		'javax.validation.constraints.Digits.message' : 'Valor numérico fora dos limites (máximo <{0} inteiros>.<{1} decimais>)',
		'javax.validation.constraints.Future.message' : 'tem de ser uma data futura',
		'javax.validation.constraints.Max.message' : 'tem de ser menor ou igual a {0}',
		'javax.validation.constraints.Min.message' : 'tem de ser maior ou igual a {0}',
		'javax.validation.constraints.NotNull.message' : 'Preenchimento obrigatório',
		'javax.validation.constraints.Null.message' : 'tem de ser vazio',
		'javax.validation.constraints.Past.message' : 'tem de ser uma data passada',
		'javax.validation.constraints.Pattern.message' : 'deve fazer matching com "{0}"',
		'javax.validation.constraints.Size.message' : 'o tamanho tem de estar compreendido entre {0} e {1}'
	}
};

PrimeFaces.locales['pt_PT'] = {
		closeText : 'Fechar',
		prevText : 'Anterior',
		nextText : 'Seguinte',
		monthNames : [ 'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
				'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro' ],
		monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago',
				'Set', 'Out', 'Nov', 'Dez' ],
		dayNames : [ 'Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta',
				'Sábado' ],
		dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb' ],
		dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S', 'S' ],
		weekHeader : 'Semana',
		firstDay : 1,
		isRTL : false,
		showMonthAfterYear : false,
		yearSuffix : '',
		timeOnlyTitle : 'Só Horas',
		timeText : 'Tempo',
		hourText : 'Hora',
		minuteText : 'Minuto',
		secondText : 'Segundo',
		currentText : 'Hoje',
		ampm : false,
		month : 'Mês',
		week : 'Semana',
		day : 'Dia',
		allDayText : 'Dia completo',
		messages : { // optional for Client Side Validation
			'javax.faces.component.UIInput.CONVERSION' : 'Ocorreu um erro de conversão',
			'javax.faces.component.UIInput.REQUIRED' : 'Preenchimento obrigatório',
			'javax.faces.component.UIInput.UPDATE' : 'Ocorreu um erro ao processar a informação submetida',
			'javax.faces.component.UISelectOne.INVALID' : 'Valor não é válido',
			'javax.faces.component.UISelectMany.INVALID' : 'Valor não é válido',
			'javax.faces.converter.BigDecimalConverter.DECIMAL' : '"{0}" deverá ser um nº decimal',
			'javax.faces.converter.BigDecimalConverter.DECIMAL_detail' : '"{0}" deve ser um nº decimal consistindo de zero ou mais digitos e que pode ser seguido por um ponto decimal.  Exemplo\: {1}',
			'javax.faces.converter.BigIntegerConverter.BIGINTEGER' : '"{0}" deverá ser um nº consistindo de um ou mais digitos',
			'javax.faces.converter.BigIntegerConverter.BIGINTEGER_detail' : '"{0}" deverá ser um nº consistindo de um ou mais digitos. Exemplo\: {1}',
			'javax.faces.converter.BooleanConverter.BOOLEAN' : '{1}: "{0}" deve ser "verdadeiro" ou "falso"',
			'javax.faces.converter.BooleanConverter.BOOLEAN_detail' : '{1}: "{0}" deve ser "verdadeiro" ou "falso". Outro valor diferente de "verdadeiro" será interpretado como "falso"',
			'javax.faces.converter.ByteConverter.BYTE' : '"{0}" deve ser um nº entre 0 e 255',
			'javax.faces.converter.ByteConverter.BYTE_detail' : '"{0}" deve ser um nº entre 0 e 255.  Exemplo\: {1}',
			'javax.faces.converter.CharacterConverter.CHARACTER' : '"{0}" deve ser um caracter válido',
			'javax.faces.converter.CharacterConverter.CHARACTER_detail' : '"{0}" deve ser um caracter ASCII válido',
			'javax.faces.converter.DateTimeConverter.DATE' : '"{0}" não  interpretado como data',
			'javax.faces.converter.DateTimeConverter.DATE_detail' : 'Data inválida',
			'javax.faces.converter.DateTimeConverter.TIME' : '"{0}" não foi interpretado como tempo',
			'javax.faces.converter.DateTimeConverter.TIME_detail' : '"{0}" não foi interpretado como tempo. Exemplo: {1}',
			'javax.faces.converter.DateTimeConverter.DATETIME' : '"{0}" não foi interpretado como data e hora',
			'javax.faces.converter.DateTimeConverter.DATETIME_detail' : '"{0}" não foi interpretado como data e hora. Exemplo: {1}',
			'javax.faces.converter.DateTimeConverter.PATTERN_TYPE' : '{1}: um "padrão" ou "tipo" deve ser especificado para converter o valor "{0}"',
			'javax.faces.converter.DoubleConverter.DOUBLE' : '"{0}" tem de ser um número constituído por um ou mais digitos',
			'javax.faces.converter.DoubleConverter.DOUBLE_detail' : '"{0}" deve ser um nº entre 4.9E-324 e 1.7976931348623157E308  Exemplo\: {1}',
			'javax.faces.converter.EnumConverter.ENUM' : '"{0}" tem de ser convertível para um enumerado',
			'javax.faces.converter.EnumConverter.ENUM_detail' : '"{0}" "{0}" tem de ser convertível para um enumerado a partir da constante "{1}".',
			'javax.faces.converter.EnumConverter.ENUM_NO_CLASS' : '{1}: "{0}" must be convertible to an enum from the enum, but no enum class provided.',
			'javax.faces.converter.EnumConverter.ENUM_NO_CLASS_detail' : '{1}: "{0}" "{0}" tem de ser convertível para um enumerado mas nenhuma classe de enumerado foi indicada',
			'javax.faces.converter.FloatConverter.FLOAT' : '"{0}" tem de ser um número constituído por um ou mais digitos',
			'javax.faces.converter.FloatConverter.FLOAT_detail' : '"{0}" deve ser um nº entre 1.4E-45 e 3.4028235E38  Exemplo\: {1}',
			'javax.faces.converter.IntegerConverter.INTEGER' : '"{0}" must be a number consisting of one or more digits.',
			'javax.faces.converter.IntegerConverter.INTEGER_detail' : '"{0}" deve ser um valor numérico. Exemplo\: {1}',
			'javax.faces.converter.LongConverter.LONG' : '"{0}" must be a number consisting of one or more digits.',
			'javax.faces.converter.LongConverter.LONG_detail' : '"{0}" deve ser um valor numérico. Exemplo\: {1}',
			'javax.faces.converter.NumberConverter.CURRENCY' : '"{0}" could not be understood as a currency value.',
			'javax.faces.converter.NumberConverter.CURRENCY_detail' : '"{0}" could not be understood as a currency value. Exemplo: {1}',
			'javax.faces.converter.NumberConverter.PERCENT' : '"{0}" could not be understood as a percentage.',
			'javax.faces.converter.NumberConverter.PERCENT_detail' : '"{0}" could not be understood as a percentage. Exemplo: {1}',
			'javax.faces.converter.NumberConverter.NUMBER' : '"{0}" não é um nº.',
			'javax.faces.converter.NumberConverter.NUMBER_detail' : '"{0}" não é um nº. Exemplo\: {1}',
			'javax.faces.converter.NumberConverter.PATTERN' : '"{0}" is not a number pattern.',
			'javax.faces.converter.NumberConverter.PATTERN_detail' : '"{0}" is not a number pattern. Exemplo: {1}',
			'javax.faces.converter.ShortConverter.SHORT' : '"{0}" deve ser um nº que consista de um ou mais digitos.',
			'javax.faces.converter.ShortConverter.SHORT_detail' : '"{0}" deve ser um nº entre -32768 e 32767 Exemplo\: {1}',
			'javax.faces.converter.STRING' : 'não é possivel converter "{0}" para texto.',
			'javax.faces.validator.DoubleRangeValidator.MAXIMUM' : 'o valor é maior que o máximo permitido de "{0}"',
			'javax.faces.validator.DoubleRangeValidator.MINIMUM' : 'o valor é menor que o minimo permidito de "{0}"',
			'javax.faces.validator.DoubleRangeValidator.NOT_IN_RANGE' : 'o valor especificado não está entre os valores expectáveis de {0} e {1}.',
			'javax.faces.validator.DoubleRangeValidator.TYPE' : 'o valor não é do tipo correcto',
			'javax.faces.validator.LengthValidator.MAXIMUM' : 'o tamanho é maior que o máximo permitido de "{0}"',
			'javax.faces.validator.LengthValidator.MINIMUM' : 'o tamanho é menor que o minimo permitido de "{0}"',
			'javax.faces.validator.LongRangeValidator.MAXIMUM' : 'o valor é maior que o máximo permitido de "{0}"',
			'javax.faces.validator.LongRangeValidator.MINIMUM' : 'o valor é menor que o minimo permitido de "{0}"',
			'javax.faces.validator.LongRangeValidator.NOT_IN_RANGE' : 'o valor especificado não está entre os valores expectáveis de {0} e {1}',
			'javax.faces.validator.LongRangeValidator.TYPE' : 'o valor não é do tipo apropriado',
			'javax.faces.validator.NOT_IN_RANGE' : 'o valor especificado não está entre os valores expectáveis de {0} e {1}',
			'javax.faces.validator.RegexValidator.PATTERN_NOT_SET' : 'a expressão regular tem de ser especificada',
			'javax.faces.validator.RegexValidator.PATTERN_NOT_SET_detail' : 'a expressão regular não pode estar vazia',
			'javax.faces.validator.RegexValidator.NOT_MATCHED' : 'a expressão regular não coincide',
			'javax.faces.validator.RegexValidator.NOT_MATCHED_detail' : 'a expressão regular "{0}" não coincide',
			'javax.faces.validator.RegexValidator.MATCH_EXCEPTION' : 'erro na expressão regular',
			'javax.faces.validator.RegexValidator.MATCH_EXCEPTION_detail' : 'erro na expressão regular, "{0}"',
			'javax.faces.validator.BeanValidator.MESSAGE' : '{0}',
			'javax.validation.constraints.AssertFalse.message' : 'tem de ser falso',
			'javax.validation.constraints.AssertTrue.message' : 'tem de ser verdadeiro',
			'javax.validation.constraints.DecimalMax.message' : 'tem de ser menor ou igual a {0}',
			'javax.validation.constraints.DecimalMin.message' : 'tem de ser maior ou igual a {0}',
			'javax.validation.constraints.Digits.message' : 'Valor numérico fora dos limites (máximo <{0} inteiros>.<{1} decimais>)',
			'javax.validation.constraints.Future.message' : 'tem de ser uma data futura',
			'javax.validation.constraints.Max.message' : 'tem de ser menor ou igual a {0}',
			'javax.validation.constraints.Min.message' : 'tem de ser maior ou igual a {0}',
			'javax.validation.constraints.NotNull.message' : 'Preenchimento obrigatório',
			'javax.validation.constraints.Null.message' : 'tem de ser vazio',
			'javax.validation.constraints.Past.message' : 'tem de ser uma data passada',
			'javax.validation.constraints.Pattern.message' : 'deve fazer matching com "{0}"',
			'javax.validation.constraints.Size.message' : 'o tamanho tem de estar compreendido entre {0} e {1}'
		}
	};

PrimeFaces.widget.AutoComplete = PrimeFaces.widget.AutoComplete.extend({

	bindStaticEvents : function() {
		var $this = this;

		this.bindKeyEvents();

		this.dropdown.mouseover(function() {
			$(this).addClass('ui-state-hover');
		}).mouseout(function() {
			$(this).removeClass('ui-state-hover');
		}).mousedown(function() {
			if ($this.active) {
				$(this).addClass('ui-state-active');
			}
		}).mouseup(function() {
			if ($this.active) {
				$(this).removeClass('ui-state-active');

				$this.search('');
				$this.input.focus();
			}
		}).focus(function() {
			$(this).addClass('ui-state-focus');
		}).blur(function() {
			$(this).removeClass('ui-state-focus');
		}).keydown(
				function(e) {
					var keyCode = $.ui.keyCode, key = e.which;

					if (key === keyCode.SPACE || key === keyCode.ENTER
							|| key === keyCode.NUMPAD_ENTER) {
						$(this).addClass('ui-state-active');
					}
				}).keyup(
				function(e) {
					var keyCode = $.ui.keyCode, key = e.which;

					if (key === keyCode.SPACE || key === keyCode.ENTER
							|| key === keyCode.NUMPAD_ENTER) {
						$(this).removeClass('ui-state-active');
						$this.search('');
						$this.input.focus();
						e.preventDefault();
						e.stopPropagation();
					}
				});

		// hide overlay when outside is clicked
		this.hideNS = 'mousedown.' + this.id;
		$(document.body).off(this.hideNS).on(
				this.hideNS,
				function(e) {
					if ($this.panel.is(":hidden")) {
						return;
					}

					var offset = $this.panel.offset();
					if (e.target === $this.input.get(0)) {
						return;
					}

					// http://stackoverflow.com/questions/29474817/force-selection-of-highlighted-pautocomplete-item-when-panel-is-hidden-by-click
					if ($this.cfg.forceSelection) {
						$this.items.filter('.ui-state-highlight').click();
					}

					if (e.pageX < offset.left
							|| e.pageX > offset.left + $this.panel.width()
							|| e.pageY < offset.top
							|| e.pageY > offset.top + $this.panel.height()) {
						$this.hide();
					}
				});

		this.resizeNS = 'resize.' + this.id;
		$(window).off(this.resizeNS).on(this.resizeNS, function(e) {
			if ($this.panel.is(':visible')) {
				$this.alignPanel();
			}
		});
	}

});