package com.pradeep.form.simple_form.model

import com.pradeep.form.simple_form.form_items.FormTypes
import com.pradeep.form.simple_form.form_items.NumberInputType
import com.pradeep.form.simple_form.form_items.SingleLineTextType
import com.pradeep.form.simple_form.utils.SimpleFormUtils.isEmailValid
import java.lang.Exception
import java.util.*

data class Form(
    val formType: Int = FormTypes.NONE,
    val question: String? = null,
    val choices: List<String>? = null,
    val description: String? = null,
    val hint: String? = null,
    var answer: String? = null,
    var answers: List<String>? = null,
    val isMandatory: Boolean = false,
    val sectionTitle: String? = null,
    val singleLineTextType: SingleLineTextType = SingleLineTextType.TEXT,
    val numberInputType: NumberInputType = NumberInputType.NUMBER,
    val charLimit: Int = -1,
    val showCharLimitCounter: Boolean = false,
    val errorMessage: String = "Please provide an answer",
    var formValidationListener: String? = null
) {
    val id: String = UUID.randomUUID().toString()
    var isSectionTitle: Boolean = false
    var isValid = true

    init {
        check()
    }

    private fun check() {
        if (charLimit == 0) {
            throw Exception("'charLimit': use -1 for no limit or use value greater than 0")
        }
        if (formType == FormTypes.NONE && sectionTitle.isNullOrBlank()) {
            throw Exception("please provide a 'sectionTitle'")
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
            isValid = !(isMandatory && answer.isNullOrBlank())
            return isValid
        }

        return false
    }
}