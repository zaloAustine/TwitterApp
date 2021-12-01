package com.zalocoders.twitterapp.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.zalocoders.twitterapp.R
import com.zalocoders.twitterapp.databinding.ActivityHomeBinding
import com.zalocoders.twitterapp.utils.makeStatusBarTransparent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
	
	private lateinit var binding:ActivityHomeBinding
	private lateinit var appBarConfiguration: AppBarConfiguration
	
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityHomeBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		
		val navHostFragment =
				supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		val navController = navHostFragment.navController
		
		appBarConfiguration = AppBarConfiguration(navController.graph)
		binding.toolbar.setupWithNavController(navController, appBarConfiguration)
	}
}