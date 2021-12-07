package com.example.myclock.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myclock.R
import com.example.myclock.adapters.AlarmsRvAdapter
import com.example.myclock.databinding.FragmentAlarmsBinding
import com.example.myclock.models.Alarm
import com.example.myclock.utilities.Utility
import com.example.myclock.viewmodels.AlarmsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AlarmsFragment : Fragment() {
    private val alarmsViewModel: AlarmsViewModel by activityViewModels()
    private val alarmsList = mutableListOf<Alarm>()
    private lateinit var alarmsRvAdapter: AlarmsRvAdapter
    private var _binding: FragmentAlarmsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlarmsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpAlarmsRecyclerView()

        // Set observers
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                alarmsViewModel.alarms.collectLatest {
                    Utility.print("alarmsViewModel.alarms.collect")
                    Utility.print(it.size.toString())
                    alarmsList.clear()
                    alarmsList.addAll(it)
                    alarmsRvAdapter.notifyDataSetChanged()
                }

                // alarmsList.addAll(alarmsViewModel.alarms)
                // alarmsRvAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setUpAlarmsRecyclerView() {
        // Set up RecyclerView
        binding.rvAlarms.layoutManager = LinearLayoutManager(context)
        binding.rvAlarms.addItemDecoration(
                DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )

        // Set up adapter
        val onItemSelectListener = object : AlarmsRvAdapter.OnItemSelectListener {
            override fun onItemSelect(position: Int) {
                alarmsViewModel.selectedAlarm.value = alarmsViewModel.alarms.value[position]
                val action = AlarmsFragmentDirections.actionAlarmsFragmentToAlarmFormFragment()
                findNavController().navigate(action)
            }
        }

        val onItemUpdateListener = object : AlarmsRvAdapter.OnItemUpdateListener {
            override fun onItemUpdate(any: Any, position: Int) {
                val alarm: Alarm = any as Alarm
                alarmsViewModel.toggleAlarmStatus(alarm)
            }
        }

        alarmsRvAdapter = AlarmsRvAdapter(
                alarmsList,
                onItemSelectListener,
                onItemUpdateListener,
                viewLifecycleOwner
        )

        binding.rvAlarms.adapter = alarmsRvAdapter

        // Set swipe to delete callback
        val itemTouchHelperCallback = object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                alarmsViewModel.deleteAlarm(alarmsList[viewHolder.bindingAdapterPosition])
                alarmsList.removeAt(viewHolder.bindingAdapterPosition)
                alarmsRvAdapter.notifyItemRemoved(viewHolder.bindingAdapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvAlarms)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_alarms, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}