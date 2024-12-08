package com.example.desafio1_appvader.ventanas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio1_appvader.api.UsuarioNetwork
import com.example.desafio1_appvader.modelo.usuario.Estadisticas
import com.example.desafio1_appvader.modelo.usuario.Usuario
import kotlinx.coroutines.launch
import retrofit2.Response

class FragRankingViewModel : ViewModel() {
    private val _usuarios = MutableLiveData<List<Usuario>>()
    val usuarios: LiveData<List<Usuario>> get() = _usuarios

    private val _estadisticas = MutableLiveData<Estadisticas?>()
    val estadisticas: LiveData<Estadisticas?> get() = _estadisticas

    fun restablecerEstadisticas(){
        _estadisticas.value = null
    }


    fun obtenerRankingVM() {
        viewModelScope.launch {
            val response: Response<MutableList<Usuario>> = UsuarioNetwork.retrofit.obtenerRanking()
            _usuarios.value = response.body()
        }
    }

    fun obtenerEstadisticasPorIdVM(idUsuario: Int){
        viewModelScope.launch {
            val response: Response<Estadisticas?> = UsuarioNetwork.retrofit.obtenerEstadisticasPorId(idUsuario)
            _estadisticas.value = response.body()
        }

    }
}