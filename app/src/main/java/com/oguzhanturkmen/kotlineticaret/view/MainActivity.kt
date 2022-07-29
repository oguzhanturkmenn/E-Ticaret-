package com.oguzhanturkmen.kotlineticaret.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.oguzhanturkmen.kotlineticaret.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(this,R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)
       // val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
     //   val navController = navHostFragment.navController
      //  bottomNavigationView.setupWithNavController(navController)


    }
}