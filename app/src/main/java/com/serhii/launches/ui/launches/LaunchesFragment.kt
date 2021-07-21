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
import com.serhii.repository.Resource
import com.serhii.repository.hasData
import com.serhii.repository.model.Launch
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchesFragment : Fragment() {

    private val viewModel by viewModels<LaunchesViewModel>()
    private var _binding: LaunchesFragmentBinding? = null
    private val binding get() = requireNotNull(_binding) { "LaunchesFragmentBinding can't be null" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadLaunches()
    }

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
        observeLaunches()
    }

    private fun observeLaunches() {
        viewModel.launches.observe(viewLifecycleOwner, {
            binding.swipeToRefresh.isRefreshing = (it == Resource.Loading)

            if (it.hasData) {
                (binding.launchesList.adapter as LaunchesAdapter).launchesList =
                    (it as Resource.Success).data.orEmpty()
            }

            if (it is Resource.Error) {
                Toast.makeText(context, R.string.error_message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            setTitle(R.string.launches_screen_title)
            inflateMenu(R.menu.menu_launches)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.filter_success -> {
                        viewModel.filterCriteria = FilterCriteria.SuccessLaunch
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
