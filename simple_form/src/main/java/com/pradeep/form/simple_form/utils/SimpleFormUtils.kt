package com.pradeep.form.simple_form.utils

import android.text.TextUtils

object SimpleFormUtils {
    fun convertListToSingleString(answers: List<String>): String {
        val builder = StringBuilder()
        answers.forEachIndexed { index, answer ->
            builder.append(answer)
            if (index != (answers.size - 1)) {
                builder.append(", ")
            }
        }
        return builder.toString()
    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}