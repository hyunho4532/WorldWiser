package com.hyun.worldwiser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.navigation.NavigationBarView
import com.hyun.worldwiser.R
import com.hyun.worldwiser.ui.fragment.HomeFragment
import com.hyun.worldwiser.ui.fragment.ProfileFragment
import com.hyun.worldwiser.ui.fragment.TravelFragment

class MainActivity : AppCompatActivity() {

    private val homeFragment: HomeFragment = HomeFragment()
    private val travelFragment: TravelFragment = TravelFragment()
    private val profileFragment: ProfileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit()

        val navigationBarView: NavigationBarView = findViewById(R.id.bottom_navigation_view)

        navigationBarView.setOnItemSelectedListener(object: NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId){
                  R.id.home -> {
                      supportFragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit()
                      return true
                  }
                  R.id.travel -> {
                      supportFragmentManager.beginTransaction().replace(R.id.container, travelFragment).commit()
                      return true
                  }
                  R.id.profile -> {
                      supportFragmentManager.beginTransaction().replace(R.id.profile, profileFragment).commit()
                      return true
                  }
                }

                return false
            }
        })
    }
}