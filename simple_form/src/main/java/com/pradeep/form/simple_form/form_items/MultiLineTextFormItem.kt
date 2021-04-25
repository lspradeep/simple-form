package com.pradeep.form.simple_form.form_items

import android.text.InputFilter
import androidx.core.widget.doAfterTextChanged
import com.pradeep.form.simple_form.SimpleFormAdapter
import com.pradeep.form.simple_form.databinding.ItemMultiLineTextBinding
import com.pradeep.form.simple_form.model.Form

class MultiLineTextFormItem(
    private val binding: ItemMultiLineTextBinding,
    private val adapter: SimpleFormAdapter
) :
    BaseFormItem(binding.root, adapter) {

    override fun bind(form: Form) {
        binding.editAnswer.filters = emptyArray()

        if (form.showCharLimitCounter && form.charLimit != -1 && form.charLimit > 0) {
            binding.editAnswer.filters = arrayOf(InputFilter.LengthFilter(form.charLimit))
            binding.inputAnswer.counterMaxLength = form.charLimit
            binding.inputAnswer.isCounterEnabled = true

        } else {
            binding.inputAnswer.isCounterEnabled = false
        }

        form.hint?.let {
            binding.inputAnswer.hint = it.toString()
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
                if (isMandatory && input.isNullOrBlank()) {
                    binding.inputAnswer.error = errorMessage
                } else {
                    answer = input.toString()
                    binding.inputAnswer.isErrorEnabled=false
                }
            }
        }

        if (!form.isValid) {
            binding.editAnswer.requestFocus()
            form.apply {
                if (isMandatory && answer.isNullOrBlank()) {
                    binding.inputAnswer.error = errorMessage
                } else {
                    binding.inputAnswer.isErrorEnabled=false
                }
            }
        } else {
            binding.inputAnswer.isErrorEnabled=false
        }
    }

}