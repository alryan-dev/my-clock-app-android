package com.example.myclock.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.myclock.BR
import com.example.myclock.databinding.ItemAlarmBinding
import com.example.myclock.models.Alarm
import com.example.myclock.utilities.Utility

class AlarmsRvAdapter constructor(
    private var alarmsList: List<Alarm>,
    private val onItemSelectListener: OnItemSelectListener,
    private val onItemEnabledListener: OnItemUpdateListener,
    private val lifecycleOwner: LifecycleOwner,
) :
    RecyclerView.Adapter<AlarmsRvAdapter.ViewHolder>() {

    class ViewHolder(
        val binding: ItemAlarmBinding,
        onItemSelectListener: OnItemSelectListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val currentPosition = bindingAdapterPosition
                if (currentPosition != RecyclerView.NO_POSITION) {
                    onItemSelectListener.onItemSelect(currentPosition)
                }
            }
        }
    }

    interface OnItemSelectListener {
        fun onItemSelect(position: Int)
    }

    interface OnItemUpdateListener {
        fun onItemUpdate(any: Any, position: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAlarmBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding, onItemSelectListener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Utility.print(alarmsList[position].time.timeInMillis.toString())
        val enabled = MutableLiveData(alarmsList[position].enabled)

        enabled.observe(lifecycleOwner, {
            viewHolder.binding.smEnabled.postOnAnimation {
                val currentPosition = viewHolder.bindingAdapterPosition
                if (currentPosition != RecyclerView.NO_POSITION && it != alarmsList[currentPosition].enabled) {
                    alarmsList[currentPosition].enabled = it
                    onItemEnabledListener.onItemUpdate(alarmsList[currentPosition], currentPosition)
                }
            }
        })

        viewHolder.binding.setVariable(BR.alarm, alarmsList[position])
        viewHolder.binding.setVariable(BR.enabled, enabled)
        viewHolder.binding.lifecycleOwner = lifecycleOwner
        viewHolder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = alarmsList.size
}
