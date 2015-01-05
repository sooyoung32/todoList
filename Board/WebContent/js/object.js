/* Separator */
var SEPARATOR_DATE    = "-";
var SEPARATOR_TIME    = ":";
var SEPARATOR_NUMBER  = ",";
var SEPARATOR_DEFAULT = "-";



/**
 * String.nvl() method 구현
 */
String.prototype.nvl = function() {
	return ( this == null ) ? "" : this;
};

String.prototype.nvlNum = function(initVal) {
	return ( this == null ) ? initVal : (this == "" ) ? initVal : this;
};
/**
 * String.trim() method 구현
 */
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/gi, "");
};

/**
 * String.ltrim() method 구현
 */
String.prototype.ltrim = function() {
	return this.replace(/(^\s*)/g, "");
};

/**
 * String.rtrim() method 구현
 */
String.prototype.rtrim = function() {
	return this.replace(/(\s*$)/g, "");
};

/**
 * String.lpad() method 구현
 */
String.prototype.lpad = function(padLength, padChar) {
    padChar = padChar == null ? " " : padChar;
    var result = "";
    for (var i = 0, n = padLength - this.byteLength(); i < n; i++) {
        result += padChar;
    }

    return result + this;
};


/**
 * String.rpad() method 구현
 */
String.prototype.rpad = function(padLength, padChar) {
    padChar = padChar == null ? " " : padChar;
    var result = this;
    for (var i = 0, n = padLength - this.byteLength(); i < n; i++) {
        result += padChar;
    }

    return result;
};


/**
 * String.replaceAll(from, to) method 구현
 */
String.prototype.replaceAll = function(from, to) {
    if ( from == "" ) {
        return this;
    } else {
        return this.replace(new RegExp(from, "g"), to);    
    }
	
};


/**
 * String.removeAll(ch) method 구현
 */
String.prototype.removeAll = function(ch) {
	return this.replaceAll(ch, "");
};


/**
 * String.reverse() method 구현
 */

String.prototype.reverse = function() {
	var result = "";
	var i = this.length;

	while (i > 0) {
		result += this.charAt(--i);
	}

	return result;
};

/**
 * String.byteLength() method 구현
 */
String.prototype.byteLength = function() {
    var result = 0;
    for (var i = 0; i < this.length; i++) {
        var chr = escape(this.charAt(i));
        if (chr.length == 1 ) {
            result++;
        } else if (chr.indexOf("%u") != -1) {
            result += 2;
        } else if (chr.indexOf("%") != -1) {
            result += chr.length / 3;
        }
    }
    return result;
};


/**
 * String.byteIndexOf() method 구현
 */
String.prototype.byteIndexOf = function(chr) {
    var idx = this.indexOf(chr);
    if (idx == -1) {
        return idx;
    } else {
        return this.substring(0, idx).byteLength();
    }
};


/**
 * String.substringByte() method 구현
 */
String.prototype.substringByte = function (start, end) {
    var result = "";

    var preByte = 0;
    var curByte = 0;

    for (var i = 0; i < this.length; i++) {
        var chr = this.charAt(i);

        preByte = curByte;
        curByte += chr.byteLength();

        if (preByte >= start && curByte <= end) {
            result += chr;
        } else if (curByte > end) {
            break;
        }
    }

    return result;
};


/**
 * String.substrByte() method 구현
 */
String.prototype.substrByte = function(start, length) {
    return this.substringByte(start, start + length);
};

/**
 * String.defaultString() method 구현
 * description : String 값이 "" 이면 def를 반환
 */
String.prototype.defaultString = function(def) {
	return this.trim() == "" ? def : this;
};


/**
 * String.toInt() method 구현
 */
String.prototype.toInt = function() {
    return this.trim() == "" ? 0 : parseInt(this.trim(), 10);
};


/**
 * String.toHex() method 구현 : 16진수 구하기
 */
String.prototype.toHex = function() {
    return this.toRadix(16);
};


/**
 * String.toRadix() method 구현 : radix 진수 구하기
 */
String.prototype.toRadix = function(radix) {
    return Number(this).toString(radix).toUpperCase();
};


/**
 * String.isEmpty() method 구현
 */
String.prototype.isEmpty = function() {
    return this.trim().length == 0;
};


/**
 * String값을 Number type으로 반환
 */
String.prototype.toNumber = function() {
	return (!this.isNumber()) ? 0 : Number(this.unmask("real")) ;
};


/**
 * String값을 Date 객체로 반환
 */
String.prototype.toDate = function() {
	var sDate = this.unmask("date");
	var iYear = sDate.substr(0, 4).toNumber();
	var iMonth = sDate.substr(4, 6).defaultString("0").toNumber() - 1;
	var iDate = sDate.substr(6, 4).defaultString("1").toNumber();

	return new Date(iYear, iMonth, iDate);
};


/**
 * String.isDigit() method 구현
 */
String.prototype.isDigit = function() {
    if (this.isEmpty()) {
        return true;
    }

    return this.search(/^\d+$/) != -1;
};

/**
 * String.isInt() method 구현
 */
