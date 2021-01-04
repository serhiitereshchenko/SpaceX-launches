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
import com.serhii.launches.mvvm.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.rocket_details_fragment.*
import timber.log.Timber

@AndroidEntryPoint
class RocketDetailsFragment : Fragment() {

    private val args: RocketDetailsFragmentArgs by navArgs()

    private val viewModel by viewModels<RocketDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.rocket_details_fragment, container, false);

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_to_refresh.setOnRefreshListener {
            viewModel.loadRocket(
                id = args.rocketId,
                forceUpdate = true
            )
        }
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        viewModel.rocket.observe(viewLifecycleOwner, {
            swipe_to_refresh.isRefreshing = (it == Resource.Loading)

            if (it is Resource.Success) {
                val rocket = it.data
                rocket_name.text = rocket.name
                toolbar.title = rocket.name
                rocket_description.text = rocket.description
                Glide.with(this).load(rocket.imageUrl).into(rocket_image)
            }

            if (it is Resource.Error) {
                Timber.e(it.exception)
                Toast.makeText(context, R.string.error_message, Toast.LENGTH_SHORT).show()
            }

        })

        viewModel.loadRocket(args.rocketId)
    }
}
