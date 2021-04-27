package com.pradeep.form.simple_form.form_items

import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.pradeep.form.simple_form.adapter.SimpleFormAdapter
import com.pradeep.form.simple_form.databinding.ItemDatePickerBinding
import com.pradeep.form.simple_form.databinding.ItemTimePickerBinding
import com.pradeep.form.simple_form.model.Form
import com.pradeep.form.simple_form.utils.SimpleFormUtils
import com.pradeep.form.simple_form.utils.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

class TimePickerFormItem constructor(
    val binding: ItemTimePickerBinding,
    val adapter: SimpleFormAdapter
) : BaseFormItem(binding.root, adapter) {

    override fun bind(form: Form) {
        val utcFormat = SimpleDateFormat(SimpleFormUtils.UTC_PATTERN, Locale.getDefault())

        form.hint?.let {
            binding.inputAnswer.hint = it
        }
        form.answer?.let { utcDate ->
            try {
                utcFormat.parse(
                    utcDate
                )?.let { date ->
                    binding.editAnswer.setText(
                        form.timeDisplayFormat.format(
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
            val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
            val picker = MaterialTimePicker.Builder()
            if (form.timeFormat == TimeFormat.FORMAT_24_HOURS) {
                picker.setTimeFormat(com.google.android.material.timepicker.TimeFormat.CLOCK_24H)
            } else {
                picker.setTimeFormat(com.google.android.material.timepicker.TimeFormat.CLOCK_12H)
            }
            val dialog = picker.build()
            dialog.show(adapter.getFragmentManager(), null)
            dialog.addOnPositiveButtonClickListener {
                val hourOfTheDay = dialog.hour
                val am_pm = when {
                    hourOfTheDay == 0 -> {
                        "AM"
                    }
                    hourOfTheDay == 12 -> {
                        "PM"
                    }
                    hourOfTheDay > 12 -> {
                        "PM"
                    }
                    else -> {
                        "AM"
                    }
                }
                adapter.getData()[adapterPosition].apply {
                    val output = "${dialog.hour}:${dialog.minute} $am_pm"
                    timeFormatter.parse(output)?.let { date ->
                        binding.editAnswer.setText(form.timeDisplayFormat.format(date))
                        answer = utcFormat.format(date)
                    }
                    if (isMandatory && answer.isNullOrBlank()) {
                        binding.inputAnswer.error = errorMessage
                    } else {
                        binding.inputAnswer.isErrorEnabled = false
                    }
                }
            }
        }

        if (!form.isValid) {
            binding.editAnswer.requestFocus()
            form.apply {
                if (isMandatory && answer.isNullOrBlank()) {
                    binding.inputAnswer.error = errorMessage
                } else {
                    binding.inputAnswer.isErrorEnabled = false
                }
            }
        } else {
            binding.inputAnswer.isErrorEnabled = false
        }
    }
}