String.prototype.isInt = function() {
    if (this.isEmpty()) {
        return true;
    }

    return this.search(/^(?:\-?|\+?)\d+$/) != -1;
};

/**
 * String.isDouble() method 구현
 */
String.prototype.isDouble = function(dec,frac) {
    if (this.isEmpty()) {
        return true;
    }
    oriStr  = this.removeAll(",");
    if (oriStr.indexOf(".") >= 0) {
        decStr  = oriStr.substring(0,oriStr.indexOf("."));
        fracStr = oriStr.substring(oriStr.indexOf(".")+1);
    } else {
        decStr  = oriStr;
        fracStr = "";
    }
    if (dec < decStr.length || frac < fracStr.length) return false;

    return !isNaN(oriStr);
};

/**
 * String.isNumber() method 구현
 */
String.prototype.isNumber = function() {
    if (this.isEmpty()) {
        return true;
    }
    return !isNaN(this);
};


/**
 * String.isCardNo() method 구현 - 카드번호
 */
String.prototype.isCardNo = function() {
    if (this.isEmpty()) {
        return true;
    }

    return this.search(/^\d{4}\-?\d{4}\-?\d{4}\-?\d{4}$/) != -1;
};



/**
 * String.isIdNo() method 구현 - 주민등록번호
 */
String.prototype.isIdNo = function() {
    if (this.isEmpty()) {
        return true;
    }

    if (this.search(/^\d{6}\-?\d{7}$/) == -1) {
        return false;
    }

    var sRegNo = this.removeAll("-");
    var iSum   = 0;
	for (var i = 0; i < sRegNo.length - 1; i++) {
	    iSum += (sRegNo.charAt(i) * (i % 8 + 2));
    }

    return sRegNo.charAt(12) == (11 - iSum % 11) % 10;
};



/**
 * String.isBizNo() method 구현 - 사업자등록번호
 */
String.prototype.isBizNo = function() {
    if (this.isEmpty()) {
        return true;
    }

    if (this.search(/^\d{3}\-?\d{2}\-?\d{5}$/) == -1) {
        return false;
    }

    var sBizNo = this.removeAll("-");
    var aCheckNo = new Array(1, 3, 7, 1, 3, 7, 1, 3, 5, 1);
	var iSum = 0;

    for (var i = 0; i < sBizNo.length; i++) {
        iSum += sBizNo.charAt(i) * aCheckNo[i] % 10;

        if (i == 8) {
            iSum += Math.floor(sBizNo.charAt(i) * aCheckNo[i] / 10);
        }
    }

    return iSum % 10 == 0;
};


/**
 * String.isCorpNo() method 구현 - 법인등록번호
 */
String.prototype.isCorpNo = function() {
    if (this.isEmpty()) {
        return true;
    }

    if (this.search(/^\d{6}\-?\d{7}$/) == -1) {
        return false;
    }

    var sCorpNo = this.removeAll("-");
    var iSum = 0;

    for (var i = 0; i < sCorpNo.length - 1; i++) {
        iSum += (sCorpNo.charAt(i) * (i % 2 + 1));
    }

    return sCorpNo.charAt(12) == (10 - iSum % 10);
};




/**
 * String.isZipNo() method 구현 - 우편번호
 */
String.prototype.isZipNo = function() {
    if (this.isEmpty()) {
        return true;
    }

    return this.search(/^\d{3}\-?\d{3}$/) != -1;
};


/**
 * String.isPhoneNo() method 구현 - 전화번호
 */
String.prototype.isPhoneNo = function() {
    if (this.isEmpty()) {
        return true;
    }

    return this.search(/^(?:0\d{1,2}\-?\d{2,4}|\d{2,4})\-?\d{4}$/) != -1;
};

/**
 * String.isYMD() method 구현 - 날짜
 * 구분자 => / or - or .
 */
String.prototype.isYMD = function() {
    if (this.isEmpty()) {
        return true;
    }

    if (this.search(/^(\d{4})[\/|\-|.]?(\d{2})[\/|\-|.]?(\d{2})$/) == -1) {
        return false;
    }

    var lastDays = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    if (RegExp.$2 > 12 || RegExp.$2 < 01) {
        return false;
    }

    if (RegExp.$3 > lastDays[RegExp.$2 - 1] || RegExp.$3 < 01) {
        return false;
    }

    if (RegExp.$2 == 2
            && RegExp.$3 > 28
            && (RegExp.$1 % 4 != 0 || (RegExp.$1 % 100 == 0 && RegExp.$1 % 400 != 0))) {
        return false;
    }

    return true;
};



/**
 * String.isYM() method 구현 - 날짜
 * 구분자 => / or - or .
 */
String.prototype.isYM = function() {
    if (this.isEmpty()) {
        return true;
    }

    if (this.search(/^(\d{4})[\/|\-|.]?(\d{2})$/) == -1) {
        return false;
    }

    if (RegExp.$2 > 12 || RegExp.$2 < 01) {
        return false;
    }

    return true;
};


