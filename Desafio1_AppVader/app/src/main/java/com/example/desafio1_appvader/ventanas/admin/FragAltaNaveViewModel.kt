package com.example.desafio1_appvader.ventanas.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.Cloudinary
import com.example.desafio1_appvader.api.NaveNetwork
import com.example.desafio1_appvader.modelo.Tipo
import com.example.desafio1_appvader.modelo.nave.Nave
import com.example.desafio1_appvader.parametros.Parametros
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.File

class FragAltaNaveViewModel : ViewModel() {
    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    fun restablecerError(){
        _errorCode.value = null
    }

    private val _resRegistro = MutableLiveData<Boolean>()
    val resRegistro: LiveData<Boolean> get() = _resRegistro

    fun restablecerResRegistro(){
        _resRegistro.value = false
    }

    private val _tipos = MutableLiveData<List<Tipo>>()
    val tipos: LiveData<List<Tipo>> get() = _tipos

    private val _urlfoto = MutableLiveData<String?>()
    val urlfoto: LiveData<String?> get() = _urlfoto

    fun restablecerUrlFoto(){
        _urlfoto.value = null
    }

    fun obtenerTiposNavesVM(){
        viewModelScope.launch {
            val response: Response<MutableList<Tipo>> = NaveNetwork.retrofit.obtenerTiposNaves()
            _tipos.value = response.body()
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

    fun registrarNaveVM(nave: Nave) {
        viewModelScope.launch {
            val response: Response<Boolean> = NaveNetwork.retrofit.registrarNave(nave)
            _resRegistro.value = response.body()
            _errorCode.value = response.code()
        }
    }
}