package com.example.desafio1_appvader.ventanas.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio1_appvader.api.UsuarioNetwork
import com.example.desafio1_appvader.modelo.usuario.Usuario
import kotlinx.coroutines.launch
import retrofit2.Response

class FragBajaPilotoViewModel : ViewModel() {
    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    fun restablecerError(){
        _errorCode.value = null
    }

    private val _usuarios = MutableLiveData<List<Usuario>>()
    val usuarios: LiveData<List<Usuario>> get() = _usuarios

    fun obtenerPilotosVM() {
        viewModelScope.launch {
            val response: Response<MutableList<Usuario>> = UsuarioNetwork.retrofit.obtenerPilotos()
            _usuarios.value = response.body()
        }
    }

    fun eliminarUsuarioVM(id: Int) {
        viewModelScope.launch {
            val response: Response<Boolean> = UsuarioNetwork.retrofit.eliminarUsuario(id)
            _errorCode.value = response.code()
            obtenerPilotosVM()
        }
    }
}