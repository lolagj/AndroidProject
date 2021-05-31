package com.fpr.appcoink.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fpr.appcoink.R
import com.fpr.appcoink.databinding.FragmentSetupProfileBinding


class SetupProfileFragment : Fragment(R.layout.fragment_setup_profile) {
    lateinit var binding:FragmentSetupProfileBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSetupProfileBinding.bind(view)
    }
}