/**
 * String.isMD() method 구현 - 날짜
 * 구분자 => / or - or .
 */
String.prototype.isMD = function() {
    if (this.isEmpty()) {
        return true;
    }
    if (this.search(/^(\d{2})[\/|\-|.]?(\d{2})$/) == -1) {
        return false;
    }

    var lastDays = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    if (RegExp.$1 > 12 || RegExp.$1 < 01) {
        return false;
    }

    if (RegExp.$2 > lastDays[RegExp.$1 - 1] || RegExp.$2 < 01) {
        return false;
    }
    return true;
};



/**
 * String.isHMS() method 구현 - 시간
 */
String.prototype.isHMS = function() {
    if (this.isEmpty()) {
        return true;
    }

    if (this.search(/^([0|1|2]\d):?(\d{2})?:?(\d{2})?$/) == -1) {
        return false;
    }

    if (RegExp.$1 > 23 || RegExp.$2 > 59 || RegExp.$3 > 59) {
        return false;
    }

    return true;
};



/**
 * String.isMS() method 구현 - 시간
 */
String.prototype.isMS = function() {
    if (this.isEmpty()) {
        return true;
    }

    if (this.search(/^(\d{2}):?(\d{2})$/) == -1) {
        return false;
    }

    if (RegExp.$1 > 59 || RegExp.$2 > 59) {
        return false;
    }

    return true;
};


/**
 * String.isEmail() method 구현 - email
 */
String.prototype.isEmail = function() {
    if (this.isEmpty()) {
        return true;
    }

	var checkTLD = 1;
	var knownDomsPat = /^(com|net|org|edu|int|mil|gov|arpa|biz|aero|name|coop|info|pro|museum)$/;
	var emailPat = /^(.+)@(.+)$/;
	var specialChars = "\\(\\)><@,;:\\\\\\\"\\.\\[\\]";
	var validChars = "\[^\\s" + specialChars + "\]";
	var quotedUser = "(\"[^\"]*\")";
	var ipDomainPat = /^\[(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})\]$/;
	var atom = validChars + '+';
	var word = "(" + atom + "|" + quotedUser + ")";
	var userPat = new RegExp("^" + word + "(\\." + word + ")*$");
	var domainPat = new RegExp("^" + atom + "(\\." + atom +")*$");
	var matchArray = this.match(emailPat);

	if (matchArray == null) {
		return false;
	}

	var user = matchArray[1];
	var domain = matchArray[2];

	for (var i = 0; i < user.length; i++) {
		if (user.charCodeAt(i) > 127) {
			return false;
	   }
	}

	for (var i = 0; i < domain.length; i++) {
		if (domain.charCodeAt(i) > 127) {
			return false;
	    }
	}

	if (user.match(userPat) == null) {
		return false;
	}

	var ipArray = domain.match(ipDomainPat);

	if (ipArray != null) {
		for (var i = 1;i <= 4; i++) {
			if (ipArray[i] > 255) {
				return false;
 		    }
		}
		return true;
	}

	var atomPat = new RegExp("^" + atom + "$");
	var domArr = domain.split(".");
	var len = domArr.length;

	for (i = 0; i < len; i++) {
		if (domArr[i].search(atomPat) == -1) {
			return false;
	   }
	}

	if (checkTLD && domArr[domArr.length - 1].length != 2
		&& domArr[domArr.length - 1].search(knownDomsPat) == -1) {
		return false;
	}

    return len >= 2;
};
/**
 * String.isFormat() method 구현 - 특정 포맷에 맞는 형식인가
 */
String.prototype.isFormat = function() {

    if (this.isEmpty()) {
        return true;
    }

    var formatStr = arguments[0].split("-");
    var searchStr = "this.search(/^";
    for (var _i=0; _i < formatStr.length; _i++) {
        if (_i==0) {
            searchStr += "\\d{"+formatStr[_i]+"}";
        } else {
            searchStr += "\\-?\\d{"+formatStr[_i]+"}";
        }
    }
    searchStr += "$/) != -1;";

    return eval(searchStr);
};


/**
 * String의 각 표현형식별 mask 처리하여 반환
 */
String.prototype.mask = function(preset) {
	var result = null;

    // preset에 () 형식이 포함될 경우 - 특정 format 처리
    if (preset.indexOf("(") >= 0 && preset.indexOf(")") >=0 ) {
        var formatStr = preset.substring(preset.indexOf("(")+1,preset.indexOf(")"));
        preset = preset.substring(0,preset.indexOf("("));
        result = this.maskFormat(formatStr);

    // 일반적인 경우
    } else {
    	switch (preset.toLowerCase()) {
    		case "number":
    			break;
    		case "numberdot":
    			break;
    		case "numberplus":
    			break;
    		case "amtplus":
    		case "amt":
    		case "amtdot":
    			result = this.maskNumber();
    			break;

            case "ymdhms":
            case "ymdhm":
                result = this.maskDateTime();
                break;

    		case "dateymd":
    		case "dateym":
    			result = this.maskDate();
    			break;

    		case "datemd":
    			result = this.maskDateMD();
    			break;

    		case "timehms":
    		case "timehm":
    		case "timems":
    			result = this.maskTime();
    			break;

    		case "telno":
    		case "hpno":
    			result = this.maskPhoneNo();
    			break;

    		case "cardno":
    			result = this.maskCardNo();
    			break;

    		case "idno":
            case "corpno":
    			result = this.maskIdNo();
    			break;

    		case "bizno":
    			result = this.maskBizNo();
    			break;

    		case "corp":
    			result = this.maskIdNo();
    			break;

    		case "zipno":
    			result = this.maskZipNo();
    			break;

    		default:
    			result = this;
    			break;
    	}
    }
    if (result == null || result == "undefined") result = this;
	return result;
};


