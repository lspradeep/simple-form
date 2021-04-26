package com.pradeep.form.simple_form.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.pradeep.form.simple_form.utils.SimpleFormViewHolderProvider
import com.pradeep.form.simple_form.form_items.BaseFormItem
import com.pradeep.form.simple_form.form_items.FormTypes
import com.pradeep.form.simple_form.model.Form
import com.pradeep.form.simple_form.utils.SimpleFormUtils

class SimpleFormAdapter(
    val activity: Activity,
    forms: List<Form>? = null,
    val sectionedForms: Map<String, List<Form>>? = null,
    val showOneSectionAtATime: Boolean = false
) : RecyclerView.Adapter<BaseFormItem>() {

    private var dataSet = mutableListOf<Form>()
    private var sectionedDataSet = mutableListOf<Form>()
    private var sectionTitleIdPairs = mutableListOf<Pair<String, String>>()
    private var isSectionedForm = false
    private var currentSectionId: String? = null
    private var currentSectionIndex = -1
    private var sectionedFormOutputData = mutableMapOf<String, List<Form>>()

    init {
        forms?.let {
            dataSet.addAll(it)
        } ?: run {
            sectionedForms?.let {
                isSectionedForm = true
                dataSet.addAll(SimpleFormUtils.convertMapToList(it))
                dataSet.forEach { form ->
                    if (form.formType == FormTypes.NONE && !form.sectionTitle.isNullOrBlank()) {
                        sectionTitleIdPairs.add(Pair(form.id, form.sectionTitle!!))
                    }
                }

            }
        }
        if (getIsSectionedAdapter()) {
            sectionedDataSet.clear()
            currentSectionId = getSectionIdTitlePairs()[0].first
            currentSectionIndex = 0
            sectionedDataSet.addAll(getCurrentSectionData())
        }
    }

    fun getFragmentManager(): FragmentManager {
        return (activity as FragmentActivity).supportFragmentManager
    }

    fun getCurrentSectionData(): List<Form> {
        return if (!sectionedFormOutputData[currentSectionId].isNullOrEmpty()) {
            sectionedFormOutputData[currentSectionId].orEmpty()
        } else {
            dataSet.filter { it.sectionMapperId == currentSectionId }
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
        return if (getIsSectionedAdapter()) getCurrentSectionData() else dataSet
    }

    fun getSectionedFormOutputData(): List<Form> {
        return SimpleFormUtils.constructSectionedFormOutput(
            sectionedFormOutputData,
            sectionTitleIdPairs
        )
    }

    fun showNextSection() {
        if (!getIsLastSection()) {
            currentSectionIndex++
            currentSectionId = getSectionIdTitlePairs()[currentSectionIndex].first
            sectionedDataSet.clear()
            sectionedDataSet.addAll(getCurrentSectionData())
            notifyDataSetChanged()
        }

    }

    fun showPreviousSection() {
        if (!getIsFirstSection()) {
            currentSectionIndex--
            currentSectionId = getSectionIdTitlePairs()[currentSectionIndex].first
            sectionedDataSet.clear()
            sectionedDataSet.addAll(getCurrentSectionData())
            notifyDataSetChanged()
        }
    }

    fun getIsLastSection(): Boolean {
        return currentSectionIndex == (getSectionIdTitlePairs().size - 1)
    }

    fun getIsFirstSection(): Boolean {
        return currentSectionIndex == 0
    }

    fun getSectionIdTitlePairs(): List<Pair<String, String>> {
        return sectionTitleIdPairs
    }

    fun getIsSectionedAdapter(): Boolean {
        return isSectionedForm && showOneSectionAtATime && getSectionIdTitlePairs().isNotEmpty()
    }

    fun storeCurrentSectionData() {
        currentSectionId?.let { sectionId ->
//            sectionedFormOutputData[sectionId] = sectionedDataSet
            sectionedFormOutputData.put(sectionId, getData())
        }
    }
}


