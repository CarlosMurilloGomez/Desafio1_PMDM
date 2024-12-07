package com.example.desafio1_appvader.ventanas.piloto

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.desafio1_appvader.R
import com.example.desafio1_appvader.api.MainViewModel
import com.example.desafio1_appvader.databinding.ActivityVentanaAdminBinding
import com.example.desafio1_appvader.databinding.ActivityVentanaPilotoBinding
import com.example.desafio1_appvader.ventanas.FragRanking
import com.google.android.material.bottomnavigation.BottomNavigationView

class VentanaPiloto : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    lateinit var binding: ActivityVentanaPilotoBinding
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVentanaPilotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel.iniciarSesionVM(intent.getIntExtra("idUsuario", 0))

        setSupportActionBar(binding.toolbarPiloto)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.frag_cont_piloto) as NavHostFragment
        navController = navHostFragment.navController

        val navView: BottomNavigationView = binding.bottomNavPiloto

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_fragRanking)
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_fragRanking -> {
                    supportActionBar?.title = "RANKING"
                }
                R.id.nav_fragMisionesPendientes -> {
                    supportActionBar?.title = "MISIONES PENDIENTES"
                }
            }
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
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.frag_cont_piloto) as NavHostFragment
                val currentFragment = navHostFragment.childFragmentManager.primaryNavigationFragment
                if (!(currentFragment is FragRanking)) {
                    navController.navigate(R.id.nav_fragRanking)
                    supportActionBar?.title = "RANKING"
                }
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