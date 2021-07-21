package com.serhii.launches.ui.launches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sehii.launches.R
import com.sehii.launches.databinding.LaunchItemBinding
import com.serhii.repository.model.Launch

class LaunchesAdapter(
    launches: List<Launch> = emptyList()
) : RecyclerView.Adapter<LaunchesAdapter.LaunchViewHolder>() {

    private var onLaunchClickListener: OnLaunchClickListener? = null

    var launchesList = launches
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun setOnLaunchClickListener(listener: OnLaunchClickListener) {
        this.onLaunchClickListener = listener
    }

    class LaunchViewHolder(private val itemBinding: LaunchItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(launch: Launch, clickListener: OnLaunchClickListener? = null) {
            itemBinding.nameText.text = launch.name
            itemBinding.dateText.text = launch.formattedDate
            itemBinding.resultText.text =
                itemView.context.getString(if (launch.isSuccess) R.string.success else R.string.fail)
            itemView.setOnClickListener { clickListener?.onLaunchClicked(launch) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder =
        LaunchViewHolder(
            LaunchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = launchesList.size

    override fun onBindViewHolder(holderLaunch: LaunchViewHolder, position: Int) {
        val launch = launchesList[position]
        holderLaunch.bind(launch, onLaunchClickListener)
    }

    interface OnLaunchClickListener {
        fun onLaunchClicked(launch: Launch)
    }
}
