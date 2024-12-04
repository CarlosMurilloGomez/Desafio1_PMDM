package com.example.desafio1_appvader.ventanas.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.Cloudinary
import com.example.desafio1_appvader.api.UsuarioNetwork
import com.example.desafio1_appvader.modelo.usuario.UsuarioPerfil
import com.example.desafio1_appvader.parametros.Parametros
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.File

class FragActivarCuentaViewModel : ViewModel() {
    private val _resModificar = MutableLiveData<Boolean>()
    val resModificar: LiveData<Boolean> get() = _resModificar

    fun restablecerResModificar(){
        _resModificar.value = false
    }
    private val _resActivar = MutableLiveData<Boolean>()
    val resActivar: LiveData<Boolean> get() = _resActivar

    fun restablecerResActivar(){
        _resActivar.value = false
    }

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

    fun modificarPerfilUsuarioVM(perfil: UsuarioPerfil) {
        viewModelScope.launch {
            val response: Response<Boolean> = UsuarioNetwork.retrofit.modificarPerfilUsuario(perfil)
            _resModificar.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun activarCuentaVM(id: Int) {
        viewModelScope.launch {
            val response: Response<Boolean> = UsuarioNetwork.retrofit.activarCuenta(id)
            _resActivar.value = response.body()
            if (!response.isSuccessful) {
                _errorCode.value = response.code()
            }

        }
    }
}