package com.sharifiniax.parscalendar


import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.orhanobut.logger.Logger
import com.sharifiniax.parscalendar.databinding.ActivityMainBinding
import com.sharifiniax.parscalendar.ui.todo.TodoFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var appBarConfiguration : AppBarConfiguration
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        val navController=findNavController(R.id.fragment_container_view)

        appBarConfiguration= AppBarConfiguration(
            setOf(R.id.MainFragment,R.id.todoFragment),
            binding.drawerLayout)
        toolbar=binding.appBarMain.toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.title=""
        setupActionBarWithNavController(navController,appBarConfiguration)
        binding.navView.setupWithNavController(navController)


    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action === MotionEvent.ACTION_DOWN) {

            val fragment= supportFragmentManager
                .findFragmentById(R.id.fragment_container_view)
                ?.childFragmentManager?.fragments?.get(0)


            if ( fragment != null && fragment is TodoFragment) {

                fragment.hideBottomSheetFromOutSide(ev)
            }
        }
        return super.dispatchTouchEvent(ev)
    }




    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragment_container_view).navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}