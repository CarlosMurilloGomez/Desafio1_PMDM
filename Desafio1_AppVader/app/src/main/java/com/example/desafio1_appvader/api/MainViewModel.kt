package com.example.desafio1_appvader.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.Cloudinary
import com.example.desafio1_appvader.modelo.Cadena
import com.example.desafio1_appvader.modelo.Tipo
import com.example.desafio1_appvader.modelo.mision.Asignacion
import com.example.desafio1_appvader.modelo.mision.Mision
import com.example.desafio1_appvader.modelo.mision.MisionBombardeo
import com.example.desafio1_appvader.modelo.mision.MisionCaza
import com.example.desafio1_appvader.modelo.mision.MisionMostrar
import com.example.desafio1_appvader.modelo.mision.MisionVuelo
import com.example.desafio1_appvader.modelo.nave.Nave
import com.example.desafio1_appvader.modelo.nave.TipoCargaPasajeros
import com.example.desafio1_appvader.modelo.usuario.Usuario
import com.example.desafio1_appvader.modelo.usuario.UsuarioLogIn
import com.example.desafio1_appvader.parametros.Parametros
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.File

class MainViewModel : ViewModel() {

    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    fun restablecerError(){
        _errorCode.value = null
    }

    private val _resOperacion = MutableLiveData<Boolean>()
    val resOperacion: LiveData<Boolean> get() = _resOperacion

    fun restablecerResOperacion(){
        _resOperacion.value = false
    }

    
    private val _tipos = MutableLiveData<List<Tipo>>()
    val tipos: LiveData<List<Tipo>> get() = _tipos


    private val _usuarioLogeado = MutableLiveData<Usuario?>()
    val usuarioLogeado: LiveData<Usuario?> get() = _usuarioLogeado

    fun iniciarSesionVM(id: Int){
        viewModelScope.launch {
            val response: Response<Usuario?> = UsuarioNetwork.retrofit.obtenerUsuarioPorId(id)
            _usuarioLogeado.value = response.body()
        }
    }
    fun cerrarSesionVM(){
        _usuarioLogeado.value = null
    }

    private val _urlfoto = MutableLiveData<String?>()
    val urlfoto: LiveData<String?> get() = _urlfoto

    fun restablecerUrlFoto(){
        _urlfoto.value = null
    }
    fun subirImgen(file: File){
        viewModelScope.launch {
            val config = mapOf(
                "cloud_name" to Parametros.cloud_name,
                "api_key" to Parametros.api_key,
                "api_secret" to Parametros.api_secret
            )
            val cloudinary = Cloudinary(config)
            val options = mapOf("folder" to "Desafio1")
            val uploadResult = withContext(Dispatchers.IO) {
                cloudinary.uploader().upload(file, options)
            }
            _urlfoto.value = uploadResult["url"].toString()
        }

    }

    //USUARIO
    private val _usuario = MutableLiveData<Usuario?>()
    val usuario: LiveData<Usuario?> get() = _usuario

    fun restablecerUsuario(){
        _usuario.value = null
    }

    private val _usuarios = MutableLiveData<List<Usuario>>()
    val usuarios: LiveData<List<Usuario>> get() = _usuarios





    fun modificarExperienciaUsuarioVM(id: Int, experiencia: Int) {
        viewModelScope.launch {
            val response: Response<Boolean> = UsuarioNetwork.retrofit.modificarExperienciaUsuario(id, experiencia)
            _resOperacion.value = response.body()
            _errorCode.value = response.code()
        }
    }



