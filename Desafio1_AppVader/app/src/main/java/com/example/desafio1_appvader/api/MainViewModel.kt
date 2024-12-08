package com.example.desafio1_appvader.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.Cloudinary
import com.example.desafio1_appvader.modelo.Cadena
import com.example.desafio1_appvader.modelo.Tipo
import com.example.desafio1_appvader.modelo.mision.Asignacion
import com.example.desafio1_appvader.modelo.mision.Mision
import com.example.desafio1_appvader.modelo.mision.MisionBombardeo
import com.example.desafio1_appvader.modelo.mision.MisionCaza
import com.example.desafio1_appvader.modelo.mision.MisionMostrar
import com.example.desafio1_appvader.modelo.mision.MisionVuelo
import com.example.desafio1_appvader.modelo.nave.Nave
import com.example.desafio1_appvader.modelo.nave.TipoCargaPasajeros
import com.example.desafio1_appvader.modelo.usuario.Usuario
import com.example.desafio1_appvader.modelo.usuario.UsuarioLogIn
import com.example.desafio1_appvader.parametros.Parametros
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.File

class MainViewModel : ViewModel() {

    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    fun restablecerError(){
        _errorCode.value = null
    }

    private val _usuarioLogeado = MutableLiveData<Usuario?>()
    val usuarioLogeado: LiveData<Usuario?> get() = _usuarioLogeado

    fun iniciarSesionVM(id: Int){
        viewModelScope.launch {
            val response: Response<Usuario?> = UsuarioNetwork.retrofit.obtenerUsuarioPorId(id)
            _usuarioLogeado.value = response.body()
        }
    }
    fun cerrarSesionVM(){
        _usuarioLogeado.value = null
    }


    //USUARIO
    private val _usuario = MutableLiveData<Usuario?>()
    val usuario: LiveData<Usuario?> get() = _usuario

    fun restablecerUsuario(){
        _usuario.value = null
    }

    fun loginVM(datosLogIn: UsuarioLogIn) {
        viewModelScope.launch {
            val response: Response<Usuario?> = UsuarioNetwork.retrofit.login(datosLogIn)
            _usuario.value = response.body()
            _usuarioLogeado.value = response.body()
            _errorCode.value = response.code()
        }
    }

}