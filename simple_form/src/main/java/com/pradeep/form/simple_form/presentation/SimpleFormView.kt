package com.pradeep.form.simple_form.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pradeep.form.simple_form.R
import com.pradeep.form.simple_form.SimpleFormAdapter
import com.pradeep.form.simple_form.databinding.ViewSimpleFormBinding
import com.pradeep.form.simple_form.form_items.FormTypes
import com.pradeep.form.simple_form.model.Form
import timber.log.Timber

class SimpleFormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?
) :
    RelativeLayout(context, attrs) {

    private var showOneSectionAtATime: Boolean
    var simpleFormAdapter: SimpleFormAdapter? = null
    private val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SimpleFormView,
            0, 0
        ).apply {

            try {
                showOneSectionAtATime =
                    getBoolean(R.styleable.SimpleFormView_showOneSectionAtATime, false)
            } finally {
                recycle()
            }
        }
    }


    private val binding: ViewSimpleFormBinding = ViewSimpleFormBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    fun setData(forms: List<Form>) {
        simpleFormAdapter = SimpleFormAdapter(
            forms = forms,
            sectionedForms = null,
            showOneSectionAtATime = false,
            simpleFormView = binding
        )
        binding.recyclerForms.apply {
            layoutManager = linearLayoutManager
            adapter = simpleFormAdapter
        }
        setListeners()
    }

    fun setData(forms: Map<String, List<Form>>) {
        simpleFormAdapter = SimpleFormAdapter(
            sectionedForms = forms,
            forms = null,
            showOneSectionAtATime = showOneSectionAtATime,
            simpleFormView = binding
        )
        binding.recyclerForms.apply {
            layoutManager = linearLayoutManager
            adapter = simpleFormAdapter
            itemAnimator = null
        }
        setListeners()
    }

    private fun setListeners() {
        binding.btnNext.setOnClickListener {
            simpleFormAdapter?.showNextSection()
            linearLayoutManager.scrollToPositionWithOffset(0, 0)
        }
        binding.btnPrevious.setOnClickListener {
            simpleFormAdapter?.showPreviousSection()
            linearLayoutManager.scrollToPositionWithOffset(0, 0)
        }
    }

    //Only for library users
    fun getFormItems(): List<Form> {
        return simpleFormAdapter?.getAllData()?.filter { it.formType != FormTypes.NONE } ?: listOf()
    }

    //Only for library users
    fun getSectionTitles(): List<String> {
        return simpleFormAdapter?.getSectionTitles() ?: listOf()
    }

    fun validateInputs(): Boolean {
        val allFormsItems = simpleFormAdapter?.getAllData()
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