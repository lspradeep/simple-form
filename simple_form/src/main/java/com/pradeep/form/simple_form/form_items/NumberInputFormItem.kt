package com.pradeep.form.simple_form.form_items

import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import androidx.core.widget.doAfterTextChanged
import com.pradeep.form.simple_form.SimpleFormAdapter
import com.pradeep.form.simple_form.databinding.ItemNumberInputBinding
import com.pradeep.form.simple_form.databinding.ItemSingleLineTextBinding
import com.pradeep.form.simple_form.model.Form
import com.pradeep.form.simple_form.utils.SimpleFormUtils.isEmailValid
import timber.log.Timber
import java.lang.Exception
import java.text.DecimalFormat

class NumberInputFormItem(
    private val binding: ItemNumberInputBinding,
    private val adapter: SimpleFormAdapter
) :
    BaseFormItem(binding.root, adapter) {

    override fun bind(form: Form) {
        binding.editAnswer.text = null
        binding.inputAnswer.error = null

        when (form.numberType) {
            NumberType.PHONE_NUMBER -> {
                binding.editAnswer.inputType = InputType.TYPE_CLASS_PHONE
            }
            NumberType.DECIMAL_NUMBER -> {
                binding.editAnswer.inputType =
                    InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            }
            else -> {
                binding.editAnswer.inputType = InputType.TYPE_CLASS_NUMBER
            }
        }

        form.hint?.let {
            binding.inputAnswer.hint = it
        }

        form.answer?.let { answer ->
            try {
                if (answer.isNotBlank() && form.numberType == NumberType.PHONE_NUMBER) {
                    binding.editAnswer.setText(answer)
                } else if (answer.isNotBlank() && form.numberType == NumberType.DECIMAL_NUMBER && answer.toDoubleOrNull() != null &&
                    answer.toDouble() < Double.MAX_VALUE
                ) {
                    binding.editAnswer.setText(answer.toDouble().toString())
                } else if (answer.isNotBlank() && answer.toLongOrNull() != null && answer.toLong() < Int.MAX_VALUE) {
                    binding.editAnswer.setText(answer.toLong().toString())
                }
            } catch (e: Exception) {
            }
        }

        binding.editAnswer.doAfterTextChanged {
            val input = binding.editAnswer.text.toString()
            var ans: String? = null
            try {
                if (input.isNotBlank()) {
                    if (form.numberType == NumberType.PHONE_NUMBER) {
                        ans = input
                    } else if (form.numberType == NumberType.DECIMAL_NUMBER && input
                            .toDoubleOrNull() != null && input
                            .toDouble() < Double.MAX_VALUE
                    ) {
                        ans = input.toDouble().toString()
                    } else if (form.numberType == NumberType.NUMBER && input
                            .toLongOrNull() != null && input.toLong() < Int.MAX_VALUE
                    ) {
                        ans = input.toLong().toString()
                    }
                } else {
                    ans = null
                }
            } catch (e: Exception) {

            }

            adapter.getData()[adapterPosition].apply {
                answer = ans
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
        }
    }

}