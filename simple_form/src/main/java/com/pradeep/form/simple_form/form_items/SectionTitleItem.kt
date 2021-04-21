package com.pradeep.form.simple_form.form_items

import com.pradeep.form.simple_form.SimpleFormAdapter
import com.pradeep.form.simple_form.databinding.ItemSectionTitleBinding
import com.pradeep.form.simple_form.model.Form

class SectionTitleItem(
    private val binding: ItemSectionTitleBinding,
    private val adapter: SimpleFormAdapter
) : BaseFormItem(binding.root,adapter) {
    override fun bind(form: Form) {
        binding.textTitle.text = form.sectionTitle
    }

     fun validateForm(form:Form) {

    }
}