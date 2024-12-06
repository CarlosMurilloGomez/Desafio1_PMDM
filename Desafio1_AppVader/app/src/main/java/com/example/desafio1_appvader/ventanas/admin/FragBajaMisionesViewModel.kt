package com.example.desafio1_appvader.ventanas.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio1_appvader.api.MisionNetwork
import com.example.desafio1_appvader.modelo.Tipo
import com.example.desafio1_appvader.modelo.mision.Mision
import com.example.desafio1_appvader.modelo.mision.MisionBombardeo
import com.example.desafio1_appvader.modelo.mision.MisionCaza
import com.example.desafio1_appvader.modelo.mision.MisionVuelo
import kotlinx.coroutines.launch
import retrofit2.Response

class FragBajaMisionesViewModel : ViewModel() {
    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    fun restablecerError(){
        _errorCode.value = null
    }
    private val _misiones = MutableLiveData<List<Mision>>()
    val misiones: LiveData<List<Mision>> get() = _misiones

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

    fun obtenerMisionesVM() {
        viewModelScope.launch {
            val response: Response<MutableList<Mision>> = MisionNetwork.retrofit.obtenerMisiones()
            _misiones.value = response.body()
        }
    }

    fun eliminarMisionVM(id: Int) {
        viewModelScope.launch {
            val response: Response<Boolean> = MisionNetwork.retrofit.eliminarMision(id)
            _errorCode.value = response.code()
            obtenerMisionesVM()
        }
    }

    fun obtenerVueloPorIdVM(idMision: Int) {
        viewModelScope.launch {
            val response: Response<MisionVuelo?> = MisionNetwork.retrofit.obtenerVueloPorId(idMision)
            _vuelo.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun obtenerBombardeoPorIdVM(idMision: Int) {
        viewModelScope.launch {
            val response: Response<MisionBombardeo?> = MisionNetwork.retrofit.obtenerBombardeoPorId(idMision)
            _bombardeo.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun obtenerCazaPorIdVM(idMision: Int) {
        viewModelScope.launch {
            val response: Response<MisionCaza?> = MisionNetwork.retrofit.obtenerCazaPorId(idMision)
            _caza.value = response.body()
            _errorCode.value = response.code()
        }
    }
}