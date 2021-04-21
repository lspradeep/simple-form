package com.pradeep.form.simple_form

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.pradeep.form.simple_form.form_items.BaseFormItem
import com.pradeep.form.simple_form.form_items.FormTypes
import com.pradeep.form.simple_form.model.Form
import timber.log.Timber

class SimpleFormAdapter(
    private val forms: List<Form>? = null,
    private val sectionedForms: Map<String, List<Form>>? = null
) : RecyclerView.Adapter<BaseFormItem>() {

    private var dataSet = mutableListOf<Form>()
    private var sectionTitles = mutableListOf<String>()

    init {
        forms?.let {
            dataSet.addAll(it)
        } ?: run {
            sectionedForms?.let {
                sectionTitles.addAll(it.keys)
                it.forEach { entry: Map.Entry<String, List<Form>> ->
                    dataSet.add(
                        Form(
                            sectionTitle = entry.key,
                            formType = FormTypes.NONE
                        ).apply { isSectionTitle = true })
                    entry.value.forEach { form: Form ->
                        dataSet.add(form)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseFormItem {
        return SimpleFormViewHolderProvider.getViewHolderFor(
            parent,
            LayoutInflater.from(parent.context),
            viewType,
            this
        )
    }

    override fun onBindViewHolder(holderBase: BaseFormItem, position: Int) {
        dataSet[position].let { form ->
            holderBase.question?.isVisible = !(form.question.isNullOrBlank())
            holderBase.description?.isVisible = !(form.description.isNullOrBlank())
            holderBase.question?.text = form.question
            holderBase.description?.text = form.description

            holderBase.bind(form = form)
        }
    }

    override fun getItemCount(): Int = dataSet.size


    override fun getItemViewType(position: Int): Int {
        return dataSet[position].formType
    }


    fun getData(): List<Form> {
        return dataSet
    }

    fun getSectionTitles(): List<String> {
        return sectionTitles
    }

}


