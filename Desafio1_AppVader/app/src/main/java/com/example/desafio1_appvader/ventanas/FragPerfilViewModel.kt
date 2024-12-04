package com.example.desafio1_appvader.ventanas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.Cloudinary
import com.example.desafio1_appvader.api.UsuarioNetwork
import com.example.desafio1_appvader.modelo.Cadena
import com.example.desafio1_appvader.modelo.usuario.UsuarioPerfil
import com.example.desafio1_appvader.parametros.Parametros
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.File

class FragPerfilViewModel : ViewModel() {
    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    fun restablecerError(){
        _errorCode.value = null
    }

    private val _urlfoto = MutableLiveData<String?>()
    val urlfoto: LiveData<String?> get() = _urlfoto

    fun restablecerUrlFoto(){
        _urlfoto.value = null
    }

    private val _rol = MutableLiveData<String?>()
    val rol: LiveData<String?> get() = _rol

    fun restablecerRol(){
        _rol.value = null
    }
    private val _nivel = MutableLiveData<String?>()
    val nivel: LiveData<String?> get() = _nivel

    fun restablecerNivel(){
        _nivel.value = null
    }

    private val _resModificar = MutableLiveData<Boolean>()
    val resModificar: LiveData<Boolean> get() = _resModificar

    fun restablecerResModificar(){
        _resModificar.value = false
    }


    fun modificarPerfilUsuarioVM(perfil: UsuarioPerfil) {
        viewModelScope.launch {
            val response: Response<Boolean> = UsuarioNetwork.retrofit.modificarPerfilUsuario(perfil)
            _resModificar.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun subirImgen(file: File){
        viewModelScope.launch {
            val config = mapOf(
                "cloud_name" to Parametros.cloud_name,
                "api_key" to Parametros.api_key,
                "api_secret" to Parametros.api_secret
            )
            val cloudinary = Cloudinary(config)
            val options = mapOf("folder" to "Desafio1")
            val uploadResult = withContext(Dispatchers.IO) {
                cloudinary.uploader().upload(file, options)
            }
            _urlfoto.value = uploadResult["url"].toString()
        }

    }

    fun obtenerRolPorIdVM(idUsuario: Int) {
        viewModelScope.launch {
            val response: Response<Cadena?> = UsuarioNetwork.retrofit.obtenerRolPorId(idUsuario)
            _rol.value = response.body()?.texto
            _errorCode.value = response.code()
        }
    }

    fun obtenerNivelPorIdVM(idUsuario: Int) {
        viewModelScope.launch {
            val response: Response<Cadena?> = UsuarioNetwork.retrofit.obtenerNivelPorId(idUsuario)
            _nivel.value = response.body()?.texto
            _errorCode.value = response.code()
        }
    }
}