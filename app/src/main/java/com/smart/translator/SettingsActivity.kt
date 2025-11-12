package com.smart.translator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smart.translator.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // TODO: implement settings UI (engine selection, API key input, offline language management)
    }
}
