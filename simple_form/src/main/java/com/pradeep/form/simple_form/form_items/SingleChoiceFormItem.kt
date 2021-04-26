package com.pradeep.form.simple_form.form_items


import android.content.DialogInterface
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pradeep.form.simple_form.R
import com.pradeep.form.simple_form.adapter.SimpleFormAdapter
import com.pradeep.form.simple_form.databinding.ItemSingleChoiceBinding
import com.pradeep.form.simple_form.model.Form

class SingleChoiceFormItem(
    private val binding: ItemSingleChoiceBinding,
    private val adapter: SimpleFormAdapter
) :
    BaseFormItem(binding.root, adapter) {

    override fun bind(form: Form) {
        form.hint?.let {
            binding.inputAnswer.hint = it.toString()
        } ?: run {
            binding.inputAnswer.hint = "Please choose an answer"
        }

        var dialog: AlertDialog? = null

        form.answer?.let { answer ->
            if (answer.isNotEmpty()) {
                binding.editAnswer.setText(answer)
            } else {
                binding.editAnswer.text = null
                binding.inputAnswer.isErrorEnabled=false
            }
        } ?: run {
            binding.editAnswer.text = null
            binding.inputAnswer.isErrorEnabled=false
        }

        binding.editAnswer.setOnClickListener {
            val choices = mutableListOf<String>()
            choices.clear()
            choices.addAll(form.choices?.filter { it.isNotBlank() }?.distinct().orEmpty())

            dialog = MaterialAlertDialogBuilder(binding.root.context)
                .setTitle(form.question)
                .setSingleChoiceItems(
                    choices.toTypedArray(),
                    choices.indexOf(form.answer)
                ) { _, which ->
                    adapter.getData()[adapterPosition].apply {
                        answer = choices[which]
                        binding.editAnswer.setText(answer)
                        binding.inputAnswer.isErrorEnabled=false
                    }
                    if (form.isMandatory && form.answer.isNullOrEmpty()) {
                        binding.inputAnswer.error = form.errorMessage
                    } else {
                        binding.inputAnswer.isErrorEnabled=false
                    }
                }
                .setPositiveButton(binding.root.context.getString(R.string.done)) { _: DialogInterface, _: Int -> }
                .setNegativeButton(binding.root.context.getString(R.string.clear)) { _: DialogInterface, _: Int ->
                    adapter.getData()[adapterPosition].apply {
                        answer = null
                        binding.editAnswer.text = answer
                        binding.inputAnswer.isErrorEnabled=false
                    }
                    if (form.isMandatory && form.answer.isNullOrEmpty()) {
                        binding.inputAnswer.error = form.errorMessage
                    } else {
                        binding.inputAnswer.isErrorEnabled=false
                    }
                }.create()
            if (dialog?.isShowing != true) {
                dialog?.show()
            }
        }

        binding.editAnswer.customSelectionActionModeCallback = object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
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