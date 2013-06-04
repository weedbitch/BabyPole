function strReplaceAll( str, orgStr, repStr ) {
			return str.split(orgStr).join(repStr);
		}
	
		function isEmpty(str) {
			if( str == null || str == undefined || str == "" ) {
				return true;
			}
			return false;
		}
		
		function stripToEmpty(str) {
			if( str == null || str == undefined || str == "" ) {
				return "";
			}
			return str;
		}
	
		function removeLastDelimiter(src, del) {
			src = stripToEmpty(src);
			if( src.lastIndexOf(del) + 1 == src.length ) {
				return src.substring(0, src.length - 1);
			}
			return src;
		}
		
		
		function focusInput(ele, type, inputName) {
			if( type == "B" ) {
				$("input[name=" + inputName + "]").focus();
			} else {
				$(ele).focus();
			}
		}
		
		
		function getInputValueByType(inputName, inputType) {
			var value = "";
			if( inputType == "text" ) {
				value = $("input[name=" + inputName + "]").val();
			} else if( inputType == "radio") {
				value = $("input[name=" + inputName + "]:radio:checked").val();
			} else if( inputType == "select" ) {
				value = $('#' + inputName + " option:selected").val();
			} else if( inputType == "checkbox") {
				value = getCheckBoxValues(inputName);
			}
			return stripToEmpty(value);
			//alert("name=" + inputName + " type=" + inputType + " value=" + value );
		}
		
		function getCheckBoxValues(name) {
			var values = "";
			$("input[name=" + name + "]").each(function() {
				if( this.checked )
					values += $(this).val() + ",";
			});
			return removeLastDelimiter(values, ",");
		}
		
		function getSubjectInputNames(subjectId) {
			var names = new Array();
			$('#' + subjectId + ' :input').each( function() {
				var tagName = $(this).prop('tagName');
				if( tagName == "INPUT" ) {
					names[ $(this).attr('name') ] = $(this).attr('type');
				} else if( tagName == "SELECT") {
					names[ $(this).attr('name') ] = "select";
				}
			})
			return names;
		}
		
		/**
		* May be used by multiple check type
		*/
		function appendValue(src, value) {
			if( value != null && value != "" ) {
				src += ", '" + value + "'";
			}
			//ignore comments0_1
			return src;
		}
		
		function appendTextValue() {
			
			//ignore comments
			//ignore comments2
		}