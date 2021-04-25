package com.pradeep.form.simple_form.form_items

import android.text.InputType
import androidx.core.widget.doAfterTextChanged
import com.pradeep.form.simple_form.SimpleFormAdapter
import com.pradeep.form.simple_form.databinding.ItemNumberInputBinding
import com.pradeep.form.simple_form.model.Form
import com.pradeep.form.simple_form.utils.SimpleFormUtils


class NumberInputFormItem(
    private val binding: ItemNumberInputBinding,
    private val adapter: SimpleFormAdapter
) :
    BaseFormItem(binding.root, adapter) {

    override fun bind(form: Form) {
        binding.editAnswer.filters = emptyArray()
        binding.inputAnswer.isCounterEnabled = false

        when (form.numberInputType) {
            NumberInputType.NUMBER -> {
                binding.editAnswer.inputType = InputType.TYPE_CLASS_NUMBER
            }
            NumberInputType.DECIMAL_NUMBER -> {
                binding.editAnswer.inputType =
                    InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            }
            NumberInputType.PHONE_NUMBER -> {
                binding.editAnswer.inputType = InputType.TYPE_CLASS_PHONE
            }
        }

        form.hint?.let {
            binding.inputAnswer.hint = it
        }

        form.answer?.let { answer ->
            if (answer.isNotBlank()) {
                binding.editAnswer.setText(answer)
            } else {
                binding.editAnswer.text = null
                binding.inputAnswer.isErrorEnabled=false
            }
        } ?: run {
            binding.editAnswer.text = null
            binding.inputAnswer.isErrorEnabled=false
        }

        binding.editAnswer.doAfterTextChanged { input ->
            adapter.getData()[adapterPosition].apply {
                answer = input.toString()
                if (form.isMandatory && answer.isNullOrBlank()) {
                    binding.inputAnswer.error = form.errorMessage
                } else {
                    binding.inputAnswer.isErrorEnabled=false
                }
            }
        }

        if (!form.isValid) {
            binding.editAnswer.requestFocus()
            form.apply {
                if (numberInputType == NumberInputType.PHONE_NUMBER) {
                    if (isMandatory && answer.isNullOrBlank()) {
                        binding.inputAnswer.error = errorMessage
                    } else if (isMandatory && !answer.isNullOrBlank() && !SimpleFormUtils.isValidPhoneNumber(
                            countryCode,
                            answer
                        )
                    ) {
                        binding.inputAnswer.error = errorMessage
                    } else if (!answer.isNullOrBlank() && !SimpleFormUtils.isValidPhoneNumber(
                            countryCode,
                            answer
                        )
                    ) {
                        binding.inputAnswer.error = errorMessage
                    } else {
                        binding.inputAnswer.isErrorEnabled=false
                    }
                } else {
                    if (isMandatory && answer.isNullOrBlank()) {
                        binding.inputAnswer.error = errorMessage
                    } else {
                        binding.inputAnswer.isErrorEnabled=false
                    }
                }
            }
        } else {
            binding.inputAnswer.isErrorEnabled=false
        }
    }

}