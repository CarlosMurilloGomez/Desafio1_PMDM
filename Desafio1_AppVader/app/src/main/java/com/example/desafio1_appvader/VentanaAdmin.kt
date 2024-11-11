package com.example.desafio1_appvader

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.desafio1_appvader.databinding.ActivityVentanaAdminBinding
import com.google.android.material.navigation.NavigationView

class VentanaAdmin : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    lateinit var binding: ActivityVentanaAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVentanaAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbarAdmin)
        val drawerLayout: DrawerLayout = binding.drawerLayout


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.frag_cont_admin) as NavHostFragment
        navController = navHostFragment.navController

        //=======================================================
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_fragAltaPiloto),
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

        //=======================================================
        val navView: NavigationView = binding.navigationView
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_fragAltaPiloto -> {
                    navController.navigate(R.id.nav_fragAltaPiloto)
                    supportActionBar?.title = "ALTA DE PILOTOS"
                }

            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        binding.toolbarAdmin.title = "BIENVENIDO"
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val drawerLayout: DrawerLayout = binding.drawerLayout
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}