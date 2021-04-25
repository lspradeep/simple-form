package com.pradeep.form.simpleforms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.pradeep.form.simpleforms.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.btnSimpleForm.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.btnSectionedForm.setOnClickListener {
            startActivity(
                MainActivity2.newIntent(
                    this,
                    binding.checkboxShowOneSectionAtATime.isChecked
                )
            )
        }
    }
}