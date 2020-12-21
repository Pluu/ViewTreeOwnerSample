package com.pluu.sample.viewtreelifecycle

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.doOnAttach
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.pluu.sample.viewtreelifecycle.databinding.ViewCustomBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class CustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding: ViewCustomBinding
    private var job1: Job? = null
    private var job2: Job? = null

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER

        binding = ViewCustomBinding.inflate(LayoutInflater.from(context), this)

        bindingInit()

        doOnAttach {
            binding.tvLifecycle.text = getViewTreeLog()
        }
    }

    private fun bindingInit() {
        binding.tvLaunchText.text = "0"
        binding.tvLaunchWhenText.text = "0"

        binding.btnLaunch.setOnClickListener {
            job1?.cancel()
            job2?.cancel()

            val lco = checkNotNull(ViewTreeLifecycleOwner.get(this)) {
                "View tree for $this has no ViewTreeLifecycleOwner"
            }

            job1 = lco.lifecycleScope.launch {
                flowSample("launch")
                    .flowOn(Dispatchers.Default)
                    .collect {
                        Timber.tag("Receive [launch]").d(">>> $it")
                        binding.tvLaunchText.text = it.toString()
                    }
            }

            job2 = lco.lifecycleScope.launchWhenStarted {
                flowSample("launchWhen")
                    .flowOn(Dispatchers.Default)
                    .collect {
                        Timber.tag("Receive [launchWhen]").d(">>> $it")
                        binding.tvLaunchWhenText.text = it.toString()
                    }
            }
        }
    }

    private fun flowSample(tag: String): Flow<Int> = (0..100).asFlow()
        .onEach { delay(100L) }
        .onEach {
            Timber.tag("Sender [$tag]").d(">>> $it")
        }
}