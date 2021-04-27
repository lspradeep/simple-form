package com.pradeep.form.simpleforms

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FormOutput(
    val question: String?,
    val answer: String?,
    val answers:List<String>?,
    val formType: Int
):Parcelable