/**
 * String의 각 표현형식별 mask 처리 삭제하여 반환
 */
String.prototype.unmask = function(preset) {
	var result = null;

    // preset에 () 형식이 포함될 경우
    if (preset.indexOf("(") >= 0 && preset.indexOf(")") >=0 ) {
		result = this.removeAll(SEPARATOR_DEFAULT);

    // 일반적인 경우
    } else {

    	switch (preset.toLowerCase()) {
    		case "number":
    		case "numberdot":
    		case "numberplus":
    		case "amt":
    			result = this.removeAll(SEPARATOR_NUMBER);
    			break;

    		case "dateymd":
    		case "dateym":
    		case "datemd":
    			result = this.removeAll(SEPARATOR_DATE);
    			break;

    		case "timehms":
    		case "timehm":
    		case "timems":
    			result = this.removeAll(SEPARATOR_TIME);
    			break;

    		case "telno":
    		case "hpno":
    		case "cardno":
    		case "idno":
    		case "bizno":
    		case "corpno":
    		case "zipno":
    			result = this.removeAll(SEPARATOR_DEFAULT);
    			break;

    		default:
    			result = this.trim();
    			break;
    	}
    }
    if (result == null || result == "undefined") result = this;

	return result;
};


/**
 * String을 comma가 추가된 number type으로 반환
 */
String.prototype.maskNumber = function() {
	var sInt = this.removeAll(SEPARATOR_NUMBER);

	var sSign = sInt.charAt(0);
	if (sSign == "-" || sSign == "+") {
		sInt = sInt.substr(1);
		sSign = sSign == "+" ? "" : "-";
	} else {
        sSign = "";
    }

	var iPointIdx = sInt.indexOf(".");
	var sDecimal = "";
	if (iPointIdx > -1) {
		sDecimal = sInt.substr(iPointIdx);
		sInt = sInt.substr(0, iPointIdx);
	}

	if (sInt.length <= 3) {
		return sSign + sInt + sDecimal;
	}

	var sReverseInt = sInt.reverse();
	var sReverseResult = "";

    for(var i = 0, n = sReverseInt.length; i < n; i += 3) {
		sReverseResult += sReverseInt.substr(i, 3);
		if (i + 3 < n) {
			sReverseResult += SEPARATOR_NUMBER;
		}
    }

	return sSign + sReverseResult.reverse() + sDecimal;
};


/**
 * String을 날짜구분자가 추가된 형식으로 반환
 */
String.prototype.maskDateTime = function() {
	var sDateTime = this.removeAll(SEPARATOR_DATE)
                        .removeAll(SEPARATOR_TIME)
                        .removeAll(" ");
    var sDate = sDateTime.substring(0, 8);
    var sTime = sDateTime.substring(8);

    return sDate.maskDate() + " " + sDate.maskTime();
};


/**
 * String을 날짜구분자가 추가된 형식으로 반환
 */
String.prototype.maskDate = function() {
	var sDate = this.removeAll(SEPARATOR_DATE);
	var len = sDate.length;

	if (len <= 4) {
		return sDate;
	} else if (len <= 6) {
		return sDate.substr(0, 4)
			 + SEPARATOR_DATE
			 + sDate.substr(4);
	} else if (len <= 8) {
		return sDate.substr(0, 4)
		     + SEPARATOR_DATE
		     + sDate.substr(4, 2)
		     + SEPARATOR_DATE
		     + sDate.substr(6);
	}
};

/**
 * String을 날짜구분자가 추가된 형식으로 반환
 */
String.prototype.maskDateMD = function() {
	var sDate = this.removeAll(SEPARATOR_DATE);
	var len = sDate.length;

	if (len <= 2) {
		return sDate;
	} else if (len <= 4) {
		return sDate.substr(0, 2)
			 + SEPARATOR_DATE
			 + sDate.substr(2);
	}
};

/**
 * String을 시간구분자가 추가된 형식으로 반환
 */
String.prototype.maskTime = function() {
	var sTime = this.removeAll(SEPARATOR_TIME);
	var len = sTime.length;

	if (len <= 2) {
		return sTime;
	} else if (len <= 4) {
		return sTime.substr(0, 2)
		     + SEPARATOR_TIME
		     + sTime.substr(2);
	} else {
		return sTime.substr(0, 2)
		     + SEPARATOR_TIME
		     + sTime.substr(2, 2)
		     + SEPARATOR_TIME
		     + sTime.substr(4, 2);
	}
};


