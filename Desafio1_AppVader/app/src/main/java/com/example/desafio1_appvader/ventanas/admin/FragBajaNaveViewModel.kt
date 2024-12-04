package com.example.desafio1_appvader.ventanas.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio1_appvader.api.NaveNetwork
import com.example.desafio1_appvader.modelo.Cadena
import com.example.desafio1_appvader.modelo.Tipo
import com.example.desafio1_appvader.modelo.nave.Nave
import kotlinx.coroutines.launch
import retrofit2.Response

class FragBajaNaveViewModel : ViewModel() {
    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    fun restablecerError(){
        _errorCode.value = null
    }
    private val _naves = MutableLiveData<List<Nave>>()
    val naves: LiveData<List<Nave>> get() = _naves

    private val _tipos = MutableLiveData<List<Tipo>>()
    val tipos: LiveData<List<Tipo>> get() = _tipos



    fun obtenerNavesVM(){
        viewModelScope.launch {
            val response: Response<MutableList<Nave>> = NaveNetwork.retrofit.obtenerNaves()
            _naves.value = response.body()
        }
    }

    fun eliminarNaveVM(matricula: String) {
        viewModelScope.launch {
            val response: Response<Boolean> = NaveNetwork.retrofit.eliminarNave(matricula)
            _errorCode.value = response.code()
            obtenerNavesVM()
        }
    }

    fun obtenerTiposNavesVM(){
        viewModelScope.launch {
            val response: Response<MutableList<Tipo>> = NaveNetwork.retrofit.obtenerTiposNaves()
            _tipos.value = response.body()
        }
    }

}