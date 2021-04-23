package com.pradeep.form.simple_form

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.pradeep.form.simple_form.databinding.ViewSimpleFormBinding
import com.pradeep.form.simple_form.form_items.BaseFormItem
import com.pradeep.form.simple_form.form_items.FormTypes
import com.pradeep.form.simple_form.model.Form

class SimpleFormAdapter(
    forms: List<Form>? = null,
    val sectionedForms: Map<String, List<Form>>? = null,
    val showOneSectionAtATime: Boolean = false,
    val simpleFormView: ViewSimpleFormBinding
) : RecyclerView.Adapter<BaseFormItem>() {

    private var dataSet = mutableListOf<Form>()
    private var sectionedDataSet = mutableListOf<Form>()
    private var sectionTitles = mutableListOf<String>()
    private var isSectioned = false
    private var currentSectionTitle: String? = null
    private var currentSectionIndex = -1
    private var sectionedFormOutputData = mutableMapOf<String, List<Form>>()

    init {
        forms?.let {
            dataSet.addAll(it)
        } ?: run {
            sectionedForms?.let {
                isSectioned = true
                sectionTitles.addAll(it.keys)
                it.forEach { entry: Map.Entry<String, List<Form>> ->
                    dataSet.add(
                        Form(
                            sectionTitle = entry.key,
                            formType = FormTypes.NONE
                        ).apply { isSectionTitle = true })
                    entry.value.forEach { form: Form ->
                        form.sectionTitle = entry.key
                        dataSet.add(form)
                    }
                }
            }
        }
        simpleFormView.layoutSectionedFormButtons.isVisible = getIsSectionedAdapter()
        if (getIsSectionedAdapter()) {
            currentSectionTitle = getSectionTitles()[0]
            currentSectionIndex = 0
            dataSet.forEach { form ->
                if (form.sectionTitle == currentSectionTitle) {
                    sectionedDataSet.add(form)
                }
            }

            updateVisibility()
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
        if (getIsSectionedAdapter()) {
            sectionedDataSet[position].let { form ->
                holderBase.question?.isVisible = !(form.question.isNullOrBlank())
                holderBase.description?.isVisible = !(form.description.isNullOrBlank())
                holderBase.question?.text = form.question
                holderBase.description?.text = form.description

                holderBase.bind(form = form)
            }
        } else {
            //show all
            dataSet[position].let { form ->
                holderBase.question?.isVisible = !(form.question.isNullOrBlank())
                holderBase.description?.isVisible = !(form.description.isNullOrBlank())
                holderBase.question?.text = form.question
                holderBase.description?.text = form.description

                holderBase.bind(form = form)
            }
        }

    }

    override fun getItemCount(): Int =
        if (getIsSectionedAdapter()) sectionedDataSet.size else dataSet.size


    override fun getItemViewType(position: Int): Int {
        return if (getIsSectionedAdapter()) sectionedDataSet[position].formType else dataSet[position].formType
    }


    fun getData(): List<Form> {
        return if (getIsSectionedAdapter()) sectionedDataSet else dataSet
    }

    fun getAllData(): List<Form> {
        return dataSet
    }

    fun showNextSection() {
        //store current section data
        sectionedFormOutputData[getSectionTitles()[currentSectionIndex]] = sectionedDataSet

        if (!getIsLastSection()) {
            currentSectionIndex++
            currentSectionTitle = getSectionTitles()[currentSectionIndex]
            sectionedDataSet.clear()
            if (sectionedFormOutputData[getSectionTitles()[currentSectionIndex]].isNullOrEmpty()) {
                dataSet.filter { it.sectionTitle == currentSectionTitle }.let {
                    sectionedDataSet.addAll(it)
                }
            } else {
                sectionedFormOutputData[getSectionTitles()[currentSectionIndex]]?.let {
                    sectionedDataSet.addAll(it)
                }
            }

            notifyDataSetChanged()
            updateVisibility()
        }

    }

    fun showPreviousSection() {
        //store current section data
        sectionedFormOutputData[getSectionTitles()[currentSectionIndex]] = sectionedDataSet

        if (!getIsFirstSection()) {
            currentSectionIndex--
            currentSectionTitle = getSectionTitles()[currentSectionIndex]
            sectionedDataSet.clear()
            if (sectionedFormOutputData[getSectionTitles()[currentSectionIndex]].isNullOrEmpty()) {
                dataSet.filter { it.sectionTitle == currentSectionTitle }.let {
                    sectionedDataSet.addAll(it)
                }
            } else {
                sectionedFormOutputData[getSectionTitles()[currentSectionIndex]]?.let {
                    sectionedDataSet.addAll(it)
                }
            }
            notifyDataSetChanged()
            updateVisibility()
        }
    }

    private fun updateVisibility() {
        simpleFormView.btnPrevious.isVisible = !getIsFirstSection()
    }

    private fun getIsLastSection(): Boolean {
        return currentSectionIndex == (getSectionTitles().size - 1)
    }

    private fun getIsFirstSection(): Boolean {
        return currentSectionIndex == 0
    }

    fun getSectionData(sectionTitle: String): List<Form> {
        return dataSet.filter { it.sectionTitle == sectionTitle }
    }

    fun getSectionTitles(): List<String> {
        return sectionTitles
    }

    private fun getIsSectionedAdapter(): Boolean {
        return isSectioned && showOneSectionAtATime && getSectionTitles().isNotEmpty()
    }

}


