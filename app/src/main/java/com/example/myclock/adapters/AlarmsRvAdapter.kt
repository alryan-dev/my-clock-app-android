package com.example.myclock.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.example.myclock.R
import com.example.myclock.activities.AlarmFormActivity
import com.example.myclock.models.Alarm
import com.example.myclock.utilities.Utility
import com.example.myclock.viewmodels.AlarmsViewModel
import com.google.gson.Gson

class AlarmsRvAdapter(
    private val context: Context,
    private var alarmsList: List<Alarm>,
    private val alarmsViewModel: AlarmsViewModel
) :
    RecyclerView.Adapter<AlarmsRvAdapter.ViewHolder>() {
    private val startForResult =
        (context as ComponentActivity).registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data != null) {
                    val alarm = data.getParcelableExtra<Alarm>("ALARM")
                    alarm?.let { it1 ->
                        editPosition?.let {
                            alarmsViewModel.alarmsLiveData.value?.let { it2 ->
                                it2[it] = it1
                                alarmsList = it2
                                notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
        }
    private var editPosition: Int? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val tvLabel: TextView = view.findViewById(R.id.tvLabel)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_alarm, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvTime.text = Utility.timeToString(alarmsList[position].time)
        viewHolder.tvLabel.text = alarmsList[position].label

        viewHolder.itemView.setOnClickListener {
            editPosition = position
            val intent = Intent(context, AlarmFormActivity::class.java)
            intent.putExtra("ALARM", alarmsList[position])
            startForResult.launch(intent)
        }
    }

    override fun getItemCount(): Int = alarmsList.size
}
