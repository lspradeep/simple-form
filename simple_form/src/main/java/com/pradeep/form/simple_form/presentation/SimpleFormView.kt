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
import java.lang.Exception

class SimpleFormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?
) :
    RelativeLayout(context, attrs) {

    private var showOneSectionAtOnce: Boolean = false
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
                showOneSectionAtOnce =
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
        if (simpleFormAdapter?.getIsSectionedAdapter() == true) {
            binding.btnNext.text = context.getString(R.string.next)
        } else {
            binding.btnNext.text = context.getString(R.string.submit)
        }
    }

    fun setData(
        forms: Map<String, List<Form>>,
        callback: FormSubmitCallback,
        showOnSectionAtATime: Boolean = false
    ) {
        showOneSectionAtOnce = showOnSectionAtATime
        forms.keys.forEach { key ->
            if (forms[key]?.size ?: 0 <= 0) {
                throw Exception("please add items to '$key' section")
            }
        }
        this.callback = callback

        simpleFormAdapter = SimpleFormAdapter(
            sectionedForms = forms,
            forms = null,
            showOneSectionAtATime = showOneSectionAtOnce
        )
        binding.recyclerForms.apply {
            layoutManager = linearLayoutManager
            adapter = simpleFormAdapter
            itemAnimator = null
        }

        initView()
        setListeners()
    }

    private fun updateUi() {
        binding.btnPrevious.isVisible =
            !(simpleFormAdapter?.getIsSectionedAdapter() == true && simpleFormAdapter?.getIsFirstSection() == true)
        if (simpleFormAdapter?.getIsLastSection()==true){
            binding.btnNext.text = context.getString(R.string.submit)
        }else{
            binding.btnNext.text = context.getString(R.string.next)
        }
    }

    private fun setListeners() {
        if (simpleFormAdapter?.getIsSectionedAdapter() == true) {
            binding.btnNext.setOnClickListener {
                simpleFormAdapter?.storeCurrentSectionData()
                if (simpleFormAdapter?.getIsLastSection() != true) {
                    //btn next
                    if (validateCurrentSectionInputs()) {
                        simpleFormAdapter?.showNextSection()
                        linearLayoutManager.scrollToPositionWithOffset(0, 0)
                    }
                } else {
                    //submit button
                    if (validateCurrentSectionInputs()) {
                        callback?.onFormSubmitted(simpleFormAdapter?.getSectionedFormOutputData() ?: listOf())
                    }
                }

                updateUi()
            }
        } else {
            //For Normal Forms
            binding.btnNext.setOnClickListener {
                //submit button
                if (validateInputs()) {
                    callback?.onFormSubmitted(simpleFormAdapter?.getData() ?: listOf())
                }
            }
        }

        binding.btnPrevious.setOnClickListener {
            if (simpleFormAdapter?.getIsFirstSection() != true) {
                simpleFormAdapter?.showPreviousSection()
                linearLayoutManager.scrollToPositionWithOffset(0, 0)
            }
            updateUi()
        }
    }

    //Only for library users
    fun getData(): List<Form> {
        return simpleFormAdapter?.getData()?.filter { it.formType != FormTypes.NONE } ?: listOf()
    }

    //Only for library users
    fun getSectionIdTitlePairs(): List<Pair<String, String>> {
        return simpleFormAdapter?.getSectionIdTitlePairs() ?: listOf()
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

    private fun validateCurrentSectionInputs(): Boolean {
        val currentSectionFormItems = simpleFormAdapter?.getCurrentSectionData()
        var isValid = true
        for (index in currentSectionFormItems?.indices ?: 0..0) {
            val element = currentSectionFormItems?.get(index)
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

//    override fun updateVisibility() {
//        if (simpleFormAdapter?.getIsSectionedAdapter() == true)
//            binding.btnPrevious.isVisible = (simpleFormAdapter?.getIsFirstSection() == false)
//    }

}