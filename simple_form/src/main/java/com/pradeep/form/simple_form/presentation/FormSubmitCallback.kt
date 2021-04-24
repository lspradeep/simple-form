package com.pradeep.form.simple_form.presentation

import com.pradeep.form.simple_form.model.Form

interface FormSubmitCallback {
    fun onFormSubmitted(forms: List<Form>)
}