package com.serhii.launches.ui.launches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sehii.launches.R
import com.sehii.launches.databinding.LaunchesFragmentBinding
import com.serhii.repository.model.Launch
import com.serhii.repository.Resource
import com.serhii.repository.succeeded
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LaunchesFragment : Fragment() {

    private val viewModel by viewModels<LaunchesViewModel>()

    private var _binding: LaunchesFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LaunchesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initList()
        observeData()
    }

    private fun observeData() {
        viewModel.launches.observe(viewLifecycleOwner, {
            binding.swipeToRefresh.isRefreshing = (it == Resource.Loading)

            if (it.succeeded) {
                (binding.launchesList.adapter as LaunchesAdapter).setList((it as Resource.Success).data)
            }

            if (it is Resource.Error) {
                Timber.e(it.exception)
                Toast.makeText(context, R.string.error_message, Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.loadLaunches()
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            setTitle(R.string.launches_screen_title)
            inflateMenu(R.menu.menu_launches)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.filter_success -> {
                        viewModel.filterCriteria = FilterCriteria.SuccessFilter
                        return@setOnMenuItemClickListener true
                    }
                    R.id.filter_all -> {
                        viewModel.filterCriteria = null
                        return@setOnMenuItemClickListener true
                    }
                }
                return@setOnMenuItemClickListener false
            }
        }
    }

    private fun initList() {
        binding.swipeToRefresh.setOnRefreshListener { viewModel.loadLaunches(forceUpdate = true) }
        binding.launchesList.apply {
            setHasFixedSize(true)
            adapter = LaunchesAdapter().apply {
                stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                setOnLaunchClickListener(object : LaunchesAdapter.OnLaunchClickListener {
                    override fun onLaunchClicked(launch: Launch) {
                        val action =
                            LaunchesFragmentDirections.startRocketDetailsFragment(launch.rocketId)
                        findNavController().navigate(action)
                    }
                })
            }
        }
    }
}
