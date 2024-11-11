package com.example.desafio1_appvader

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.desafio1_appvader.api.MainViewModel
import com.example.desafio1_appvader.databinding.ActivityVentanaAdminBinding
import com.google.android.material.navigation.NavigationView

class VentanaAdmin : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    lateinit var binding: ActivityVentanaAdminBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVentanaAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel.iniciarSesionVM(intent.getIntExtra("idUsuario", 0))

        setSupportActionBar(binding.toolbarAdmin)
        val drawerLayout: DrawerLayout = binding.drawerLayout


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.frag_cont_admin) as NavHostFragment
        navController = navHostFragment.navController

        //=======================================================
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_fragPerfil, R.id.nav_fragAltaPiloto, R.id.nav_fragBajaPiloto),
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
                R.id.nav_fragBajaPiloto -> {
                    navController.navigate(R.id.nav_fragBajaPiloto)
                    supportActionBar?.title = "BAJA DE PILOTOS"
                }


            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        supportActionBar?.title = "BIENVENIDO"
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_puntos, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val drawerLayout: DrawerLayout = binding.drawerLayout
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            R.id.nav_fragPerfil -> {
                navController.navigate(R.id.nav_fragPerfil)
                supportActionBar?.title = "PERFIL"
                true
            }
            R.id.nav_cerrarSesion -> {
                Toast.makeText(this, "Cerrando Sesión", Toast.LENGTH_SHORT).show()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }




}