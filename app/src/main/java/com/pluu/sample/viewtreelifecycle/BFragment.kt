package com.pluu.sample.viewtreelifecycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pluu.sample.viewtreelifecycle.databinding.FragmentBBinding

class BFragment : Fragment() {

    private var _binding: FragmentBBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBBinding.inflate(layoutInflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding!!.tvLifecycle.text = view.getViewTreeLog()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}