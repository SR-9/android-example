package com.example.myapplication.ext

import java.security.MessageDigest
import java.util.regex.Pattern

val String.md5: String
    get() {
        val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }

val String.sha1: String
    get() {
        val bytes = MessageDigest.getInstance("SHA-1").digest(this.toByteArray())
        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }


fun String.isEmailValid(): Boolean {
    val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,8}$"
    val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}


// https://mvnrepository.com/artifact/com.googlecode.libphonenumber/libphonenumber
//compile group: 'com.googlecode.libphonenumber', name: 'libphonenumber', version: '3.5'
//import com.google.i18n.phonenumbers.PhoneNumberUtil
//fun String.formatPhoneNumber(region: String): String? {
//    val phoneNumberKit = PhoneNumberUtil.getInstance()
//    val number = phoneNumberKit.parse(this, region)
//    if (!phoneNumberKit.isValidNumber(number))
//        return null
//
//    return phoneNumberKit.format(number, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
//}


val String.containsLatinLetter: Boolean
    get() = matches(Regex(".*[A-Za-z].*"))

val String.containsDigit: Boolean
    get() = matches(Regex(".*[0-9].*"))

val String.isAlphanumeric: Boolean
    get() = matches(Regex("[A-Za-z0-9]*"))

val String.hasLettersAndDigits: Boolean
    get() = containsLatinLetter && containsDigit

val String.isIntegerNumber: Boolean
    get() = toIntOrNull() != null

val String.toDecimalNumber: Boolean
    get() = toDoubleOrNull() != null


val String.creditCardFormatted: String
    get() {
        val preparedString = replace(" ", "").trim()
        val result = StringBuilder()
        for (i in preparedString.indices) {
            if (i % 4 == 0 && i != 0) {
                result.append(" ")
            }
            result.append(preparedString[i])
        }
        return result.toString()
    }