/**
 * String을 전화번호 구분자가 추가된 형식으로 반환
 */
String.prototype.maskPhoneNo = function() {
	var phoneNo = this.removeAll(SEPARATOR_DEFAULT);

	var idx = 0;
	var result = "";

	if (phoneNo.substr(0, 2) == "02") {
		result += (phoneNo.substr(0, 2) + SEPARATOR_DEFAULT);
		idx = 2;
	} else if (phoneNo.charAt(0) == "0") {
		result += (phoneNo.substr(0, 3) + SEPARATOR_DEFAULT);
		idx = 3;
	}

	if (phoneNo.substr(idx).length < 4) {
		result += phoneNo.substr(idx);
	} else if (phoneNo.substr(idx).length < 8) {
		result += (phoneNo.substr(idx, 3)
			       + SEPARATOR_DEFAULT
		           + phoneNo.substr(idx + 3));
	} else {
		result += (phoneNo.substr(idx, 4)
			       + SEPARATOR_DEFAULT
		           + phoneNo.substr(idx + 4));
	}

	return result;
};


/**
 * String을 카드번호 구분자가 추가된 형식으로 반환
 */
String.prototype.maskCardNo = function() {
	var cardNo = this.removeAll(SEPARATOR_DEFAULT);

    if (cardNo.length <= 4) {
        return cardNo;
    } else if (cardNo.length <= 8) {
        return cardNo.substr(0, 4)
             + SEPARATOR_DEFAULT
             + cardNo.substr(4)
    } else if (cardNo.length <= 12) {
        return cardNo.substr(0, 4)
             + SEPARATOR_DEFAULT
             + cardNo.substr(4, 4)
             + SEPARATOR_DEFAULT
             + cardNo.substr(8)
    } else {
        return cardNo.substr(0, 4)
             + SEPARATOR_DEFAULT
             + cardNo.substr(4, 4)
             + SEPARATOR_DEFAULT
             + cardNo.substr(8, 4)
             + SEPARATOR_DEFAULT
             + cardNo.substr(12);
    }
};


/**
 * String을 주민등록번호 구분자가 추가된 형식으로 반환
 */
String.prototype.maskIdNo = function() {
	var idNo = this.removeAll(SEPARATOR_DEFAULT);

    if (idNo.length <= 6) {
        return idNo;
    } else {
        return idNo.substr(0, 6)
             + SEPARATOR_DEFAULT
             + idNo.substr(6);
    }
};


/**
 * String을 사업자등록번호 구분자가 추가된 형식으로 반환
 */
String.prototype.maskBizNo = function() {
	var bizNo = this.removeAll(SEPARATOR_DEFAULT);

    if (bizNo.length <= 3) {
        return bizNo;
    } else if (bizNo.length <= 5) {
        return bizNo.substr(0, 3)
             + SEPARATOR_DEFAULT
             + bizNo.substr(3)
    } else {
        return bizNo.substr(0, 3)
             + SEPARATOR_DEFAULT
             + bizNo.substr(3, 2)
             + SEPARATOR_DEFAULT
             + bizNo.substr(5);
    }
};


/**
 * String을 우편번호 구분자가 추가된 형식으로 반환
 */
String.prototype.maskZipNo = function() {
	var zipNo = this.removeAll(SEPARATOR_DEFAULT);

    if (zipNo.length <= 3) {
        return zipNo;
    } else {
        return zipNo.substr(0, 3)
             + SEPARATOR_DEFAULT
             + zipNo.substr(3);
    }
};

/**
 * String을 "-" 구분자가 추가된 특정 형식으로 반환
 */
String.prototype.maskFormat = function() {
	var formatValue = this.removeAll(SEPARATOR_DEFAULT);
    var formatStr = arguments[0].split("-");

    var resultStr = "";
    var resultIdx = 0;
    for (var _i=0; _i<formatStr.length; _i++) {
        if ((resultIdx+parseInt(formatStr[_i])) >= formatValue.length) {
            if (_i==0) {
                resultStr += formatValue.substring(resultIdx, resultIdx+parseInt(formatStr[_i]));
            } else {
                resultStr += "-" + formatValue.substring(resultIdx, resultIdx+parseInt(formatStr[_i]));
            }
            return resultStr;
        } else {
            if (_i==0) {
                resultStr += formatValue.substring(resultIdx, resultIdx+parseInt(formatStr[_i]));
            } else {
                resultStr += "-" + formatValue.substring(resultIdx, resultIdx+parseInt(formatStr[_i]));
            }
            resultIdx += parseInt(formatStr[_i]);
        }
    }
    return resultStr;
};

/**
 * URLBuiler Object
 */
function URLBuilder(url) {
    this.url      = url;
    this.paramMap = new Map();

    this.add = function(name, value) {
        this.paramMap.put(name, value);
    };


    this.get = function(name) {
        return this.paramMap.get(name);
    };


    this.remove = function(name) {
        return this.paramMap.remove(name);
    };


    this.clear = function() {
        return this.paramMap.clear();
    };


    this.queryString = function() {
        return this.paramMap.toString();
    };


    this.build = function() {
        return this.url + "?" + this.queryString();
    };
}

