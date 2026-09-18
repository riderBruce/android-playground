package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun convert(view: View) {
        val dollarInput: EditText = findViewById(R.id.edt_dollar)
        val resultTextView: TextView = findViewById(R.id.tv_result)

        if (dollarInput.text.isNotEmpty()) {
            val dollarAmount = dollarInput.text.toString().toFloat()
            val euroAmount = dollarAmount * 0.85f
            resultTextView.text = euroAmount.toString()
        } else {
            resultTextView.text = getString(R.string.no_value_string)
        }
    }
}