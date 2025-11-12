package com.smart.translator

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.smart.translator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val engines = listOf("Google ML Kit", "LibreTranslate", "Yandex Cloud", "ChatGPT")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, engines)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerEngine.adapter = adapter

        binding.btnStart.setOnClickListener {
            startService(Intent(this, FloatingViewService::class.java))
        }
    }
}
