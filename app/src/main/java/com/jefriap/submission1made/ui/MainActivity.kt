package com.jefriap.submission1made.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.jefriap.submission1made.R
import com.jefriap.submission1made.databinding.ActivityMainBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.topAppBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragment)
        val navController = navHost?.findNavController()

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_movie, R.id.navigation_tv_show, R.id.navigation_favorite
            )
        )
        if (navController != null) {
            setupActionBarWithNavController(navController, appBarConfiguration)
            binding.bottomNavigation.setupWithNavController(navController)
        }
    }
}