package com.example.myclock.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myclock.R
import com.example.myclock.adapters.AlarmsRvAdapter
import com.example.myclock.models.Alarm
import com.example.myclock.viewmodels.AlarmsViewModel

class AlarmsFragment : Fragment() {
    private lateinit var alarmsRvAdapter: AlarmsRvAdapter
    private val alarmsViewModel: AlarmsViewModel by activityViewModels()
    private var alarmsList = mutableListOf<Alarm>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_alarms, container, false)
        initAlarmsRecyclerView(view)
        setHasOptionsMenu(true)

        alarmsViewModel.alarmsLiveData.observe(viewLifecycleOwner, {
            alarmsList.addAll(it)
            alarmsRvAdapter.notifyDataSetChanged()
        })

        return view
    }

    private fun initAlarmsRecyclerView(view: View) {
        // Init recyclerview layout
        val rvAlarms = view.findViewById<RecyclerView>(R.id.rvAlarms)
        rvAlarms.layoutManager = LinearLayoutManager(context)
        rvAlarms.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        // Init adapter
        context?.let {
            alarmsRvAdapter = AlarmsRvAdapter(it, alarmsList, alarmsViewModel)
            rvAlarms.adapter = alarmsRvAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_alarms, menu)
    }
}