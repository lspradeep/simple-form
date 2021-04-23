package com.pradeep.form.simple_form.utils

import android.text.TextUtils
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource


object SimpleFormUtils {
    fun convertListToSingleString(answers: List<String>): String {
        val builder = StringBuilder()
        answers.forEachIndexed { index, answer ->
            builder.append(answer)
            if (index != (answers.size - 1)) {
                builder.append(", ")
            }
        }
        return builder.toString()
    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
            .matches()
    }

    fun isValidPhoneNumber(countryCode: String, phoneNumber: String?): Boolean {
        if (countryCode.isBlank()) {
            throw Exception("Please provide a valid country code, for example '+91")
        }
        if (phoneNumber.isNullOrBlank()) {
            return false
        }
        val number = if (phoneNumber.contains(countryCode)) phoneNumber.replace(
            countryCode,
            ""
        ) else phoneNumber

        val phoneNumberUtil: PhoneNumberUtil = PhoneNumberUtil.getInstance()
        val phone: Phonenumber.PhoneNumber

        try {
            phone = phoneNumberUtil.parse(
                "${countryCode.trim()}${number.trim()}",
                CountryCodeSource.UNSPECIFIED.name
            )
        } catch (e: Exception) {
            return false
        }
        return phoneNumberUtil.isValidNumber(phone)
    }
}