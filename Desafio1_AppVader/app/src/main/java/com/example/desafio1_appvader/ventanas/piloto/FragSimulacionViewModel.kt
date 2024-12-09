package com.example.desafio1_appvader.ventanas.piloto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio1_appvader.api.MisionNetwork
import com.example.desafio1_appvader.api.UsuarioNetwork
import com.example.desafio1_appvader.modelo.Cadena
import com.example.desafio1_appvader.modelo.mision.MisionBombardeo
import com.example.desafio1_appvader.modelo.mision.MisionCaza
import com.example.desafio1_appvader.modelo.mision.MisionMostrar
import com.example.desafio1_appvader.modelo.mision.MisionVuelo
import kotlinx.coroutines.launch
import retrofit2.Response

class FragSimulacionViewModel : ViewModel() {
    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    private val _resSimulacion = MutableLiveData<Boolean>()
    val resSimulacion: LiveData<Boolean> get() = _resSimulacion

    private val _mision = MutableLiveData<MisionMostrar?>()
    val mision: LiveData<MisionMostrar?> get() = _mision

    private val _vuelo = MutableLiveData<MisionVuelo?>()
    val vuelo: LiveData<MisionVuelo?> get() = _vuelo

    private val _bombardeo = MutableLiveData<MisionBombardeo?>()
    val bombardeo: LiveData<MisionBombardeo?> get() = _bombardeo

    private val _caza = MutableLiveData<MisionCaza?>()
    val caza: LiveData<MisionCaza?> get() = _caza


    private val _nivel = MutableLiveData<String?>()
    val nivel: LiveData<String?> get() = _nivel

    fun obtenerMisionAsignacionPorIdVM(id: Int) {
        viewModelScope.launch {
            val response: Response<MisionMostrar?> = MisionNetwork.retrofit.obtenerMisionAsignacionPorId(id)
            _mision.value = response.body()
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

    fun obtenerNivelPorIdVM(idUsuario: Int) {
        viewModelScope.launch {
            val response: Response<Cadena?> = UsuarioNetwork.retrofit.obtenerNivelPorId(idUsuario)
            _nivel.value = response.body()?.texto
        }
    }

    fun actualizarEstadoVM(id: Int, estado: Int) {
        viewModelScope.launch {
            val response: Response<Boolean> = MisionNetwork.retrofit.actualizarEstado(id, estado)
            _errorCode.value = response.code()
        }
    }

    fun modificarExperienciaUsuarioVM(id: Int, experiencia: Int) {
        viewModelScope.launch {
            val response: Response<Boolean> = UsuarioNetwork.retrofit.modificarExperienciaUsuario(id, experiencia)
            _errorCode.value = response.code()
        }
    }

    fun terminarSimulacionVM(resultado: Boolean){
        _resSimulacion.value = resultado

    }
}