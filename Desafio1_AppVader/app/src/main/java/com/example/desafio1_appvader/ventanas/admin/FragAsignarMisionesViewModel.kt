package com.example.desafio1_appvader.ventanas.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio1_appvader.api.MisionNetwork
import com.example.desafio1_appvader.api.UsuarioNetwork
import com.example.desafio1_appvader.modelo.Tipo
import com.example.desafio1_appvader.modelo.mision.Asignacion
import com.example.desafio1_appvader.modelo.mision.Mision
import com.example.desafio1_appvader.modelo.mision.MisionBombardeo
import com.example.desafio1_appvader.modelo.mision.MisionCaza
import com.example.desafio1_appvader.modelo.mision.MisionVuelo
import com.example.desafio1_appvader.modelo.usuario.Usuario
import kotlinx.coroutines.launch
import retrofit2.Response

class FragAsignarMisionesViewModel : ViewModel() {
    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    fun restablecerError(){
        _errorCode.value = null
    }
    private val _resAsignacion = MutableLiveData<Boolean>()
    val resAsignacion: LiveData<Boolean> get() = _resAsignacion

    fun restablecerResAsignacion(){
        _resAsignacion.value = false
    }

    private val _piloto = MutableLiveData<Usuario?>()
    val piloto: LiveData<Usuario?> get() = _piloto

    private val _pilotos = MutableLiveData<List<Usuario>>()
    val pilotos: LiveData<List<Usuario>> get() = _pilotos

    private val _mision = MutableLiveData<Mision?>()
    val mision: LiveData<Mision?> get() = _mision

    private val _tipos = MutableLiveData<List<Tipo>>()
    val tipos: LiveData<List<Tipo>> get() = _tipos

    private val _vuelo = MutableLiveData<MisionVuelo?>()
    val vuelo: LiveData<MisionVuelo?> get() = _vuelo

    private val _bombardeo = MutableLiveData<MisionBombardeo?>()
    val bombardeo: LiveData<MisionBombardeo?> get() = _bombardeo

    private val _caza = MutableLiveData<MisionCaza?>()
    val caza: LiveData<MisionCaza?> get() = _caza

    fun obtenerTiposMisionVM() {
        viewModelScope.launch {
            val response: Response<MutableList<Tipo>> = MisionNetwork.retrofit.obtenerTiposMision()
            _tipos.value = response.body()
        }
    }

    fun obtenerMisionPorIdVM(id: Int) {
        viewModelScope.launch {
            val response: Response<Mision?> = MisionNetwork.retrofit.obtenerMisionPorId(id)
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

    fun obtenerPilotosVM() {
        viewModelScope.launch {
            val response: Response<MutableList<Usuario>> = UsuarioNetwork.retrofit.obtenerPilotos()
            _pilotos.value = response.body()
        }
    }

    fun obtenerPilotoPorIdVM(id: Int) {
        viewModelScope.launch {
            val response: Response<Usuario?> = UsuarioNetwork.retrofit.obtenerUsuarioPorId(id)
            _piloto.value = response.body()
        }
    }

    fun asignarMisionVM(asignacion: Asignacion) {
        viewModelScope.launch {
            val response: Response<Boolean> = MisionNetwork.retrofit.asignarMision(asignacion)
            _resAsignacion.value = response.body()
            _errorCode.value = response.code()
        }
    }
}