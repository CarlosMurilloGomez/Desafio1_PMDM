package com.example.desafio1_appvader.ventanas.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio1_appvader.api.UsuarioNetwork
import com.example.desafio1_appvader.modelo.usuario.Usuario
import kotlinx.coroutines.launch
import retrofit2.Response

class FragAltaPilotoViewModel : ViewModel() {
    private val _resRegistro = MutableLiveData<Boolean>()
    val resRegistro: LiveData<Boolean> get() = _resRegistro

    fun restablecerResRegistro(){
        _resRegistro.value = false
    }

    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    fun registrarUsuarioVM(usuario: Usuario) {
        viewModelScope.launch {
            val response: Response<Boolean> = UsuarioNetwork.retrofit.registrarUsuario(usuario)
            _resRegistro.value = response.body()
            _errorCode.value = response.code()
        }
    }
}