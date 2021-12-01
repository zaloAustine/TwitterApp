package com.zalocoders.twitterapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zalocoders.twitterapp.databinding.ActivityMainBinding
import com.zalocoders.twitterapp.ui.base.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
	
	private val activityScope = CoroutineScope(Dispatchers.Main)
	private lateinit var binding:ActivityMainBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		activityScope.launch {
			delay(3000)
			val intent = Intent(this@MainActivity, HomeActivity::class.java)
			startActivity(intent)
			finish()
		}
	}
	
	override fun onPause() {
		activityScope.cancel()
		super.onPause()
	}
}