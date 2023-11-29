package com.example.byteduo.Controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.byteduo.R
import com.example.byteduo.adapter.MenuAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class CustomerMenu : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_menu)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.btm_nav)
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }
}