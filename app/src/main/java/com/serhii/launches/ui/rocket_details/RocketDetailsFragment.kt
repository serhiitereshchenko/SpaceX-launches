package com.serhii.launches.ui.rocket_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sehii.launches.R
import com.sehii.launches.databinding.RocketDetailsFragmentBinding
import com.serhii.repository.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RocketDetailsFragment : Fragment() {

    private val args: RocketDetailsFragmentArgs by navArgs()
    private val viewModel by viewModels<RocketDetailsViewModel>()

    private var _binding: RocketDetailsFragmentBinding? = null
    private val binding get() = requireNotNull(_binding) { "RocketDetailsFragmentBinding can't be null" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RocketDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeRocket()
    }

    private fun initUI() {
        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.loadRocket(
                id = args.rocketId,
                forceUpdate = true
            )
        }
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    private fun observeRocket() {
        viewModel.rocket.observe(viewLifecycleOwner, {
            binding.swipeToRefresh.isRefreshing = (it == Resource.Loading)

            if (it is Resource.Success) {
                val rocket = it.data
                binding.rocketName.text = rocket.name
                binding.toolbar.title = rocket.name
                binding.rocketDescription.text = rocket.description
                Glide.with(this).load(rocket.imageUrl).into(binding.rocketImage)
            }

            if (it is Resource.Error) {
                Toast.makeText(context, R.string.error_message, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.loadRocket(args.rocketId)
    }
}
