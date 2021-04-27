package com.pradeep.form.simpleforms

import com.pradeep.form.simple_form.form_items.FormTypes
import com.pradeep.form.simple_form.model.Form
import com.pradeep.form.simple_form.utils.NumberInputType
import com.pradeep.form.simple_form.utils.SingleLineTextType
import com.pradeep.form.simple_form.utils.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

object ConstructFormData {
    fun getFormData(): List<Form> {
        val forms = mutableListOf<Form>()
        forms.add(
            Form(
                formType = FormTypes.TIME_PICKER,
                question = "Time",
                hint = "please pick a time",
                errorMessage = "Please pick a time",
                timeFormat = TimeFormat.FORMAT_24_HOURS,
                isMandatory = true
            )
        )
        forms.add(
            Form(
                formType = FormTypes.DATE_PICKER,
                question = "Time",
                hint = "please pick a time",
                errorMessage = "Please pick a time",
                timeFormat = TimeFormat.FORMAT_24_HOURS,
                isMandatory = true
            )
        )
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "Name",
                hint = "please enter your name",
                singleLineTextType = SingleLineTextType.TEXT,
                errorMessage = "Please provide an answer"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "Email",
                hint = "please enter your Email address",
                singleLineTextType = SingleLineTextType.EMAIL_ADDRESS,
                errorMessage = "Please provide a valid email address"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.MULTI_LINE_TEXT,
                question = "Bio",
                description = "tell us about you",
                hint = "I'm from India. I love ice cream.",
                errorMessage = "Please provide an answer"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.SINGLE_CHOICE,
                question = "Gender",
                hint = "please choose your gender",
                choices = listOf(
                    "Male",
                    "Female",
                    "Others"
                ),
                errorMessage = "Please select an answer"
            )
        )

        forms.add(
            Form(
                formType = FormTypes.NUMBER,
                question = "Do you earn or still studying",
                description = "if yes, care to tell us how much you earn per year?",
                numberInputType = NumberInputType.NUMBER,
                errorMessage = "Please provide an answer"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.NUMBER,
                question = "What's  your score in college",
                numberInputType = NumberInputType.DECIMAL_NUMBER,
                errorMessage = "Please provide an answer"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.NUMBER,
                question = "Phone number",
                hint = "please provide your phone number",
                numberInputType = NumberInputType.PHONE_NUMBER,
                countryCode = "+91",
                errorMessage = "Please provide a valid phone number"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.MULTI_CHOICE,
                question = "Your favourite TV shows",
                description = "you can pick more than one",
                hint = "hint 3",
                choices = listOf(
                    "friends",
                    "tom and jerry",
                    "pokemon",
                    "rick and morty"
                ),
                errorMessage = "Please choose an answer"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.DATE_PICKER,
                question = "Date",
                hint = "please pick a date",
                errorMessage = "Please pick a date",
                dateDisplayFormat = SimpleDateFormat("MMMM/dd/yyyy", Locale.getDefault())
            )
        )
        return forms
    }

    fun getSection1FormData(): List<Form> {
        val forms = mutableListOf<Form>()
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "Name",
                hint = "please enter your name",
                singleLineTextType = SingleLineTextType.TEXT,
                errorMessage = "Please provide an answer"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.NUMBER,
                question = "Age",
                hint = "please enter your age",
                numberInputType = NumberInputType.NUMBER,
                errorMessage = "Please provide an answer"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "Email",
                hint = "please enter your Email address",
                singleLineTextType = SingleLineTextType.EMAIL_ADDRESS,
                errorMessage = "Please provide a valid email address"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.NUMBER,
                question = "Phone number",
                hint = "please provide your phone number",
                numberInputType = NumberInputType.PHONE_NUMBER,
                countryCode = "+91",
                errorMessage = "Please provide a valid phone number"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.SINGLE_CHOICE,
                question = "Gender",
                hint = "please provide your gender",
                choices = listOf("Male", "Female", "Other"),
                errorMessage = "Please select an answer"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.MULTI_CHOICE,
                question = "Among these which represents you the most ?",
                hint = "you can choose more than one option",
                choices = listOf("Cricket", "Chess", "PUBG"),
                errorMessage = "Please select an answer"
            )
        )
        return forms
    }

    fun getSection2FormData(): List<Form> {
        val forms = mutableListOf<Form>()
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "College Name",
                hint = "please enter your name",
                singleLineTextType = SingleLineTextType.TEXT,
                errorMessage = "Please provide an answer"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.NUMBER,
                question = "Your score in college",
                hint = "example: 7.6",
                numberInputType = NumberInputType.NUMBER,
                errorMessage = "Please provide an answer"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.MULTI_LINE_TEXT,
                question = "Describe about the projects you worked on",
                charLimit = 5,
                errorMessage = "Please provide an answer"
            )
        )
        return forms
    }

    fun getSection3FormData(): List<Form> {
        val forms = mutableListOf<Form>()
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "Father's Name",
                hint = "please enter your father's name",
                singleLineTextType = SingleLineTextType.TEXT,
                errorMessage = "Please provide an answer"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "Mother's Name",
                hint = "please enter your mother's name",
                singleLineTextType = SingleLineTextType.TEXT,
                errorMessage = "Please provide an answer"
            )
        )
        return forms
    }
}