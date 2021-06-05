package com.serhii.launches.ui.launches

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sehii.launches.R
import com.serhii.repository.model.Launch

class LaunchesAdapter(
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
        RecyclerView.ViewHolder(inflater.inflate(R.layout.launch_item, parent, false)) {

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
