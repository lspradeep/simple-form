package com.pradeep.form.simpleforms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.pradeep.form.simple_form.model.Form
import com.pradeep.form.simple_form.utils.FormSubmitCallback
import com.pradeep.form.simpleforms.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FormSubmitCallback {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.simpleForm.setData(this, ConstructFormData.getFormData(), this)
    }

    override fun onFormSubmitted(forms: List<Form>) {
        val formOutput = mutableListOf<FormOutput>()
        forms.forEach {
            formOutput.add(FormOutput(it.question, it.answer, it.answers, it.formType))
        }
        startActivity(FormOutputDisplayActivity.newIntent(this, formOutput))
    }
}