package com.example.desafio1_appvader.ventanas.admin

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.desafio1_appvader.R
import com.example.desafio1_appvader.api.MainViewModel
import com.example.desafio1_appvader.databinding.ActivityVentanaAdminBinding
import com.example.desafio1_appvader.ventanas.FragRanking
import com.google.android.material.navigation.NavigationView

class VentanaAdmin : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    lateinit var binding: ActivityVentanaAdminBinding
    private val mainViewModel: MainViewModel by viewModels()
    var idFragVolver = R.id.nav_fragRanking

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVentanaAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel.iniciarSesionVM(intent.getIntExtra("idUsuario", 0))

        setSupportActionBar(binding.toolbarAdmin)
        val drawerLayout: DrawerLayout = binding.drawerLayout

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.frag_cont_admin) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_fragRanking, R.id.nav_fragBajaPiloto, R.id.nav_fragBajaNave, R.id.nav_fragBajaMisiones),
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

        val navView: NavigationView = binding.navigationView
        navController.addOnDestinationChangedListener{ _, destination, _ ->
            when (destination.id) {
                R.id.nav_fragRanking -> {
                    supportActionBar?.title = resources.getString(R.string.tituloRanking)
                    idFragVolver = R.id.nav_fragRanking
                }
                R.id.nav_fragBajaPiloto -> {
                    supportActionBar?.title = resources.getString(R.string.tituloBajaPiloto)
                    idFragVolver = R.id.nav_fragBajaPiloto
                }
                R.id.nav_fragBajaNave -> {
                    supportActionBar?.title = resources.getString(R.string.tituloBajaNave)
                    idFragVolver = R.id.nav_fragBajaNave
                }
                R.id.nav_fragBajaMisiones -> {
                    supportActionBar?.title = resources.getString(R.string.tituloBajaMisiones)
                    idFragVolver = R.id.nav_fragBajaMisiones

                }
                R.id.nav_fragAltaPiloto -> {
                    supportActionBar?.title = resources.getString(R.string.tituloAltaPiloto)
                    idFragVolver = R.id.nav_fragAltaPiloto
                }
                R.id.nav_fragAltaNave -> {
                    supportActionBar?.title = resources.getString(R.string.tituloAltaNave)
                    idFragVolver = R.id.nav_fragAltaNave
                }
                R.id.nav_fragAltaMisiones -> {
                    supportActionBar?.title = resources.getString(R.string.tituloAltaMisiones)
                    idFragVolver = R.id.nav_fragAltaMisiones
                }
                R.id.nav_fragAsignarMisiones -> {
                    supportActionBar?.title = resources.getString(R.string.tituloAsignarMisiones)
                }
            }
        }
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_fragRanking -> navController.navigate(R.id.nav_fragRanking)
                R.id.nav_fragBajaPiloto -> navController.navigate(R.id.nav_fragBajaPiloto)
                R.id.nav_fragBajaNave -> navController.navigate(R.id.nav_fragBajaNave)
                R.id.nav_fragBajaMisiones -> navController.navigate(R.id.nav_fragBajaMisiones)
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
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
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.frag_cont_admin) as NavHostFragment
                val fragmentoActual = navHostFragment.childFragmentManager.primaryNavigationFragment
                if ((fragmentoActual is FragRanking) || (fragmentoActual is FragBajaPiloto) || (fragmentoActual is FragBajaNave) || (fragmentoActual is FragBajaMisiones)) {
                    binding.drawerLayout.openDrawer(GravityCompat.START)
                }else if (fragmentoActual is FragAltaPiloto) {
                    navController.navigate(R.id.nav_fragBajaPiloto)
                }else if (fragmentoActual is FragAltaNave) {
                    navController.navigate(R.id.nav_fragBajaNave)
                }else if (fragmentoActual is FragAltaMisiones) {
                    navController.navigate(R.id.nav_fragBajaMisiones)
                }else {
                    navController.navigate(idFragVolver)
                }
                true
            }
            R.id.nav_fragPerfil -> {
                navController.navigate(R.id.nav_fragPerfil)
                supportActionBar?.title = resources.getString(R.string.tituloPerfil)
                true
            }
            R.id.nav_cerrarSesion -> {
                Toast.makeText(this, resources.getString(R.string.msjCerrarSesion), Toast.LENGTH_SHORT).show()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }





}