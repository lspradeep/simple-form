package com.pradeep.form.simple_form.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pradeep.form.simple_form.SimpleFormAdapter
import com.pradeep.form.simple_form.databinding.ViewSimpleFormBinding
import com.pradeep.form.simple_form.form_items.FormTypes
import com.pradeep.form.simple_form.model.Form
import kotlinx.coroutines.*
import timber.log.Timber

class SimpleFormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?
) :
    RelativeLayout(context, attrs) {

    var simpleFormAdapter: SimpleFormAdapter? = null
    private val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)


    private val binding: ViewSimpleFormBinding = ViewSimpleFormBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    fun setData(forms: List<Form>) {
        simpleFormAdapter = SimpleFormAdapter(forms = forms, sectionedForms = null)
        binding.recyclerForms.apply {
            layoutManager = linearLayoutManager
            adapter = simpleFormAdapter
        }
    }

    fun setData(forms: Map<String, List<Form>>) {
        simpleFormAdapter = SimpleFormAdapter(sectionedForms = forms, forms = null)
        binding.recyclerForms.apply {
            layoutManager = linearLayoutManager
            adapter = simpleFormAdapter
        }
    }

    //Only for library users
    fun getFormItems(): List<Form> {
        return simpleFormAdapter?.getData()?.filter { it.formType != FormTypes.NONE } ?: listOf()
    }

    //Only for library users
    fun getSectionTitles(): List<String> {
        return simpleFormAdapter?.getSectionTitles() ?: listOf()
    }

    fun validateInputs(): Boolean {
        val allFormsItems = simpleFormAdapter?.getData()
        var isValid = true
        for (index in allFormsItems?.indices ?: 0..0) {
            val element = allFormsItems?.get(index)
            if (element?.isFormItemValid() == false) {
                linearLayoutManager.scrollToPositionWithOffset(index, 0)
                simpleFormAdapter?.notifyItemChanged(index)
                isValid = false
                Timber.d("check ${element.formType} - ${element.question} at index $index")
                break
            }
        }
        return isValid
    }

}