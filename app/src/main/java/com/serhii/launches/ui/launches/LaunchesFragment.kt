package com.serhii.launches.ui.launches

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sehii.launches.R
import com.serhii.launches.data.model.Launch
import com.serhii.launches.mvvm.Resource
import com.serhii.launches.mvvm.succeeded
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.launches_fragment.*
import timber.log.Timber

@AndroidEntryPoint
class LaunchesFragment : Fragment() {

    private val viewModel by viewModels<LaunchesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.launches_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initList()

        viewModel.launches.observe(viewLifecycleOwner, {
            swipe_to_refresh.isRefreshing = (it == Resource.Loading)

            if (it.succeeded) {
                (rw_launches.adapter as LaunchesAdapter).setList((it as Resource.Success).data)
            }

            if (it is Resource.Error) {
                Timber.e(it.exception)
                Toast.makeText(context, R.string.error_message, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.loadLaunches()
    }

    private fun initToolbar() {
        toolbar.apply {
            setTitle(R.string.launches_screen_title)
            inflateMenu(R.menu.menu_launches)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.filter_success -> {
                        viewModel.filterCriteria = { launch -> launch.isSuccess }
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
        swipe_to_refresh.setOnRefreshListener { viewModel.loadLaunches(forceUpdate = true) }
        rw_launches.apply {
            setHasFixedSize(true)
            adapter = LaunchesAdapter()
                .apply {
                    stateRestorationPolicy =
                        RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                    setLaunchClickListener(object : LaunchesAdapter.OnLaunchClickListener {
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

private class LaunchesAdapter(
    launches: List<Launch> = emptyList()
) : RecyclerView.Adapter<LaunchesAdapter.ViewHolder>() {

    private var launchesList = launches
    private var onLaunchClickListener: OnLaunchClickListener? = null

    fun setList(list: List<Launch>) {
        launchesList = list
        notifyDataSetChanged()
    }

    fun setLaunchClickListener(listener: OnLaunchClickListener) {
        this.onLaunchClickListener = listener
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.launch_row_item, parent, false)) {

        private var tvName: TextView = itemView.findViewById(R.id.tv_name)
        private var tvDate: TextView = itemView.findViewById(R.id.tv_date)
        private var tvResult: TextView = itemView.findViewById(R.id.tv_result)

        fun bind(launch: Launch, clickListener: OnLaunchClickListener? = null) {
            tvName.text = launch.name
            tvDate.text = launch.formattedDate
            tvResult.text =
                itemView.context.getString(if (launch.isSuccess) R.string.success else R.string.fail)
            itemView.setOnClickListener { clickListener?.onLaunchClicked(launch) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context), parent)

    override fun getItemCount(): Int = launchesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val launch = launchesList[position]
        holder.bind(launch, onLaunchClickListener)
    }

    interface OnLaunchClickListener {
        fun onLaunchClicked(launch: Launch)
    }
}
