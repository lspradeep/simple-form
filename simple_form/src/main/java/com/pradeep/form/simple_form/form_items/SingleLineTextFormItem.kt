package com.pradeep.form.simple_form.form_items

import android.text.InputFilter
import android.text.InputType
import androidx.core.widget.doAfterTextChanged
import com.pradeep.form.simple_form.SimpleFormAdapter
import com.pradeep.form.simple_form.databinding.ItemSingleLineTextBinding
import com.pradeep.form.simple_form.model.Form
import com.pradeep.form.simple_form.utils.SimpleFormUtils.isEmailValid

class SingleLineTextFormItem(
    private val binding: ItemSingleLineTextBinding,
    private val adapter: SimpleFormAdapter
) :
    BaseFormItem(binding.root, adapter) {

    override fun bind(form: Form) {
        binding.editAnswer.filters = arrayOf()

        if (form.singleLineTextType == SingleLineTextType.TEXT) {
            binding.editAnswer.inputType = InputType.TYPE_CLASS_TEXT
            if (form.showCharLimitCounter && form.charLimit != -1 && form.charLimit > 0) {
                binding.editAnswer.filters = arrayOf(InputFilter.LengthFilter(form.charLimit))
                binding.inputAnswer.counterMaxLength = form.charLimit
                binding.inputAnswer.isCounterEnabled = true
            } else {
                binding.inputAnswer.isCounterEnabled = false
            }
        } else if (form.singleLineTextType == SingleLineTextType.EMAIL_ADDRESS) {
            binding.inputAnswer.isCounterEnabled = false
            binding.editAnswer.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }


        form.hint?.let {
            binding.inputAnswer.hint = it
        }

        form.answer?.let { answer ->
            if (answer.isNotBlank()) {
                binding.editAnswer.setText(answer)
            } else {
                binding.editAnswer.text = null
                binding.inputAnswer.error = null
            }
        } ?: run {
            binding.editAnswer.text = null
            binding.inputAnswer.error = null
        }

        binding.editAnswer.doAfterTextChanged { input ->
            binding.inputAnswer.isCounterEnabled = false
            adapter.getData()[adapterPosition].apply {
                if (singleLineTextType == SingleLineTextType.TEXT) {
                    if (isMandatory && input.isNullOrBlank()) {
                        answer = null
                        binding.inputAnswer.error = errorMessage
                    } else {
                        answer = input.toString()
                        binding.inputAnswer.error = null
                    }

                } else if (singleLineTextType == SingleLineTextType.EMAIL_ADDRESS) {
                    if (isMandatory && input.isNullOrBlank()) {
                        binding.inputAnswer.error = "Please provide a valid email address"
                    } else if (isMandatory && !input.isNullOrBlank() && !input.toString()
                            .isEmailValid()
                    ) {
                        answer = input.toString()
                        binding.inputAnswer.error = "Please provide a valid email address"
                    } else if (!input.isNullOrBlank() && !input.toString()
                            .isEmailValid()
                    ) {
                        answer = input.toString()
                        binding.inputAnswer.error = "Please provide a valid email address"
                    } else {
                        answer = input.toString()
                        binding.inputAnswer.error = null
                    }
                }

            }
        }

        if (!form.isValid) {
            binding.editAnswer.requestFocus()
            form.apply {
                if (singleLineTextType == SingleLineTextType.TEXT) {
                    if (isMandatory && answer.isNullOrBlank()) {
                        binding.inputAnswer.error = errorMessage
                    } else {
                        binding.inputAnswer.error = null
                    }
                } else if (singleLineTextType == SingleLineTextType.EMAIL_ADDRESS) {
                    if (isMandatory && answer.isNullOrBlank()) {
                        binding.inputAnswer.error = "Please provide a valid email address"
                    } else if (isMandatory && !answer.isNullOrBlank() && !answer.toString()
                            .isEmailValid()
                    ) {
                        binding.inputAnswer.error = "Please provide a valid email address"
                    } else if (!answer.isNullOrBlank() && !answer.toString().isEmailValid()) {
                        binding.inputAnswer.error = "Please provide a valid email address"
                    } else {
                        binding.inputAnswer.error = null
                    }
                }
            }
        } else {
            binding.inputAnswer.error = null
        }
    }

}