package com.pradeep.form.simpleforms

import android.content.Context
import android.content.Intent
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
        sectionedForms["Personal Information"] = ConstructFormData.getSection1FormData()
        sectionedForms["Education"] = ConstructFormData.getSection2FormData()
        sectionedForms["Family"] = ConstructFormData.getSection3FormData()

        binding.simpleForm.setData(
            sectionedForms, this, intent.getBooleanExtra(
                ARGS_SHOW_ONE_SECTION_AT_A_TIME, false
            )
        )
    }

    override fun onFormSubmitted(forms: List<Form>) {

    }

    companion object {
        private const val ARGS_SHOW_ONE_SECTION_AT_A_TIME = "SHOW_ONE_SECTION_AT_A_TIME"
        fun newIntent(context: Context, showOnSectionAtATime: Boolean): Intent {
            val intent = Intent(context, MainActivity2::class.java)
            intent.putExtra(ARGS_SHOW_ONE_SECTION_AT_A_TIME, showOnSectionAtATime)
            return intent
        }
    }
}