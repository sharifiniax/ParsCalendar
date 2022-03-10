package com.sharifiniax.parscalendar


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.orhanobut.logger.Logger
import com.sharifiniax.parscalendar.data.ParsCalendarEvent
import com.sharifiniax.parscalendar.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration : AppBarConfiguration
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        val navController=findNavController(R.id.fragment_container_view)

        appBarConfiguration= AppBarConfiguration(
            navController.graph
            ,binding.drawerLayout)
        setSupportActionBar(binding.appBarMain.toolbar)

        supportActionBar?.title=""
       binding.appBarMain.toolbar.setNavigationIcon(R.drawable.ic_menu)

        setupActionBarWithNavController(navController,appBarConfiguration)
        binding.navView.setupWithNavController(navController)

//        makeTest()
    }




    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragment_container_view).navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}