package com.example.desafio1_appvader.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio1_appvader.modelo.Asignacion
import com.example.desafio1_appvader.modelo.Mision
import com.example.desafio1_appvader.modelo.MisionBombardeo
import com.example.desafio1_appvader.modelo.MisionCaza
import com.example.desafio1_appvader.modelo.MisionVuelo
import com.example.desafio1_appvader.modelo.Nave
import com.example.desafio1_appvader.modelo.Usuario
import com.example.desafio1_appvader.modelo.UsuarioLogIn
import com.example.desafio1_appvader.modelo.UsuarioPerfil
import kotlinx.coroutines.launch
import retrofit2.Response

class mainViewModel : ViewModel() {

    private val _cadena = MutableLiveData<String?>()
    val cadena: LiveData<String?> get() = _cadena

    private val _resOperacion = MutableLiveData<Boolean>()
    val resOperacion: LiveData<Boolean> get() = _resOperacion

    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    //USUARIO
    private val _usuario = MutableLiveData<Usuario?>()
    val usuario: LiveData<Usuario?> get() = _usuario

    private val _usuarios = MutableLiveData<List<Usuario>>()
    val usuarios: LiveData<List<Usuario>> get() = _usuarios

    fun registrarUsuarioVM(usuario: Usuario) {
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofit.registrarUsuario(usuario)
            _resOperacion.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun modificarPerfilUsuarioVM(perfil: UsuarioPerfil) {
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofit.modificarPerfilUsuario(perfil)
            _resOperacion.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun modificarExperienciaUsuarioVM(id: Int, experiencia: Int) {
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofit.modificarExperienciaUsuario(id, experiencia)
            _resOperacion.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun eliminarUsuarioVM(id: Int) {
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofit.eliminarUsuario(id)
            _resOperacion.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun loginVM(datosLogIn: UsuarioLogIn) {
        viewModelScope.launch {
            val response: Response<Usuario?> = UserNetwork.retrofit.login(datosLogIn)
            _usuario.value = response.body()
            _errorCode.value = response.code()
        }

    }

    fun obtenerUsuariosVM(){
        viewModelScope.launch {
            val response: Response<MutableList<Usuario>> = UserNetwork.retrofit.obtenerUsuarios()
            _usuarios.value = response.body()
        }
    }

    fun obtenerUsuarioPorIdVM(id: Int) {
        viewModelScope.launch {
            val response: Response<Usuario?> = UserNetwork.retrofit.obtenerUsuarioPorId(id)
            _usuario.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun obtenerPilotosVM() {
        viewModelScope.launch {
            val response: Response<MutableList<Usuario>> = UserNetwork.retrofit.obtenerPilotos()
            _usuarios.value = response.body()
        }
    }

    fun obtenerRankingVM() {
        viewModelScope.launch {
            val response: Response<MutableList<Usuario>> = UserNetwork.retrofit.obtenerRanking()
            _usuarios.value = response.body()
        }
    }

    fun obtenerRolPorIdVM(idUsuario: Int) {
        viewModelScope.launch {
            val response: Response<String?> = UserNetwork.retrofit.obtenerRolPorId(idUsuario)
            _cadena.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun obtenerNivelPorIdVM(idUsuario: Int) {
        viewModelScope.launch {
            val response: Response<String?> = UserNetwork.retrofit.obtenerNivelPorId(idUsuario)
            _cadena.value = response.body()
            _errorCode.value = response.code()
        }
    }

    //NAVE
    private val _nave = MutableLiveData<Nave?>()
    val nave: LiveData<Nave?> get() = _nave

    private val _naves = MutableLiveData<List<Nave>>()
    val naves: LiveData<List<Nave>> get() = _naves

    fun registrarNaveVM(nave: Nave) {
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofit.registrarNave(nave)
            _resOperacion.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun eliminarNaveVM(matricula: String) {
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofit.eliminarNave(matricula)
            _resOperacion.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun obtenerNavesVM(){
        viewModelScope.launch {
            val response: Response<MutableList<Nave>> = UserNetwork.retrofit.obtenerNaves()
            _naves.value = response.body()
        }
    }

    fun obtenerNavePorMatriculaVM(matricula: String) {
        viewModelScope.launch {
            val response: Response<Nave?> = UserNetwork.retrofit.obtenerNavePorMatricula(matricula)
            _nave.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun obtenerNavesPorTipoVM(tipo: String) {
        viewModelScope.launch {
            val response: Response<MutableList<Nave>> = UserNetwork.retrofit.obtenerNavesPorTipo(tipo)
            _naves.value = response.body()
        }
    }

    //MISION
    private val _mision = MutableLiveData<Mision?>()
    val mision: LiveData<Mision?> get() = _mision

    private val _misiones = MutableLiveData<List<Mision>>()
    val misiones: LiveData<List<Mision>> get() = _misiones

    private val _vuelo = MutableLiveData<MisionVuelo?>()
    val vuelo: LiveData<MisionVuelo?> get() = _vuelo

    private val _bombardeo = MutableLiveData<MisionBombardeo?>()
    val bombardeo: LiveData<MisionBombardeo?> get() = _bombardeo

    private val _caza = MutableLiveData<MisionCaza?>()
    val caza: LiveData<MisionCaza?> get() = _caza

    private val _asignacion = MutableLiveData<Asignacion?>()
    val asignacion: LiveData<Asignacion?> get() = _asignacion

    private val _asignaciones = MutableLiveData<List<Asignacion>>()
    val asignaciones: LiveData<List<Asignacion>> get() = _asignaciones

    fun registrarMisionVM(mision: Mision) {
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofit.registrarMision(mision)
            _resOperacion.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun registrarVueloVM(mision: MisionVuelo) {
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofit.registrarVuelo(mision)
            _resOperacion.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun registrarBombardeoVM(mision: MisionBombardeo) {
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofit.registrarBombardeo(mision)
            _resOperacion.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun registrarCazaVM(mision: MisionCaza) {
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofit.registrarCaza(mision)
            _resOperacion.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun eliminarMisionVM(id: Int) {
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofit.eliminarMision(id)
            _resOperacion.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun obtenerMisionesVM() {
        viewModelScope.launch {
            val response: Response<MutableList<Mision>> = UserNetwork.retrofit.obtenerMisiones()
            _misiones.value = response.body()
        }
    }

    fun obtenerMisionPorIdVM(id: Int) {
        viewModelScope.launch {
            val response: Response<Mision?> = UserNetwork.retrofit.obtenerMisionPorId(id)
            _mision.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun obtenerVueloPorIdVM(idMision: Int) {
        viewModelScope.launch {
            val response: Response<MisionVuelo?> = UserNetwork.retrofit.obtenerVueloPorId(idMision)
            _vuelo.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun obtenerBombardeoPorIdVM(idMision: Int) {
        viewModelScope.launch {
            val response: Response<MisionBombardeo?> = UserNetwork.retrofit.obtenerBombardeoPorId(idMision)
            _bombardeo.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun obtenerCazaPorIdVM(idMision: Int) {
        viewModelScope.launch {
            val response: Response<MisionCaza?> = UserNetwork.retrofit.obtenerCazaPorId(idMision)
            _caza.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun asignarMisionVM(asignacion: Asignacion) {
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofit.asignarMision(asignacion)
            _resOperacion.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun obtenerAsignacionesPorUsuarioVM(idUsuario: Int) {
        viewModelScope.launch {
            val response: Response<MutableList<Asignacion>> = UserNetwork.retrofit.obtenerAsignacionesPorUsuario(idUsuario)
            _asignaciones.value = response.body()
        }
    }

    fun obtenerAsignacionPorIdVM(id: Int) {
        viewModelScope.launch {
            val response: Response<Asignacion?> = UserNetwork.retrofit.obtenerAsignacionPorId(id)
            _asignacion.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun actualizarEstadoVM(id: Int, estado: Int) {
        viewModelScope.launch {
            val response: Response<Boolean> = UserNetwork.retrofit.actualizarEstado(id, estado)
            _resOperacion.value = response.body()
            _errorCode.value = response.code()
        }
    }

}