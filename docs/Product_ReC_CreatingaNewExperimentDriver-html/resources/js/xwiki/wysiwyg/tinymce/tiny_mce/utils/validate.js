function testRegExp(B,C,A){return new RegExp(A).test(document.forms[B].elements[C].value)
}function validateString(A,B){return(document.forms[A].elements[B].value.length>0)
}function validateSelection(A,B){return(document.forms[A].elements[B].selectedIndex>0)
}function validateCheckBox(A,B){return document.forms[A].elements[B].checked
}function validateCleanString(A,B){return testRegExp(A,B,"^[A-Za-z0-9_]+$")
}function validateEmail(A,B){return testRegExp(A,B,"^[-!#$%&'*+\\./0-9=?A-Z^_`a-z{|}~]+@[-!#$%&'*+\\/0-9=?A-Z^_`a-z{|}~]+.[-!#$%&'*+\\./0-9=?A-Z^_`a-z{|}~]+$")
}function validateAbsUrl(A,B){return testRegExp(A,B,"^(news|telnet|nttp|file|http|ftp|https)://[-A-Za-z0-9\\.]+$")
}function validateNumber(A,B,C){return(!C&&value=="")?false:testRegExp(A,B,"^-?[0-9]*\\.?[0-9]*$")
}function validateSize(A,B){return testRegExp(A,B,"^[0-9]+(px|%)?$")
}function validateID(A,B){return testRegExp(A,B,"^[A-Za-z_]([A-Za-z0-9_])*$")
};