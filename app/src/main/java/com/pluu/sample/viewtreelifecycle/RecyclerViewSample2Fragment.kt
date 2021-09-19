package com.pluu.sample.viewtreelifecycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewSample2Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recycler_view_sample_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                adapter = ItemRecyclerViewAdapter()

                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        LinearLayout.VERTICAL
                    ).apply {
                        val drawable =
                            ContextCompat.getDrawable(requireContext(), R.drawable.divider)
                        setDrawable(drawable!!)
                    }
                )
            }
        }
        return view
    }
}