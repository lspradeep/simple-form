package com.pradeep.form.simple_form.form_items


import android.content.DialogInterface
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
        val selectedAnswers = mutableListOf<String>()
        selectedAnswers.clear()
        selectedAnswers.addAll(form.answers.orEmpty())

        val choices = mutableListOf<String>()
        choices.clear()
        choices.addAll(form.choices?.filter { it.isNotBlank() }?.distinct().orEmpty())

        var dialog: AlertDialog? = null

        form.hint?.let {
            binding.inputAnswer.hint = it.toString()
        } ?: run {
            binding.inputAnswer.hint = "Please choose an answer"
        }

        if (selectedAnswers.isNotEmpty()) {
            binding.editAnswer.setText(SimpleFormUtils.convertListToSingleString(selectedAnswers))
        } else {
            binding.editAnswer.text = null
            binding.inputAnswer.error = null
        }

        val checkedItems = BooleanArray(choices.size)

        choices.forEachIndexed { index, choiceItem ->
            checkedItems[index] = form.answers?.contains(choiceItem) ?: false
        }

        binding.editAnswer.setOnClickListener {
            if (dialog == null) {
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
                            adapter.getData().firstOrNull { it.id == form.id }?.apply {
                                answers = selectedAnswers
                            }
                        } else {
                            selectedAnswers.remove(choices[which])
                            adapter.getData().firstOrNull { it.id == form.id }?.apply {
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
                    .setPositiveButton("Done") { _: DialogInterface, _: Int ->
                    }.create()
            }

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