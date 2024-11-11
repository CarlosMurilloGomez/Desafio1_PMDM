package com.example.desafio1_appvader

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        mainViewModel.usuario.observe(this) {
            if (it != null) {
                if (it.activo == 0){
                    navController.navigate(R.id.fragActivarCuenta)
                }else if (it.rol == 1) {
                    val intent = Intent(this, VentanaAdmin::class.java)
                    startActivity(intent)
                }else if (it.rol == 2){
                    //Ir a la activity de piloto
                }
            }
        }

        //android:theme="?attr/actionBarTheme"
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragLogin -> {
                    binding.toolBarLogin.title = "INICIAR SESIÓN"

                }
                R.id.fragActivarCuenta -> {
                    binding.toolBarLogin.title = "ACTIVAR CUENTA"
                }
            }
        }

    }
}
