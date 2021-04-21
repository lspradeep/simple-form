package com.pradeep.form.simple_form

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.pradeep.form.simple_form.form_items.*

class SimpleFormViewHolderProvider {
    companion object {
        fun getViewHolderFor(
            parent: ViewGroup,
            layoutInflater: LayoutInflater,
            viewType: Int,
            simpleFormAdapter: SimpleFormAdapter,
        ): BaseFormItem {
            return getFormItemForType(
                parent,
                layoutInflater,
                viewType,
                simpleFormAdapter
            )
        }

        private fun getFormItemForType(
            parent: ViewGroup,
            layoutInflater: LayoutInflater,
            formType: Int,
            simpleFormAdapter: SimpleFormAdapter,
        ): BaseFormItem {
            when (formType) {
                -1 -> {
                    return SectionTitleItem(
                        DataBindingUtil.inflate(
                            layoutInflater,
                            R.layout.item_section_title,
                            parent,
                            false
                        ),
                        simpleFormAdapter
                    )
                }
                FormTypes.SINGLE_LINE_TEXT -> {
                    return SingleLineTextFormItem(
                        DataBindingUtil.inflate(
                            layoutInflater,
                            R.layout.item_single_line_text,
                            parent,
                            false
                        ),
                        simpleFormAdapter
                    )
                }
                FormTypes.MULTI_LINE_TEXT -> {
                    return MultiLineTextFormItem(
                        DataBindingUtil.inflate(
                            layoutInflater,
                            R.layout.item_multi_line_text,
                            parent,
                            false
                        ),
                        simpleFormAdapter
                    )
                }
                FormTypes.SINGLE_CHOICE -> {
                    return SingleChoiceFormItem(
                        DataBindingUtil.inflate(
                            layoutInflater,
                            R.layout.item_single_choice,
                            parent,
                            false
                        ),
                        simpleFormAdapter
                    )
                }
                FormTypes.MULTI_CHOICE -> {
                    return MultiChoiceFormItem(
                        DataBindingUtil.inflate(
                            layoutInflater,
                            R.layout.item_multi_choice,
                            parent,
                            false
                        ),
                        simpleFormAdapter
                    )
                }
                FormTypes.NUMBER -> {
                    return NumberInputFormItem(
                        DataBindingUtil.inflate(
                            layoutInflater,
                            R.layout.item_number_input,
                            parent,
                            false
                        ),
                        simpleFormAdapter
                    )
                }
                else -> {
                    return SingleLineTextFormItem(
                        DataBindingUtil.inflate(
                            layoutInflater,
                            R.layout.item_single_line_text,
                            parent,
                            false
                        ),
                        simpleFormAdapter
                    )
                }
            }
        }
    }
}