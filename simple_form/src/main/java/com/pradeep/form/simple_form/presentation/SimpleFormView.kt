package com.pradeep.form.simple_form.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.view.isVisible
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
    RelativeLayout(context, attrs), SimpleFormAdapter.AdapterCallback {

    private var showOneSectionAtATime: Boolean
    private var simpleFormAdapter: SimpleFormAdapter? = null
    private var callback: FormSubmitCallback? = null
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

    fun setData(forms: List<Form>, callback: FormSubmitCallback) {
        this.callback = callback

        simpleFormAdapter = SimpleFormAdapter(
            forms = forms,
            sectionedForms = null,
            showOneSectionAtATime = false,
            adapterCallback = this
        )

        binding.recyclerForms.apply {
            layoutManager = linearLayoutManager
            adapter = simpleFormAdapter
            itemAnimator = null
        }

        initView()
        setListeners()
    }

    private fun initView() {
        if (showOneSectionAtATime && simpleFormAdapter?.getSectionTitles()?.size ?: 0 > 0) {
            binding.btnNext.text = context.getString(R.string.next)
            binding.btnPrevious.isVisible = true
        } else {
            binding.btnNext.text = context.getString(R.string.submit)
            binding.btnPrevious.isVisible = false
        }
    }

    fun setData(forms: Map<String, List<Form>>, callback: FormSubmitCallback) {
        this.callback = callback

        simpleFormAdapter = SimpleFormAdapter(
            sectionedForms = forms,
            forms = null,
            showOneSectionAtATime = showOneSectionAtATime,
            adapterCallback = this
        )
        binding.recyclerForms.apply {
            layoutManager = linearLayoutManager
            adapter = simpleFormAdapter
            itemAnimator = null
        }

        initView()
        setListeners()
    }

    private fun setListeners() {
        if (showOneSectionAtATime && simpleFormAdapter?.getSectionTitles()?.size ?: 0 > 0) {
            binding.btnNext.setOnClickListener {
                simpleFormAdapter?.showNextSection()
                linearLayoutManager.scrollToPositionWithOffset(0, 0)
            }
        } else {
            binding.btnNext.setOnClickListener {
                //submit button
                if (validateInputs()) {
                    callback?.onFormSubmitted(simpleFormAdapter?.getData() ?: listOf())
                }
            }
        }

        binding.btnPrevious.setOnClickListener {
            simpleFormAdapter?.showPreviousSection()
            linearLayoutManager.scrollToPositionWithOffset(0, 0)
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

    private fun validateInputs(): Boolean {
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

    override fun updateVisibility() {
        if (simpleFormAdapter?.getIsSectionedAdapter() == true)
            binding.btnPrevious.isVisible = (simpleFormAdapter?.getIsFirstSection() == false)
    }

}