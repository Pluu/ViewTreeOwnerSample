package com.pluu.sample.viewtreelifecycle

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.pluu.sample.viewtreelifecycle.databinding.ActivityRecyclerViewSample1Binding

class RecyclerViewSample1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityRecyclerViewSample1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewSample1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.container) {
            adapter = ItemRecyclerViewAdapter()

            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayout.VERTICAL
                ).apply {
                    val drawable =
                        ContextCompat.getDrawable(context, R.drawable.divider)
                    setDrawable(drawable!!)
                }
            )
        }
    }
}