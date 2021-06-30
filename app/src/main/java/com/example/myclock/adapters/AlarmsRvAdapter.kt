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

class AlarmsRvAdapter(
    private var alarmsList: List<Alarm>,
) :
    RecyclerView.Adapter<AlarmsRvAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val tvLabel: TextView = view.findViewById(R.id.tvLabel)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_alarm, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvTime.text = Utility.timeToString(alarmsList[position].time)
        viewHolder.tvLabel.text = alarmsList[position].label

        viewHolder.itemView.setOnClickListener {
            val action = AlarmsFragmentDirections.actionAlarmsFragmentToAlarmFormFragment()
            action.alarmId = alarmsList[position].id
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = alarmsList.size
}
