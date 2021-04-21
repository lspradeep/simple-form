package com.pradeep.form.simple_form.form_items

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
        binding.editAnswer.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE

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

}