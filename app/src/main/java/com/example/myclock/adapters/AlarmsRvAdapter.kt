package com.example.myclock.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myclock.R
import com.example.myclock.models.Alarm
import java.text.SimpleDateFormat
import java.util.*

class AlarmsRvAdapter(private val alarmsList: List<Alarm>) :
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
        SimpleDateFormat("HH:mm a", Locale.getDefault()).let {
            viewHolder.tvTime.text = it.format(alarmsList[position].time)
        }
        viewHolder.tvLabel.text = alarmsList[position].label
    }

    override fun getItemCount(): Int = alarmsList.size
}
