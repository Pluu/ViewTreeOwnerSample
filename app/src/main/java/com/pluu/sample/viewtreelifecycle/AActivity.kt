package com.pluu.sample.viewtreelifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pluu.sample.viewtreelifecycle.databinding.ActivityABinding

class AActivity : AppCompatActivity() {

    private lateinit var binding: ActivityABinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityABinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvLifecycle.text = binding.root.getViewTreeLog()
    }
}