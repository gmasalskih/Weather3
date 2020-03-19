package ru.gmasalskih.weather3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ru.gmasalskih.weather3.databinding.ActivityMainBinding
import ru.gmasalskih.weather3.utils.ObserveLifeCycle
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("--- onCreate")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout
        navController = findNavController(R.id.navHost)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    override fun onStart() {
        super.onStart()
        Timber.i("--- onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("--- onResume")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("--- onPause")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("--- onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("--- onDestroy")
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}
