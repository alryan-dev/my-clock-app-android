package com.example.myclock.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myclock.R
import com.example.myclock.databinding.ActivityAlarmsBinding
import com.example.myclock.factories.AlarmsViewModelFactory
import com.example.myclock.models.Alarm
import com.example.myclock.repositories.AlarmsRepository
import com.example.myclock.viewmodels.AlarmsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmsActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    lateinit var binding: ActivityAlarmsBinding

    @Inject
    lateinit var alarmsRepository: AlarmsRepository
    private val alarmsViewModel: AlarmsViewModel by viewModels {
        AlarmsViewModelFactory(alarmsRepository, application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolbar()
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.tbAlarms)

        // Set up navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Check current destination
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.alarmFormFragment) {
                binding.tbAlarms.setNavigationIcon(R.drawable.ic_close)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_alarms_fragment_to_alarm_form_fragment) {
            alarmsViewModel.selectedAlarm.value = Alarm()
        }
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}