package com.pradeep.form.simple_form.form_items

import android.text.InputType
import androidx.core.widget.doAfterTextChanged
import com.pradeep.form.simple_form.SimpleFormAdapter
import com.pradeep.form.simple_form.databinding.ItemNumberInputBinding
import com.pradeep.form.simple_form.model.Form
import java.lang.Exception

class NumberInputFormItem(
    private val binding: ItemNumberInputBinding,
    private val adapter: SimpleFormAdapter
) :
    BaseFormItem(binding.root, adapter) {

    override fun bind(form: Form) {
        when (form.numberType) {
            NumberType.NUMBER -> {
                binding.editAnswer.inputType = InputType.TYPE_CLASS_NUMBER
            }
            NumberType.DECIMAL_NUMBER -> {
                binding.editAnswer.inputType =
                    InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            }
            NumberType.PHONE_NUMBER -> {
                binding.editAnswer.inputType = InputType.TYPE_CLASS_PHONE
            }
        }

        form.hint?.let {
            binding.inputAnswer.hint = it
        }

        form.answer?.let { answer ->
            try {
                if (answer.isNotBlank() && form.numberType == NumberType.NUMBER) {
                    if (answer.toIntOrNull() is Int && answer.toInt() < Int.MAX_VALUE) {
                        binding.editAnswer.setText(answer.toInt().toString())
                    } else if (answer.toLongOrNull() is Long && answer.toLong() < Long.MAX_VALUE) {
                        binding.editAnswer.setText(answer.toLong().toString())
                    } else {
                        binding.editAnswer.text = null
                        binding.inputAnswer.error = null
                    }
                } else if (answer.isNotBlank() && form.numberType == NumberType.DECIMAL_NUMBER && answer.toDoubleOrNull() != null &&
                    answer.toDouble() < Double.MAX_VALUE
                ) {
                    binding.editAnswer.setText(answer.toDouble().toString())
                } else if (answer.isNotBlank() && form.numberType == NumberType.PHONE_NUMBER) {
                    binding.editAnswer.setText(answer)
                } else {
                    binding.editAnswer.text = null
                    binding.inputAnswer.error = null
                }
            } catch (e: Exception) {
                binding.editAnswer.text = null
                binding.inputAnswer.error = null
            }
        } ?: run {
            binding.editAnswer.text = null
            binding.inputAnswer.error = null
        }

        binding.editAnswer.doAfterTextChanged {
            adapter.getData()[adapterPosition].apply {
                val input = it.toString()
                var output: String? = null
                try {
                    if (input.isNotBlank()) {
                        output = if (form.numberType == NumberType.NUMBER
                        ) {
                            if (input.toIntOrNull() is Int && input.toInt() < Int.MAX_VALUE) {
                                input.toInt().toString()
                            } else if (input.toLongOrNull() is Long && input.toLong() < Long.MAX_VALUE) {
                                input.toLong().toString()
                            } else {
                                null
                            }
                        } else if (form.numberType == NumberType.DECIMAL_NUMBER && input
                                .toDoubleOrNull() != null && input
                                .toDouble() < Double.MAX_VALUE
                        ) {
                            input.toDouble().toString()
                        } else if (form.numberType == NumberType.PHONE_NUMBER) {
                            input
                        } else {
                            null
                        }
                    } else {
                        output = null
                    }
                } catch (e: Exception) {
                    output = null
                }

                if (form.isMandatory && output.isNullOrBlank()) {
                    binding.inputAnswer.error = form.errorMessage
                } else {
                    binding.inputAnswer.error = null
                }
                answer = output
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