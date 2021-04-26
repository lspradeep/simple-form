package com.pradeep.form.simple_form.model

import android.os.Parcelable
import com.pradeep.form.simple_form.form_items.FormTypes
import com.pradeep.form.simple_form.utils.NumberInputType
import com.pradeep.form.simple_form.utils.SimpleFormUtils
import com.pradeep.form.simple_form.utils.SimpleFormUtils.isEmailValid
import com.pradeep.form.simple_form.utils.SingleLineTextType
import com.pradeep.form.simple_form.utils.TimeFormat
import kotlinx.android.parcel.Parcelize
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Form(
    val formType: Int = FormTypes.SINGLE_LINE_TEXT,
    val question: String? = null,
    val choices: List<String>? = null,
    val description: String? = null,
    val hint: String? = null,
    var answer: String? = null,
    var answers: List<String>? = null,
    val isMandatory: Boolean = false,
    var sectionTitle: String? = null,
    val singleLineTextType: SingleLineTextType = SingleLineTextType.TEXT,
    val numberInputType: NumberInputType = NumberInputType.NUMBER,
    var countryCode: String? = null,
    val charLimit: Int = -1,
    val dateDisplayFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyy", Locale.getDefault()),
    val timeDisplayFormat: SimpleDateFormat = SimpleDateFormat("hh:mm aaa", Locale.getDefault()),
    val timeFormat: TimeFormat = TimeFormat.FORMAT_12_HOURS,
    val errorMessage: String,
    var formValidationListener: String? = null,
    var sectionMapperId: String? = null
) : Parcelable {
    val id: String = UUID.randomUUID().toString()
    var isValid = true

    init {
        check()
    }

    private fun check() {
        if (charLimit == 0) {
            throw Exception("'charLimit': use -1 for no limit or use value greater than 0")
        }
        if (formType == FormTypes.NONE && sectionTitle.isNullOrBlank()) {
            throw Exception("'FormTypes.NONE' is used internally for creating section titles, when you set 'Map<String, List<Form>>' to 'SimpleFormView'")
        }
        if (formType == FormTypes.SINGLE_CHOICE && choices?.size ?: 0 == 0) {
            throw Exception("you mush provide 'choices' for 'FormTypes.SINGLE_CHOICE'")
        }
        if (formType == FormTypes.MULTI_CHOICE && choices?.size ?: 0 == 0) {
            throw Exception("you mush provide 'choices' for 'FormTypes.MULTI_CHOICE'")
        }
        if (formType == FormTypes.NUMBER && numberInputType == NumberInputType.PHONE_NUMBER && countryCode.isNullOrBlank()) {
            throw Exception("please provide country code to 'FormTypes.NUMBER' that has 'numberInputType = NumberInputType.PHONE_NUMBER' ")
        }
    }

    fun isFormItemValid(): Boolean {
        if (formType == FormTypes.NONE) {
            isValid = true
            return isValid
        }
        if (formType == FormTypes.SINGLE_LINE_TEXT && singleLineTextType == SingleLineTextType.TEXT) {
            isValid = !(isMandatory && answer.isNullOrBlank())
            return isValid
        }

        if (formType == FormTypes.SINGLE_LINE_TEXT && singleLineTextType == SingleLineTextType.EMAIL_ADDRESS) {

            isValid = if (isMandatory && answer.isNullOrBlank()) {
                false
            } else if (isMandatory && !answer.isNullOrBlank() && !answer.toString()
                    .isEmailValid()
            ) {
                false
            } else !(!answer.isNullOrBlank() && !answer.toString().isEmailValid())

            return isValid
        }

        if (formType == FormTypes.MULTI_LINE_TEXT) {
            isValid = !(isMandatory && answer.isNullOrBlank())
            return isValid
        }

        if (formType == FormTypes.SINGLE_CHOICE) {
            isValid = !(isMandatory && answer.isNullOrBlank())
            return isValid
        }

        if (formType == FormTypes.MULTI_CHOICE) {
            isValid = !(isMandatory && answers.isNullOrEmpty())
            return isValid
        }

        if (formType == FormTypes.NUMBER && numberInputType == NumberInputType.NUMBER) {
            isValid = !(isMandatory && answer.isNullOrBlank())
            return isValid
        }

        if (formType == FormTypes.NUMBER && numberInputType == NumberInputType.DECIMAL_NUMBER) {
            isValid = !(isMandatory && answer.isNullOrBlank())
            return isValid
        }

        if (formType == FormTypes.NUMBER && numberInputType == NumberInputType.PHONE_NUMBER) {
            isValid = if (isMandatory && answer.isNullOrBlank()) {
                false
            } else if (isMandatory && !answer.isNullOrBlank() && !SimpleFormUtils.isValidPhoneNumber(
                    countryCode,
                    answer
                )
            ) {
                false
            } else !(!answer.isNullOrBlank() && !SimpleFormUtils.isValidPhoneNumber(
                countryCode,
                answer
            ))
            return isValid
        }
        if (formType == FormTypes.DATE_PICKER) {
            isValid = !(isMandatory && answer.isNullOrBlank())
            return isValid
        }
        if (formType == FormTypes.TIME_PICKER) {
            isValid = !(isMandatory && answer.isNullOrBlank())
            return isValid
        }

        return false
    }
}