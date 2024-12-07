package com.example.desafio1_appvader.ventanas.piloto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio1_appvader.api.MisionNetwork
import com.example.desafio1_appvader.modelo.Tipo
import com.example.desafio1_appvader.modelo.mision.Asignacion
import com.example.desafio1_appvader.modelo.mision.Mision
import com.example.desafio1_appvader.modelo.mision.MisionBombardeo
import com.example.desafio1_appvader.modelo.mision.MisionCaza
import com.example.desafio1_appvader.modelo.mision.MisionMostrar
import com.example.desafio1_appvader.modelo.mision.MisionVuelo
import kotlinx.coroutines.launch
import retrofit2.Response

class FragMisionesPendientesViewModel : ViewModel() {
    private val _misiones = MutableLiveData<List<MisionMostrar>>()
    val misiones: LiveData<List<MisionMostrar>> get() = _misiones

    private val _vuelo = MutableLiveData<MisionVuelo?>()
    val vuelo: LiveData<MisionVuelo?> get() = _vuelo

    private val _bombardeo = MutableLiveData<MisionBombardeo?>()
    val bombardeo: LiveData<MisionBombardeo?> get() = _bombardeo

    private val _caza = MutableLiveData<MisionCaza?>()
    val caza: LiveData<MisionCaza?> get() = _caza

    fun obtenerMisionAsignacionesPorUsuarioVM(idUsuario: Int) {
        viewModelScope.launch {
            val response: Response<MutableList<MisionMostrar>> = MisionNetwork.retrofit.obtenerMisionAsignacionesPorUsuario(idUsuario)
            _misiones.value = response.body()
        }
    }

    fun obtenerVueloPorIdVM(idMision: Int) {
        viewModelScope.launch {
            val response: Response<MisionVuelo?> = MisionNetwork.retrofit.obtenerVueloPorId(idMision)
            _vuelo.value = response.body()
        }
    }

    fun obtenerBombardeoPorIdVM(idMision: Int) {
        viewModelScope.launch {
            val response: Response<MisionBombardeo?> = MisionNetwork.retrofit.obtenerBombardeoPorId(idMision)
            _bombardeo.value = response.body()
        }
    }

    fun obtenerCazaPorIdVM(idMision: Int) {
        viewModelScope.launch {
            val response: Response<MisionCaza?> = MisionNetwork.retrofit.obtenerCazaPorId(idMision)
            _caza.value = response.body()
        }
    }
}