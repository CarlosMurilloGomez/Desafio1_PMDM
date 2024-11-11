package com.example.desafio1_appvader

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.desafio1_appvader.api.MainViewModel
import com.example.desafio1_appvader.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.frag_cont_login) as NavHostFragment
        navController = navHostFragment.navController


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_fragLogin -> {
                    binding.toolBarLogin.title = "INICIAR SESIÓN"

                }
                R.id.nav_fragActivarCuenta -> {
                    binding.toolBarLogin.title = "ACTIVAR CUENTA"
                }
            }
        }

    }
}