    fun loginVM(datosLogIn: UsuarioLogIn) {
        viewModelScope.launch {
            val response: Response<Usuario?> = UsuarioNetwork.retrofit.login(datosLogIn)
            _usuario.value = response.body()
            _usuarioLogeado.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun obtenerUsuariosVM(){
        viewModelScope.launch {
            val response: Response<MutableList<Usuario>> = UsuarioNetwork.retrofit.obtenerUsuarios()
            _usuarios.value = response.body()
        }
    }

    fun obtenerUsuarioPorIdVM(id: Int) {
        viewModelScope.launch {
            val response: Response<Usuario?> = UsuarioNetwork.retrofit.obtenerUsuarioPorId(id)
            _usuario.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun obtenerPilotosVM() {
        viewModelScope.launch {
            val response: Response<MutableList<Usuario>> = UsuarioNetwork.retrofit.obtenerPilotos()
            _usuarios.value = response.body()
        }
    }





    //NAVE
    private val _nave = MutableLiveData<Nave?>()
    val nave: LiveData<Nave?> get() = _nave

    private val _naves = MutableLiveData<List<Nave>>()
    val naves: LiveData<List<Nave>> get() = _naves




    fun obtenerNavesVM(){
        viewModelScope.launch {
            val response: Response<MutableList<Nave>> = NaveNetwork.retrofit.obtenerNaves()
            _naves.value = response.body()
        }
    }

    fun obtenerNavePorMatriculaVM(matricula: String) {
        viewModelScope.launch {
            val response: Response<Nave?> = NaveNetwork.retrofit.obtenerNavePorMatricula(matricula)
            _nave.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun obtenerNavesPorTipoCargaPasajerosVM(datos: TipoCargaPasajeros) {
        viewModelScope.launch {
            val response: Response<MutableList<Nave>> = NaveNetwork.retrofit.obtenerNavesPorTipoCargaPasajeros(datos)
            _naves.value = response.body()
        }
    }

    fun obtenerTiposNavesVM(){
        viewModelScope.launch {
            val response: Response<MutableList<Tipo>> = NaveNetwork.retrofit.obtenerTiposNaves()
            _tipos.value = response.body()
        }
    }



    //MISION
    private val _mision = MutableLiveData<Mision?>()
    val mision: LiveData<Mision?> get() = _mision

    private val _misionAsignacion = MutableLiveData<MisionMostrar?>()
    val misionAsignacion: LiveData<MisionMostrar?> get() = _misionAsignacion


    private val _idMisionInsertada = MutableLiveData<Int?>()
    val idMisionInsertada: LiveData<Int?> get() = _idMisionInsertada

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
            val response: Response<Int> = MisionNetwork.retrofit.registrarMision(mision)
            _idMisionInsertada.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun registrarVueloVM(mision: MisionVuelo) {
        viewModelScope.launch {
            val response: Response<Boolean> = MisionNetwork.retrofit.registrarVuelo(mision)
            _resOperacion.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun registrarBombardeoVM(mision: MisionBombardeo) {
        viewModelScope.launch {
            val response: Response<Boolean> = MisionNetwork.retrofit.registrarBombardeo(mision)
            _resOperacion.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun registrarCazaVM(mision: MisionCaza) {
        viewModelScope.launch {
            val response: Response<Boolean> = MisionNetwork.retrofit.registrarCaza(mision)
            _resOperacion.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun eliminarMisionVM(id: Int) {
        viewModelScope.launch {
            val response: Response<Boolean> = MisionNetwork.retrofit.eliminarMision(id)
            _resOperacion.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun obtenerMisionesVM() {
        viewModelScope.launch {
            val response: Response<MutableList<Mision>> = MisionNetwork.retrofit.obtenerMisiones()
            _misiones.value = response.body()
        }
    }

    fun obtenerMisionPorIdVM(id: Int) {
        viewModelScope.launch {
            val response: Response<Mision?> = MisionNetwork.retrofit.obtenerMisionPorId(id)
            _mision.value = response.body()
            _errorCode.value = response.code()
        }
    }
    fun obtenerTiposMisionVM() {
        viewModelScope.launch {
            val response: Response<MutableList<Tipo>> = MisionNetwork.retrofit.obtenerTiposMision()
            _tipos.value = response.body()
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

    fun asignarMisionVM(asignacion: Asignacion) {
        viewModelScope.launch {
            val response: Response<Boolean> = MisionNetwork.retrofit.asignarMision(asignacion)
            _resOperacion.value = response.body()
            _errorCode.value = response.code()
        }
    }

    fun obtenerAsignacionesPorUsuarioVM(idUsuario: Int) {
        viewModelScope.launch {
            val response: Response<MutableList<Asignacion>> = MisionNetwork.retrofit.obtenerAsignacionesPorUsuario(idUsuario)
            _asignaciones.value = response.body()
        }
    }

    fun obtenerMisionAsignacionPorIdVM(id: Int) {
        viewModelScope.launch {
            val response: Response<MisionMostrar?> = MisionNetwork.retrofit.obtenerMisionAsignacionPorId(id)
            _misionAsignacion.value = response.body()
        }
    }

    fun actualizarEstadoVM(id: Int, estado: Int) {
        viewModelScope.launch {
            val response: Response<Boolean> = MisionNetwork.retrofit.actualizarEstado(id, estado)
            _resOperacion.value = response.body()
            _errorCode.value = response.code()
        }
    }



}