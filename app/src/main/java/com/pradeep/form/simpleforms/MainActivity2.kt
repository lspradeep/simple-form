package com.pradeep.form.simpleforms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.pradeep.form.simple_form.form_items.FormTypes
import com.pradeep.form.simple_form.form_items.NumberInputType
import com.pradeep.form.simple_form.form_items.SingleLineTextType
import com.pradeep.form.simple_form.model.Form
import com.pradeep.form.simple_form.presentation.FormSubmitCallback
import com.pradeep.form.simpleforms.databinding.ActivityMain2Binding
import com.pradeep.form.simpleforms.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity(), FormSubmitCallback {
    private lateinit var binding: ActivityMain2Binding
    private var sectionedForms = mutableMapOf<String, List<Form>>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)
        setSectionedForm()
    }

    private fun setSectionedForm() {
        sectionedForms["Section One"] = getForms()
        sectionedForms["Section Two"] = getForms()
        binding.simpleForm.setData(sectionedForms, this)
//        binding.simpleForm.setData(getForms())
    }

    private fun getForms(): List<Form> {
        val forms = mutableListOf<Form>()
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "Single Line Text",
                hint = "hint 1",
                charLimit = 4,
            )
        )
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "Provide Email address",
                description = "description 1",
                hint = "hint 1",
                singleLineTextType = SingleLineTextType.EMAIL_ADDRESS
            )
        )
        forms.add(
            Form(
                formType = FormTypes.MULTI_LINE_TEXT,
                question = "Multi Line Text",
                description = "description 2",
                hint = "hint 2",
                charLimit = 150,
                showCharLimitCounter = true
            )
        )
        forms.add(
            Form(
                formType = FormTypes.SINGLE_CHOICE,
                question = "Single choice",
                description = "description 3",
                hint = "hint 3",
                choices = listOf(
                    "one",
                    "two",
                    "3",
                    "one",
                    "two",
                    "3"
                )
            )
        )
        forms.add(
            Form(
                formType = FormTypes.MULTI_CHOICE,
                question = "Multi choice",
                description = "description 3",
                hint = "hint 3",
                choices = listOf(
                    "one",
                    "two",
                    "3",
                    "one",
                    "two",
                    "3",
                    "one"
                ),
            )
        )
        forms.add(
            Form(
                isMandatory = true,
                formType = FormTypes.NUMBER,
                question = "Number",
                description = "description 3",
                hint = "hint 3",
                numberInputType = NumberInputType.NUMBER
            )
        )
        forms.add(
            Form(
                isMandatory = true,
                formType = FormTypes.NUMBER,
                question = "Decimal number",
                description = "description 3",
                hint = "hint 3",
                numberInputType = NumberInputType.DECIMAL_NUMBER,
            )
        )
        forms.add(
            Form(
                isMandatory = true,
                formType = FormTypes.NUMBER,
                question = "Phone number",
                description = "description 3",
                hint = "hint 3",
                numberInputType = NumberInputType.PHONE_NUMBER
            )
        )
        return forms
    }

    override fun onFormSubmitted(forms: List<Form>) {

    }
}