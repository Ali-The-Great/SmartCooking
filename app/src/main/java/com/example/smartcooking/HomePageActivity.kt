package com.example.smartcooking


import RecipesFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePageActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        val selectedMenuItem = intent.getIntExtra("selectedMenuItem", R.id.menu_searcher)
        bottomNavigationView.selectedItemId = selectedMenuItem

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.menu_chat -> ChatFragment()
                R.id.menu_searcher -> SearchFragment()
                R.id.menu_recipes -> RecipesFragment()
                else -> SearchFragment()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()

            true
        }

        loadFragment(selectedMenuItem)
    }

    private fun loadFragment(menuItemId: Int) {
        val fragment: Fragment = when (menuItemId) {
            R.id.menu_chat -> ChatFragment()
            R.id.menu_searcher -> SearchFragment()
            R.id.menu_recipes -> RecipesFragment()
            else -> SearchFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
