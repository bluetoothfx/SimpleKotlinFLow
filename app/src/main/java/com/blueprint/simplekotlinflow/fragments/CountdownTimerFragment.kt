package com.blueprint.simplekotlinflow.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.blueprint.simplekotlinflow.databinding.FragmentFirstBinding
import com.blueprint.simplekotlinflow.viewmodel.CountdownTimerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountdownTimerFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val tViewModel: CountdownTimerViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        binding.buttonFirst.setOnClickListener {
            tViewModel.startCountdown(1000)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                tViewModel.countdownTimeLeft.collect { timeLeft ->
                    Log.d("resultdata", "elapsedTime: $timeLeft")
                    binding.textviewTime.text = timeLeft.toString()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            tViewModel.countdownFinishedEvent.collect {
                // Handle countdown finished event
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}