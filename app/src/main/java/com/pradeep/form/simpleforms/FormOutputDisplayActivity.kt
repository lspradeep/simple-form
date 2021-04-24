package com.pradeep.form.simpleforms

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.databinding.DataBindingUtil
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
        intent.getParcelableArrayListExtra<Form>(ARGS_FORMS)?.let { forms ->
            forms.forEach {
                stringBuilder.append(
                    "${it.question} - ${it.answer} - ${
                        SimpleFormUtils.convertListToSingleString(
                            it.answers ?: listOf()
                        )
                    }"
                )
                stringBuilder.append("\n")
            }

            binding.textOutput.text = stringBuilder
        } ?: run {
            binding.textOutput.text = "No Output"
        }
    }

    companion object {
        private const val ARGS_FORMS = "FORMS"
        fun newIntent(context: Context, forms: List<Form>): Intent {
            val intent = Intent(context, FormOutputDisplayActivity::class.java)
            intent.putParcelableArrayListExtra(ARGS_FORMS, forms as ArrayList<out Parcelable>)
            return intent
        }
    }
}