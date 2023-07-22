<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Authentification</title>
<meta http-equiv='content-type' content='text/html;charset=iso-8859-1'>
<meta http-equiv='cache-control' content='no-cache'>
<meta http-equiv='pragma' content='no-cache'>
<meta http-equiv='expires' content='0'>
<link rel='stylesheet' type='text/css' href='/JEITime/resources/css/style2.css'>
<link rel='stylesheet' type='text/css' href='/jeitime-ui/resources/css/style2.css'>
</head>

<body>
<div class=Frame id='fenetre'>
<div id='header'> </div>
<script language='javascript' type='text/javascript'>
function Menu_getParentSubMenu(elt)
{
	elt = elt.parentNode;
	while(elt)
	{
		if(elt.nodeName == "LI" && elt.className.indexOf("submenu") >= 0)
			return elt;
		elt = elt.parentNode;
	}
	return null;
}
function Menu_focus(elt)
{
	// TODO: this has to be recursive for menu deeper than 1
	var li = Menu_getParentSubMenu(elt);
	if(li.className.indexOf(" focus") < 0)
		li.className += " focus";
	
	if(li.blurMenuTimeout)
	{
		// --- maintain parent
		clearTimeout(li.blurMenuTimeout);
		li.blurMenuTimeout = null;
	}
}
function Menu_blur(elt)
{
	// TODO: this has to be recursive for menu deeper than 1
	var li = Menu_getParentSubMenu(elt);
	if(li.blurMenuTimeout)
	{
		clearTimeout(li.blurMenuTimeout);
		li.blurMenuTimeout = null;
	}
	// --- blur parent in a while (if not maintained)
	li.blurMenuTimeout = setTimeout(function(){
		li.className = "submenu";
		li.blurMenuTimeout = null;
	}, 50);
}

</script>

<div id='sidebar'> </div>

<div id='page-container'>
	<h1 class='page-title'>Authentification</h1>
	<script language='javascript' type='text/javascript'>
		function Form_focusFirstInput(formName)
		{
			// focus first form element that is:
			//  - either TEXTAREA, SELECT, INPUT text, INPUT button, INPUT submit, BUTTON, INPUT image, INPUT file, INPUT password
			//  - and visible, enabled and not readonly
			var form = document.forms[formName];
			for(var i=0; i<form.elements.length; i++)
			{
				var e = form.elements[i];
				var eltType = e.nodeName == "INPUT" ? e.type : e.nodeName;
				if((eltType == "TEXTAREA" 
					|| eltType == "SELECT" 
					|| eltType == "BUTTON" 
					|| eltType == "text" 
					|| eltType == "button" 
					|| eltType == "submit" 
					|| eltType == "image" 
					|| eltType == "file" 
					|| eltType == "password")
					&& !e.disabled && !e.readOnly && e.focus)
				{
					// --- focus the element
					e.focus();
					// --- for IE, if the element is text input, place the caret at the end
					if(e.createTextRange && (eltType == "TEXTAREA" || eltType == "text" || eltType == "password"))
					{
						var range = form.elements[i].createTextRange();
						range.collapse(false);
						range.select();
					}
					return;
				}
			}
		}
		function Checkbox_toggle(checkboxId)
		{
			var checkbox = document.getElementById(checkboxId);
			checkbox.checked = !checkbox.checked;
		}
		function Radio_check(radioId)
		{
			var radio = document.getElementById(radioId);
			if(!radio.checked)
				radio.checked = true;
		}
		function Form_submit(formName, action)
		{
			var form = document.forms[formName];
			form.enctype = "application/x-www-form-urlencoded";
			form.encoding = "application/x-www-form-urlencoded";
			var actionField = form.elements["action"];
			actionField.value = action;
			form.submit();
		}
		function DateField_showCalendar(evt, formName, dateFieldName, calendarBaseUrl)
		{
			var form = document.forms[formName];
			var dateField = form.elements[dateFieldName];
			var calendarUrl = calendarBaseUrl + (calendarBaseUrl.indexOf('?') < 0 ? "?" : "&") + "selected="+dateField.value;
			var width = 300;
			var height = 200;
			// !!! Doesn't work with doctype transitional
			var calendarWindowRule = findStyleRule(".CalendarWindow");
			if(calendarWindowRule != null)
			{
				if(calendarWindowRule.style.width != null)
					width = parseInt(calendarWindowRule.style.width);
				if(calendarWindowRule.style.height != null)
					height = parseInt(calendarWindowRule.style.height);
			}
			if(width < 100) width = 300;
			if(height < 80) height = 150;
			
			// --- open calendar popup "as modal as possible"
			if(window.showModalDialog)
				window.showModalDialog(calendarUrl, {opener: self}, "center=no;scroll=no;status=no;resizable=yes;unadorned=no;dialogWidth="+width+"px;dialogHeight="+height+"px;dialogLeft="+evt.screenX+"px;dialogTop="+evt.screenY+"px;");
			else
				window.open(calendarUrl, "_blank", "titlebar=no,modal=yes,dialog=yes,dependent=yes,status=no,location=no,menubar=no,toolbar=no,resizable=yes,width="+width+",height="+height+",left="+evt.screenX+",top="+evt.screenY);
		}
		function DateField_setDateAndClose(formName, dateFieldName, date)
		{
			// --- 1: set date
			var oWin = null;
			if(window.opener) // popup opened with window.open()
				oWin = window.opener;
			else if(window.dialogArguments && window.dialogArguments.opener) // popup opened with window.showModalDialog()
				oWin = window.dialogArguments.opener;
			
			if(oWin && oWin.document)
			{
				var form = oWin.document.forms[formName];
				if(form)
				{
					var dateField = form.elements[dateFieldName];
					if(dateField)
					{
						dateField.value = date;
						dateField.focus();
					}
				}
			}
			// --- 2: close calendar popup
			window.close();
		}
		function findStyleRule(styleName)
		{
			try
			{
				for (var i=0; i<document.styleSheets.length; i++)
				{ 
					var rules = document.all ? document.styleSheets[i].rules : document.styleSheets[i].cssRules;
					for (var j=0; j<rules.length; j++)
					{
						if (rules[j].selectorText == styleName)
							return rules[j];
					}
				}
			}
			catch(e)
			{
			}
			return null;
		}
</script>
	
	<div class='Form'>
		<form action="j_security_check" method="post">
			<div class="Fields">
				<fieldset>
					<h2 class="legend">Interface d'authentification</h2><br/>

					<div class="field odd mandatory">
						<label class='field' for='main_projetBean_nomProjet'> login </label>
						<div class=input > 
							<input type="text" name="j_username" id='main_projetBean_nomProjet' /> 
						</div>
					</div>
					<div class="field odd mandatory">
						<label class='field' for='main_projetBean_nomProjet'> mot de passe </label>
						<div class=input>
							<input type="password" name="j_password"/>
						</div>
					</div>	
				</fieldset>
			</div>
			<div class="Buttons">
				<ul><li>
					<input type="submit" value="Connexion"/>
				</li></ul>
			</div><br/><br/>

		</form>
	</div>
</div>
<div id="footer"> Créé et développé par JEInnov (version 1.5)</div>

</div>
</body>
</html>