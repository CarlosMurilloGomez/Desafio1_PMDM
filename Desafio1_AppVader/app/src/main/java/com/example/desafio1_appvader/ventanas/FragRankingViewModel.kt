package com.example.desafio1_appvader.ventanas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio1_appvader.api.UsuarioNetwork
import com.example.desafio1_appvader.modelo.usuario.Usuario
import kotlinx.coroutines.launch
import retrofit2.Response

class FragRankingViewModel : ViewModel() {
    private val _usuarios = MutableLiveData<List<Usuario>>()
    val usuarios: LiveData<List<Usuario>> get() = _usuarios

    fun obtenerRankingVM() {
        viewModelScope.launch {
            val response: Response<MutableList<Usuario>> = UsuarioNetwork.retrofit.obtenerRanking()
            _usuarios.value = response.body()
        }
    }
}