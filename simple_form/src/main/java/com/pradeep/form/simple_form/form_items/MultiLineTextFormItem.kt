package com.pradeep.form.simple_form.form_items

import android.text.InputFilter
import android.text.InputType
import androidx.core.widget.doAfterTextChanged
import com.pradeep.form.simple_form.SimpleFormAdapter
import com.pradeep.form.simple_form.databinding.ItemMultiLineTextBinding
import com.pradeep.form.simple_form.model.Form
import com.pradeep.form.simple_form.utils.SimpleFormUtils.isEmailValid

class MultiLineTextFormItem(
    private val binding: ItemMultiLineTextBinding,
    private val adapter: SimpleFormAdapter
) :
    BaseFormItem(binding.root, adapter) {

    override fun bind(form: Form) {

        if (form.charLimit != -1 && form.charLimit > 0) {
            binding.editAnswer.filters = arrayOf(InputFilter.LengthFilter(form.charLimit))
            if (form.showCharLimitCounter) {
                binding.inputAnswer.counterMaxLength = form.charLimit
                binding.inputAnswer.isCounterEnabled = true
            } else {
                binding.inputAnswer.isCounterEnabled = false
            }
        }

        form.hint?.let {
            binding.inputAnswer.hint = it.toString()
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
            adapter.getData()[adapterPosition].apply {
                if (isMandatory && input.isNullOrBlank()) {
                    binding.inputAnswer.error = errorMessage
                } else {
                    answer = input.toString()
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
        }else{
            binding.inputAnswer.error = null
        }
    }

}