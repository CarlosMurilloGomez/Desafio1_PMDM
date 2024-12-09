package com.example.desafio1_appvader.ventanas.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio1_appvader.api.MisionNetwork
import com.example.desafio1_appvader.api.NaveNetwork
import com.example.desafio1_appvader.modelo.Tipo
import com.example.desafio1_appvader.modelo.mision.Mision
import com.example.desafio1_appvader.modelo.mision.MisionBombardeo
import com.example.desafio1_appvader.modelo.mision.MisionCaza
import com.example.desafio1_appvader.modelo.mision.MisionVuelo
import com.example.desafio1_appvader.modelo.nave.Nave
import com.example.desafio1_appvader.modelo.nave.TipoCargaPasajeros
import kotlinx.coroutines.launch
import retrofit2.Response

class FragAltaMisionesViewModel : ViewModel() {
    private val _naves = MutableLiveData<List<Nave>>()
    val naves: LiveData<List<Nave>> get() = _naves

    private val _idMisionInsertada = MutableLiveData<Int?>()
    val idMisionInsertada: LiveData<Int?> get() = _idMisionInsertada

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

    fun obtenerTiposMisionVM() {
        viewModelScope.launch {
            val response: Response<MutableList<Tipo>> = MisionNetwork.retrofit.obtenerTiposMision()
            _tipos.value = response.body()
        }
    }

    fun obtenerNavesPorTipoCargaPasajerosVM(datos: TipoCargaPasajeros) {
        viewModelScope.launch {
            val response: Response<MutableList<Nave>> = NaveNetwork.retrofit.obtenerNavesPorTipoCargaPasajeros(datos)
            _naves.value = response.body()
        }
    }

    fun registrarMisionVM(mision: Mision) {
        viewModelScope.launch {
            val response: Response<Int> = MisionNetwork.retrofit.registrarMision(mision)
            _idMisionInsertada.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun registrarVueloVM(mision: MisionVuelo) {
        viewModelScope.launch {
            val response: Response<Boolean> = MisionNetwork.retrofit.registrarVuelo(mision)
            _resRegistro.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun registrarBombardeoVM(mision: MisionBombardeo) {
        viewModelScope.launch {
            val response: Response<Boolean> = MisionNetwork.retrofit.registrarBombardeo(mision)
            _resRegistro.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun registrarCazaVM(mision: MisionCaza) {
        viewModelScope.launch {
            val response: Response<Boolean> = MisionNetwork.retrofit.registrarCaza(mision)
            _resRegistro.value = response.body()
            _errorCode.value = response.code()
        }
    }
}