function getIndex(_obj){
	return _obj.parent().children().index(_obj.get(0)); 
	
}
function dateChange(strDate,strTemp,strSin){
    if ( strDate == null || strTemp.length != 9 ) return;
    var strYY = strTemp.substring(0,3);
    var strMM = strTemp.substring(3,6);
    var strDD = strTemp.substring(6,9);
    var date1 = null;
    if ( typeof(strDate) == "string" ) {
        strDate   = strDate.removeAll("-");
        var date1 = new Date();
        date1.setFullYear(strDate.substring(0,4));
        date1.setMonth   (parseInt(strDate.substring(4,6)) - 1);
        date1.setDate    (strDate.substring(6,8));
    } else {
        date1 = new Date(strDate.getFullYear(),strDate.getMonth(), strDate.getDate());
    }
    var date2 = new Date(parseInt(strYY),parseInt(strMM),parseInt(strDD));
    var date3 = new Date(0,0,0);
    var date4 = new Date(date2 - date3);
    var date5 = date1 - date4;
    var date6 = new Date(date5);
    var year  = (date6.getFullYear()).toString();
    var month = (date6.getMonth() + 1).toString();
    var day   = (date6.getDate()).toString();
    if (month .length  < 2) month  = "0" + month ;
    if (day .length    < 2) day    = "0" + day ;
    return year + "-" + month + "-" + day ;    
}

function dateComp(strDate1,strDate2,ymdType){
    if ( strDate1 == null || strDate2 == null ) return;
    var date1 = null;
    var date2 = null;
    if ( typeof(strDate1) == "string") {
        strDate1   = strDate1.removeAll("-");
        date1 = new Date();
        date1.setFullYear(strDate1.substring(0,4));
        date1.setMonth   (parseInt(strDate1.substring(4,6)) - 1);
        date1.setDate    (strDate1.substring(6,8));
    } else {
    	date1 = new Date(strDate1);
    }
    
    if ( typeof(strDate1) == "string") {
        strDate2   = strDate2.removeAll("-");
        date2 = new Date();
        date2.setFullYear(strDate2.substring(0,4));
        date2.setMonth   (parseInt(strDate2.substring(4,6)) - 1);
        date2.setDate    (strDate2.substring(6,8));
    } else {
    	date2 = new Date(strDate2);
    }
    var dateComp = date2 - date1;
    if ( ymdType == null) {
    	ymdType = "D";
    }
    var rtnVal = 0;
    if ( ymdType == "D" ) {
    	rtnVal = dateComp / (60000 * 60 * 24);
    } else if ( ymdType == "M"  ) {
    	rtnVal = dateComp / (60000 * 60 * 24 * 30);
    } else if ( ymdType == "Y"  ) {
    	rtnVal = dateComp / (60000 * 60 * 24 * 365);
    } else if ( ymdType == "HH" ) {
    	rtnVal = dateComp / (60000 * 60);
    } else if ( ymdType == "MI" ) {
    	rtnVal = dateComp / (60000);
    } else if ( ymdType == "SS" ) {
    	rtnVal = dateComp / (1000);
    	
    }

    return rtnVal;
}
/* 시스템 일자 가지고 오기;
 * */
Date.prototype.sysdate = function(){
    var jsonData;
    $.ajax({
      dataType: "json",
      url: servicePath + "/sysdate",
      async: false,
      success: function(data){_json = _json.tot.to_date}
    });    
	return to_date.toDate();
};
Date.prototype.formatDate = function(format) {
    var date = this;
    if (!format) {
    	format="yyyy-MM-dd";
    }
 
    var month = date.getMonth() + 1;
    var year = date.getFullYear();    
 
    
    format = format.replaceAll("MM",month.toString().lpad(2,"0"));        
 
    if (format.indexOf("yyyy") > -1) {
        format = format.replace("yyyy",year.toString());
    } else if (format.indexOf("yy") > -1) {
        format = format.replace("yy",year.toString().substr(2,2));
    }
 
    format = format.replace("dd",date.getDate().toString().lpad(2,"0"));
 
    var hours = date.getHours();       
    if (format.indexOf("t") > -1) {
       if (hours > 11) {
    	   format = format.replace("t","pm");
       } else {
    	   format = format.replace("t","am");
       }
    }
    if (format.indexOf("HH") > -1)
        format = format.replace("HH",hours.toString().lpad(2,"0"));
    if (format.indexOf("hh") > -1) {
        if (hours > 12) hours - 12;
        if (hours == 0) hours = 12;
        format = format.replace("hh",hours.toString().lpad(2,"0"));        
    }
    if (format.indexOf("mm") > -1)
       format = format.replace("mm",date.getMinutes().toString().lpad(2,"0"));
    if (format.indexOf("ss") > -1)
       format = format.replace("ss",date.getSeconds().toString().lpad(2,"0"));
    return format;
};



