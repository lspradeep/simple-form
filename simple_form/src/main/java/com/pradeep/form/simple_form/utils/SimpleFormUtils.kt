package com.pradeep.form.simple_form.utils

import android.text.TextUtils
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource
import com.pradeep.form.simple_form.form_items.FormTypes
import com.pradeep.form.simple_form.model.Form
import java.text.SimpleDateFormat
import java.util.*


object SimpleFormUtils {
    const val UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

    fun convertMapToList(sectionedForm: Map<String, List<Form>>): List<Form> {
        val forms = mutableListOf<Form>()
        sectionedForm.forEach { entry: Map.Entry<String, List<Form>> ->
            val titleForm = Form(
                sectionTitle = entry.key,
                formType = FormTypes.NONE,
                errorMessage = ""
            )
            titleForm.sectionMapperId = titleForm.id
            forms.add(titleForm)

            entry.value.forEach { form: Form ->
                form.sectionMapperId = titleForm.id
                forms.add(form)
            }
        }
        return forms
    }

    fun constructSectionedFormOutput(
        sectionedFormOutput: Map<String, List<Form>>,
        sectionTitleIdPairs: List<Pair<String, String>>
    ): List<Form> {
        val forms = mutableListOf<Form>()
        sectionTitleIdPairs.forEach { titlePair ->
            sectionedFormOutput[titlePair.first]?.let {
                it.forEach { form ->
                    forms.add(form)
                }
            }
        }
        return forms
    }

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

    fun isValidPhoneNumber(countryCode: String?, phoneNumber: String?): Boolean {
        if (countryCode.isNullOrBlank()) {
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