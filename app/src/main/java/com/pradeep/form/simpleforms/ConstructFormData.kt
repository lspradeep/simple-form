package com.pradeep.form.simpleforms

import com.pradeep.form.simple_form.form_items.FormTypes
import com.pradeep.form.simple_form.form_items.NumberInputType
import com.pradeep.form.simple_form.form_items.SingleLineTextType
import com.pradeep.form.simple_form.model.Form

object ConstructFormData {
    fun getFormData(): List<Form> {
        val forms = mutableListOf<Form>()
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "Name",
                hint = "please enter your name",
                singleLineTextType = SingleLineTextType.TEXT,
            )
        )
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "Email",
                hint = "please enter your Email address",
                singleLineTextType = SingleLineTextType.EMAIL_ADDRESS,
            )
        )
        forms.add(
            Form(
                formType = FormTypes.MULTI_LINE_TEXT,
                question = "Bio",
                description = "tell us about you",
                hint = "I'm from India. I love ice cream.",
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
            )
        )

        forms.add(
            Form(
                formType = FormTypes.NUMBER,
                question = "Do you earn or still studying",
                description = "if yes, care to tell us how much you earn per year?",
                numberInputType = NumberInputType.NUMBER
            )
        )
        forms.add(
            Form(
                formType = FormTypes.NUMBER,
                question = "What's  your score in college",
                numberInputType = NumberInputType.DECIMAL_NUMBER,
            )
        )
        forms.add(
            Form(
                formType = FormTypes.NUMBER,
                question = "Phone number",
                hint = "please provide your phone number",
                numberInputType = NumberInputType.PHONE_NUMBER
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
                isMandatory = true
            )
        )
        forms.add(
            Form(
                formType = FormTypes.NUMBER,
                question = "Age",
                hint = "please enter your age",
                numberInputType = NumberInputType.NUMBER,
                isMandatory = true
            )
        )
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "Email",
                hint = "please enter your Email address",
                singleLineTextType = SingleLineTextType.EMAIL_ADDRESS,
                isMandatory = true
            )
        )
        forms.add(
            Form(
                formType = FormTypes.NUMBER,
                question = "Phone number",
                hint = "please provide your phone number",
                numberInputType = NumberInputType.PHONE_NUMBER,
                isMandatory = true
            )
        )
        forms.add(
            Form(
                formType = FormTypes.SINGLE_CHOICE,
                question = "Gender",
                hint = "please provide your gender",
                choices = listOf("Male", "Female", "Other"),
                isMandatory = true
            )
        )
        forms.add(
            Form(
                formType = FormTypes.MULTI_CHOICE,
                question = "Among these which represents you the most ?",
                hint = "you can choose more than one option",
                choices = listOf("Cricket", "Chess", "PUBG")
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
                isMandatory = true
            )
        )
        forms.add(
            Form(
                formType = FormTypes.NUMBER,
                question = "Your score in college",
                hint = "example: 7.6",
                numberInputType = NumberInputType.NUMBER,
                isMandatory = true
            )
        )
        forms.add(
            Form(
                formType = FormTypes.MULTI_LINE_TEXT,
                question = "Describe about the projects you worked on",
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
                isMandatory = true
            )
        )
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "Mother's Name",
                hint = "please enter your mother's name",
                singleLineTextType = SingleLineTextType.TEXT,
                isMandatory = true
            )
        )
        return forms
    }
}