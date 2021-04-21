package com.pradeep.form.simple_form.form_items


import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pradeep.form.simple_form.SimpleFormAdapter
import com.pradeep.form.simple_form.databinding.ItemSingleChoiceBinding
import com.pradeep.form.simple_form.model.Form

class SingleChoiceFormItem(
    private val binding: ItemSingleChoiceBinding,
    private val adapter: SimpleFormAdapter
) :
    BaseFormItem(binding.root, adapter) {

    override fun bind(form: Form) {
        val choices = mutableListOf<String>()
        choices.clear()
        choices.addAll(form.choices?.distinct().orEmpty())

        var materialDialog: AlertDialog? = null

        form.hint?.let {
            binding.inputAnswer.hint = it.toString()
        } ?: run {
            binding.inputAnswer.hint = "Please choose an answer"
        }

        form.answer?.let { answer ->
            if (answer.isNotEmpty()) {
                binding.editAnswer.setText(answer)
            }
        }

        binding.editAnswer.setOnClickListener {
            if (materialDialog == null) {
                materialDialog = MaterialAlertDialogBuilder(binding.root.context)
                    .setTitle(form.question)
                    .setItems(choices.toTypedArray()) { _, which ->
                        adapter.getData()[adapterPosition].apply {
                            answer = choices[which]
                            binding.editAnswer.setText(answer)
                        }
                    }.create()
            }

            if (materialDialog?.isShowing != true) {
                materialDialog?.show()
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
                    binding.inputAnswer.error = null
                }
            }
        }
    }

}