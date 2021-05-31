package com.fpr.appcoink.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.fpr.appcoink.R
import com.fpr.appcoink.core.Result
import com.fpr.appcoink.core.hide
import com.fpr.appcoink.core.show
import com.fpr.appcoink.data.remote.home.HomeScreenDataSource
import com.fpr.appcoink.databinding.FragmentHomeScreenBinding
import com.fpr.appcoink.domain.home.HomeScreenReporImpl
import com.fpr.appcoink.presentation.HomeScreenViewModel
import com.fpr.appcoink.presentation.HomeScreenViewModelFactory
import com.fpr.appcoink.ui.main.adapter.HomeScreenAdapter


class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {
    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel by viewModels<HomeScreenViewModel> {
        HomeScreenViewModelFactory(HomeScreenReporImpl(HomeScreenDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)

        viewModel.fetchLatestPosts().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Result.Loading -> {
                    binding.progressBar.show()
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    Log.d("Datos puto", "onViewCreated: $it.data")
                    binding.rvHome.adapter = HomeScreenAdapter(it.data)
                }
                is Result.Failure -> {
                    binding.progressBar.hide()
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error: {${it.exception}}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }
}