/*
 * String 암호화
 */
String.prototype.setEncrypted = function(rsaPublicKeyModulus, rsaPpublicKeyExponent) {
	try {
	    var rsa = new RSAKey();
	    rsa.setPublic(rsaPublicKeyModulus, rsaPpublicKeyExponent);
	    return rsa.encrypt(this);
	} catch (e) {
	    return this;
	}
	
};


/*
 * Copyright (C) 2006 Baron Schwartz <baron at sequent dot org>
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, version 2.1.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 *
 * $Revision: 1.3 $
 */

// Abbreviations: LODP = Left Of Decimal Point, RODP = Right Of Decimal Point
Number.formatFunctions = {count:0};

// Constants useful for controlling the format of numbers in special cases.
Number.prototype.NaN         = 'NaN';
Number.prototype.posInfinity = 'Infinity';
Number.prototype.negInfinity = '-Infinity';
Number.prototype.numberFormatHtml = function(format, context) {
	var rtnVal = "";
	if ( this == 0 ) {
		rtnVal = "<div style='" + styleHtml + "'>&nbsp;</div>";
	} else {
		var styleHtml = "";
		if ( this > 0 ) {
			styleHtml = "";
		} else {
			styleHtml = "color:#FF0000;";
		}
		rtnVal = "<div style='" + styleHtml + "'>" + this.numberFormat(format, context) + "</div>";
	}
    return rtnVal;
};

Number.prototype.numberFormat = function(format, context) {
    if (isNaN(this) ) {
        return Number.prototype.NaNstring;
    }
    else if (this == +Infinity ) {
        return Number.prototype.posInfinity;
    }
    else if ( this == -Infinity) {
        return Number.prototype.negInfinity;
    }
    else if (Number.formatFunctions[format] == null) {
        Number.createNewFormat(format);
    }
    return this[Number.formatFunctions[format]](context);
};

Number.createNewFormat = function(format) {
    var funcName = "format" + Number.formatFunctions.count++;
    Number.formatFunctions[format] = funcName;
    var code = "Number.prototype." + funcName + " = function(context){\n";

    // Decide whether the function is a terminal or a pos/neg/zero function
    var formats = format.split(";");
    switch (formats.length) {
        case 1:
            code += Number.createTerminalFormat(format);
            break;
        case 2:
            code += "return (this < 0) ? this.numberFormat(\""
                + String.escape(formats[1])
                + "\", 1) : this.numberFormat(\""
                + String.escape(formats[0])
                + "\", 2);";
            break;
        case 3:
            code += "return (this < 0) ? this.numberFormat(\""
                + String.escape(formats[1])
                + "\", 1) : ((this == 0) ? this.numberFormat(\""
                + String.escape(formats[2])
                + "\", 2) : this.numberFormat(\""
                + String.escape(formats[0])
                + "\", 3));";
            break;
        default:
            code += "throw 'Too many semicolons in format string';";
            break;
    }
    eval(code + "}");
};

