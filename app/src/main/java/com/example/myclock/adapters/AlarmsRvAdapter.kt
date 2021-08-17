package com.example.myclock.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myclock.R
import com.example.myclock.fragments.AlarmsFragmentDirections
import com.example.myclock.models.Alarm
import com.example.myclock.utilities.Utility
import javax.inject.Inject

class AlarmsRvAdapter constructor(
    private var alarmsList: List<Alarm>,
    private val onItemSelectListener: OnItemSelectListener
) :
    RecyclerView.Adapter<AlarmsRvAdapter.ViewHolder>() {

    class ViewHolder(view: View, onItemSelectListener: OnItemSelectListener) : RecyclerView.ViewHolder(view) {
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val tvLabel: TextView = view.findViewById(R.id.tvLabel)

        init {
            view.setOnClickListener {
                val currentPosition = bindingAdapterPosition
                if (currentPosition != RecyclerView.NO_POSITION) {
                    onItemSelectListener.onItemClick(currentPosition)
                }
            }
        }
    }

    interface OnItemSelectListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_alarm, viewGroup, false)
        return ViewHolder(view, onItemSelectListener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvTime.text = Utility.timeToString(alarmsList[position].time)
        viewHolder.tvLabel.text = alarmsList[position].label
    }

    override fun getItemCount(): Int = alarmsList.size
}
