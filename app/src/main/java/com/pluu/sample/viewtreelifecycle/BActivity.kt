package com.pluu.sample.viewtreelifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pluu.sample.viewtreelifecycle.databinding.ActivityBBinding

class BActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvLifecycle.text = binding.root.getViewTreeLog()
    }
}