Number.createTerminalFormat = function(format) {
    // If there is no work to do, just return the literal value
    if (format.length > 0 && format.search(/[0#?]/) == -1) {
        return "return '" + String.escape(format) + "';\n";
    }
    // Negative values are always displayed without a minus sign when section separators are used.
    var code = "var val = (context == null) ? new Number(this) : Math.abs(this);\n";
    var thousands = false;
    var lodp = format;
    var rodp = "";
    var ldigits = 0;
    var rdigits = 0;
    var scidigits = 0;
    var scishowsign = false;
    var sciletter = "";
    // Look for (and remove) scientific notation instructions, which can be anywhere
    m = format.match(/\..*(e)([+-]?)(0+)/i);
    if (m) {
        sciletter = m[1];
        scishowsign = (m[2] == "+");
        scidigits = m[3].length;
        format = format.replace(/(e)([+-]?)(0+)/i, "");
    }
    // Split around the decimal point
    var m = format.match(/^([^.]*)\.(.*)$/);
    if (m) {
        lodp = m[1].replace(/\./g, "");
        rodp = m[2].replace(/\./g, "");
    }
    // Look for %
    if (format.indexOf('%') >= 0) {
        code += "val *= 100;\n";
    }
    // Look for comma-scaling to the left of the decimal point
    m = lodp.match(/(,+)(?:$|[^0#?,])/);
    if (m) {
        code += "val /= " + Math.pow(1000, m[1].length) + "\n;";
    }
    // Look for comma-separators
    if (lodp.search(/[0#?],[0#?]/) >= 0) {
        thousands = true;
    }
    // Nuke any extraneous commas
    if ((m) || thousands) {
        lodp = lodp.replace(/,/g, "");
    }
    // Figure out how many digits to the l/r of the decimal place
    m = lodp.match(/0[0#?]*/);
    if (m) {
        ldigits = m[0].length;
    }
    m = rodp.match(/[0#?]*/);
    if (m) {
        rdigits = m[0].length;
    }
    // Scientific notation takes precedence over rounding etc
    if (scidigits > 0) {
        code += "var sci = Number.toScientific(val,"
            + ldigits + ", " + rdigits + ", " + scidigits + ", " + scishowsign + ");\n"
            + "var arr = [sci.l, sci.r];\n";
    }
    else {
        // If there is no decimal point, round to nearest integer, AWAY from zero
        if (format.indexOf('.') < 0) {
            code += "val = (val > 0) ? Math.ceil(val) : Math.floor(val);\n";
        }
        // Numbers are rounded to the correct number of digits to the right of the decimal
        code += "var arr = val.round(" + rdigits + ").toFixed(" + rdigits + ").split('.');\n";
        // There are at least "ldigits" digits to the left of the decimal, so add zeros if needed.
        code += "arr[0] = (val < 0 ? '-' : '') + (val < 0 ? arr[0].substring(1) : arr[0]).lpad("
            + ldigits + ", '0');\n";
    }
    // Add thousands separators
    if (thousands) {
        code += "arr[0] = Number.addSeparators(arr[0]);\n";
    }
    // Insert the digits into the formatting string.  On the LHS, extra digits are copied
    // into the result.  On the RHS, rounding has chopped them off.
    code += "arr[0] = Number.injectIntoFormat(arr[0].reverse(), '"
        + String.escape(lodp.reverse()) + "', true).reverse();\n";
    if (rdigits > 0) {
        code += "arr[1] = Number.injectIntoFormat(arr[1], '" + String.escape(rodp) + "', false);\n";
    }
    if (scidigits > 0) {
        code += "arr[1] = arr[1].replace(/(\\d{" + rdigits + "})/, '$1" + sciletter + "' + sci.s);\n";
    }
    return code + "return arr.join('.');\n";
};

Number.toScientific = function(val, ldigits, rdigits, scidigits, showsign) {
    var result = {l:"", r:"", s:""};
    var ex = "";
    // Make ldigits + rdigits significant figures
    var before = Math.abs(val).toFixed(ldigits + rdigits + 1).trim('0');
    // Move the decimal point to the right of all digits we want to keep,
    // and round the resulting value off
    var after = Math.round(new Number(before.replace(".", "").replace(
        new RegExp("(\\d{" + (ldigits + rdigits) + "})(.*)"), "$1.$2"))).toFixed(0);
    // Place the decimal point in the new string
    if (after.length >= ldigits) {
        after = after.substring(0, ldigits) + "." + after.substring(ldigits);
    }
    else {
        after += '.';
    }
    // Find how much the decimal point moved.  This is #places to LODP in the original
    // number, minus the #places in the new number.  There are no left-padded zeroes in
    // the new number, so the calculation for it is simpler than for the old number.
    result.s = (before.indexOf(".") - before.search(/[1-9]/)) - after.indexOf(".");
    // The exponent is off by 1 when it gets moved to the left.
    if (result.s < 0) {
        result.s++;
    }
    // Split the value around the decimal point and pad the parts appropriately.
    result.l = (val < 0 ? '-' : '') + after.substring(0, after.indexOf(".")).lpad(ldigits, "0");
    result.r = after.substring(after.indexOf(".") + 1);
    if (result.s < 0) {
        ex = "-";
    }
    else if (showsign) {
        ex = "+";
    }
    result.s = ex + Math.abs(result.s).toFixed(0).lpad( scidigits, "0");
    return result;
};

Number.prototype.round = function(decimals) {
    if (decimals > 0) {
        var m = this.toFixed(decimals + 1).match(
            new RegExp("(-?\\d*)\.(\\d{" + decimals + "})(\\d)\\d*$"));
        if (m && m.length) {
            return new Number(m[1] + "." + String(Math.round(m[2] + "." + m[3])).lpad(decimals, "0"));
        }
    }
    return this;
};

Number.injectIntoFormat = function(val, format, stuffExtras) {
    var i = 0;
    var j = 0;
    var result = "";
    var revneg = val.charAt(val.length - 1) == '-';
    if ( revneg ) {
       val = val.substring(0, val.length - 1);
    }
    while (i < format.length && j < val.length && format.substring(i).search(/[0#?]/) >= 0) {
        if (format.charAt(i).match(/[0#?]/)) {
            // It's a formatting character; copy the corresponding character
            // in the value to the result
            if (val.charAt(j) != '-') {
                result += val.charAt(j);
            }
            else {
                result += "0";
            }
            j++;
        }
        else {
            result += format.charAt(i);
        }
        ++i;
    }
    if ( revneg && j == val.length ) {
        result += '-';
    }
    if (j < val.length) {
        if (stuffExtras) {
            result += val.substring(j);
        }
        if ( revneg ) {
             result += '-';
        }
    }
    if (i < format.length) {
        result += format.substring(i);
    }
    return result.replace(/#/g, "").replace(/\?/g, " ");
};

Number.addSeparators = function(val) {
    return val.reverse().replace(/(\d{3})/g, "$1,").reverse().replace(/^(-)?,/, "$1");
};

String.escape = function(string) {
    return string.replace(/('|\\)/g, "\\$1");
};

