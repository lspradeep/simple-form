package com.pradeep.form.simple_form.form_items


import android.content.DialogInterface
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pradeep.form.simple_form.R
import com.pradeep.form.simple_form.SimpleFormAdapter
import com.pradeep.form.simple_form.databinding.ItemMultiChoiceBinding
import com.pradeep.form.simple_form.model.Form
import com.pradeep.form.simple_form.utils.SimpleFormUtils

class MultiChoiceFormItem(
    private val binding: ItemMultiChoiceBinding,
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

        val selectedAnswers = mutableListOf<String>()
        selectedAnswers.clear()
        selectedAnswers.addAll(form.answers.orEmpty())

        if (selectedAnswers.isNotEmpty()) {
            binding.editAnswer.setText(SimpleFormUtils.convertListToSingleString(selectedAnswers))
        } else {
            binding.editAnswer.text = null
            binding.inputAnswer.error = null
        }

        binding.editAnswer.setOnClickListener {
            val choices = mutableListOf<String>()
            choices.clear()
            choices.addAll(form.choices?.filter { it.isNotBlank() }?.distinct().orEmpty())

            val checkedItems = BooleanArray(choices.size)

            choices.forEachIndexed { index, choiceItem ->
                checkedItems[index] = form.answers?.contains(choiceItem) ?: false
            }

            dialog = MaterialAlertDialogBuilder(binding.root.context)
                .setTitle(form.question)
                .setMultiChoiceItems(
                    choices.toTypedArray(),
                    checkedItems
                ) { _, which, isChecked ->
                    if (isChecked) {
                        choices[which].let {
                            selectedAnswers.add(it)
                        }
                        adapter.getData()[adapterPosition].apply {
                            answers = selectedAnswers
                        }
                    } else {
                        selectedAnswers.remove(choices[which])
                        adapter.getData()[adapterPosition].apply {
                            answers = selectedAnswers
                        }
                    }
                    binding.editAnswer.setText("")
                    binding.editAnswer.setText(
                        SimpleFormUtils.convertListToSingleString(
                            selectedAnswers
                        )
                    )
                    if (form.isMandatory && selectedAnswers.isNullOrEmpty()) {
                        binding.inputAnswer.error = form.errorMessage
                    } else {
                        binding.inputAnswer.error = null
                    }
                }
                .setPositiveButton(binding.root.context.getString(R.string.done)) { _: DialogInterface, _: Int ->
                }
                .setNegativeButton(binding.root.context.getString(R.string.clear)) { _: DialogInterface, _: Int ->
                    selectedAnswers.clear()
                    adapter.getData()[adapterPosition].apply {
                        answers = selectedAnswers
                        binding.editAnswer.setText("")
                        binding.editAnswer.setText(
                            SimpleFormUtils.convertListToSingleString(
                                selectedAnswers
                            )
                        )
                        binding.inputAnswer.error = null
                    }
                    if (form.isMandatory && selectedAnswers.isNullOrEmpty()) {
                        binding.inputAnswer.error = form.errorMessage
                    } else {
                        binding.inputAnswer.error = null
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
                if (isMandatory && answers.isNullOrEmpty()) {
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