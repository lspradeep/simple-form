package com.pradeep.form.simpleforms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.pradeep.form.simple_form.form_items.FormTypes
import com.pradeep.form.simple_form.form_items.NumberInputType
import com.pradeep.form.simple_form.form_items.SingleLineTextType
import com.pradeep.form.simple_form.model.Form
import com.pradeep.form.simple_form.presentation.FormSubmitCallback
import com.pradeep.form.simple_form.utils.SimpleFormUtils
import com.pradeep.form.simpleforms.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FormSubmitCallback {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.simpleForm.setData(getForms(),this)
    }

    private fun getForms(): List<Form> {
        val forms = mutableListOf<Form>()
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "Name",
                hint = "please enter your name",
                isMandatory = true,
                singleLineTextType = SingleLineTextType.TEXT,
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
                isMandatory = true
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
                isMandatory = true,
                formType = FormTypes.NUMBER,
                question = "What's  your score in college",
                numberInputType = NumberInputType.DECIMAL_NUMBER,
            )
        )
        forms.add(
            Form(
                isMandatory = true,
                formType = FormTypes.NUMBER,
                question = "Phone number",
                hint = "please provide your phone number",
                numberInputType = NumberInputType.PHONE_NUMBER
            )
        )
        return forms
    }

    override fun onFormSubmitted(forms: List<Form>) {
        startActivity(FormOutputDisplayActivity.newIntent(this, forms))
    }
}