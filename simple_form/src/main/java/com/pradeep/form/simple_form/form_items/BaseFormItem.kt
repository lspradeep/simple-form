package com.pradeep.form.simple_form.form_items

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.pradeep.form.simple_form.R
import com.pradeep.form.simple_form.SimpleFormAdapter
import com.pradeep.form.simple_form.model.Form
import timber.log.Timber

abstract class BaseFormItem(itemView: View, private val adapter: SimpleFormAdapter) :
    RecyclerView.ViewHolder(itemView) {

    internal var question: TextView? = itemView.findViewById(R.id.text_question)
    internal var description: TextView? = itemView.findViewById(R.id.text_description)

    abstract fun bind(
        form: Form
    )
}