package com.pradeep.form.simple_form.form_items

import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.pradeep.form.simple_form.adapter.SimpleFormAdapter
import com.pradeep.form.simple_form.databinding.ItemDatePickerBinding
import com.pradeep.form.simple_form.model.Form
import com.pradeep.form.simple_form.utils.SimpleFormUtils.UTC_PATTERN
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFormItem constructor(
    val binding: ItemDatePickerBinding,
    val adapter: SimpleFormAdapter
) :
    BaseFormItem(binding.root, adapter) {

    override fun bind(form: Form) {
        val utcFormat = SimpleDateFormat(UTC_PATTERN, Locale.getDefault())

        form.hint?.let {
            binding.inputAnswer.hint = it
        }
        form.answer?.let { utcDate ->
            try {
                utcFormat.parse(
                    utcDate
                )?.let { date ->
                    binding.editAnswer.setText(
                        form.dateDisplayFormat.format(
                            date
                        )
                    )
                }
                binding.inputAnswer.isErrorEnabled = false
            } catch (e: Exception) {
                binding.inputAnswer.isErrorEnabled = false
            }
        } ?: run {
            binding.inputAnswer.isErrorEnabled = false
        }

        binding.editAnswer.setOnClickListener {
            val dialog = MaterialDatePicker.Builder.datePicker().build()
            dialog.show(adapter.getFragmentManager(), null)
            dialog.addOnPositiveButtonClickListener {
                adapter.getData()[adapterPosition].apply {
                    binding.editAnswer.setText(form.dateDisplayFormat.format(Date(it)))
                    answer = utcFormat.format(Date(it))
                }
            }
        }
    }
}