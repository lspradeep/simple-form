package com.pradeep.form.simple_form.form_items

import android.text.InputType
import androidx.core.widget.doAfterTextChanged
import com.pradeep.form.simple_form.SimpleFormAdapter
import com.pradeep.form.simple_form.databinding.ItemNumberInputBinding
import com.pradeep.form.simple_form.model.Form

class NumberInputFormItem(
    private val binding: ItemNumberInputBinding,
    private val adapter: SimpleFormAdapter
) :
    BaseFormItem(binding.root, adapter) {

    override fun bind(form: Form) {
        binding.editAnswer.inputType = InputType.TYPE_NULL
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
                binding.inputAnswer.error = null
            }
        } ?: run {
            binding.editAnswer.text = null
            binding.inputAnswer.error = null
        }

        binding.editAnswer.doAfterTextChanged { s ->
            adapter.getData()[adapterPosition].apply {
                val input = s.toString()
                when (form.numberInputType) {
                    NumberInputType.NUMBER -> {
                        answer = if (input.toLongOrNull() != null && input.toLong() < Long.MAX_VALUE) {
                                input.toLong().toString()
                            } else {
                                null
                            }
                    }
                    NumberInputType.DECIMAL_NUMBER -> {
                        answer = if (input.toDoubleOrNull() != null && input.toDouble() < Double.MAX_VALUE) {
                                input.toDouble().toString()
                            } else {
                                null
                            }
                    }
                    NumberInputType.PHONE_NUMBER -> {
                        answer = input
                    }
                }
                answer = input
                if (form.isMandatory && answer.isNullOrBlank()) {
                    binding.inputAnswer.error = form.errorMessage
                } else {
                    binding.inputAnswer.error = null
                }
            }
        }

        if (!form.isValid) {
            binding.editAnswer.requestFocus()
            form.apply {
                if (isMandatory && answer.isNullOrBlank()) {
                    binding.inputAnswer.error = errorMessage
                } else {
                    binding.inputAnswer.error = null
                }
            }
        } else {
            binding.inputAnswer.error = null
        }
    }

}