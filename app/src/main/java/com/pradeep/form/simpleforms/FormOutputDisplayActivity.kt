package com.pradeep.form.simpleforms

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.databinding.DataBindingUtil
import com.pradeep.form.simple_form.form_items.FormTypes
import com.pradeep.form.simple_form.model.Form
import com.pradeep.form.simple_form.utils.SimpleFormUtils
import com.pradeep.form.simpleforms.databinding.ActivityFormOutputDisplayBinding
import java.util.ArrayList

class FormOutputDisplayActivity : AppCompatActivity() {


    private lateinit var binding: ActivityFormOutputDisplayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_form_output_display)
        val stringBuilder = StringBuilder()
        intent.getParcelableArrayListExtra<FormOutput>(ARGS_FORMS)?.let { forms ->
            forms.forEach {
                if (it.formType == FormTypes.NONE) {
                    stringBuilder.append("\n")
                    stringBuilder.append("\n")
                    //stringBuilder.append(it.sectionTitle)
                    stringBuilder.append("\n")
                    stringBuilder.append("\n")
                } else if (it.formType == FormTypes.MULTI_CHOICE) {
                    stringBuilder.append(
                        "${it.question} - ${
                            SimpleFormUtils.convertListToSingleString(
                                it.answers ?: listOf()
                            )
                        }"
                    )
                    stringBuilder.append("\n")
                } else {
                    stringBuilder.append(
                        "${it.question} - ${it.answer}"
                    )
                    stringBuilder.append("\n")
                }

            }

            binding.textOutput.text = stringBuilder
        } ?: run {
            binding.textOutput.text = "No Output"
        }
    }

    companion object {
        private const val ARGS_FORMS = "FORMS"
        fun newIntent(context: Context, forms: List<FormOutput>): Intent {
            val intent = Intent(context, FormOutputDisplayActivity::class.java)
            intent.putParcelableArrayListExtra(ARGS_FORMS, forms as ArrayList<out Parcelable>)
            return intent
        }
    }
}