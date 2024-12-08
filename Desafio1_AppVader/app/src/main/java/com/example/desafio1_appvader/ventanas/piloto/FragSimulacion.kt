package com.example.desafio1_appvader.ventanas.piloto

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.desafio1_appvader.R
import com.example.desafio1_appvader.api.MainViewModel
import com.example.desafio1_appvader.databinding.FragmentFragAsignarMisionesBinding
import com.example.desafio1_appvader.databinding.FragmentFragSimulacionBinding
import com.example.desafio1_appvader.modelo.mision.MisionMostrar
import com.example.desafio1_appvader.ventanas.admin.FragAsignarMisionesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FragSimulacion : Fragment() {
    private var _binding: FragmentFragSimulacionBinding? = null
    private val binding get() = _binding!!
    private val fragSimulacionViewModel: FragSimulacionViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    companion object {
        fun newInstance() = FragSimulacion()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragSimulacionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.tvSimulacion.movementMethod = ScrollingMovementMethod()
        binding.tvDatosMisionSimulacion.movementMethod = ScrollingMovementMethod()
        val idAsignacion = arguments?.getInt("idAsignacion")
        var nivel = ""
        var mision = MisionMostrar(0, "", 0, "", "", 0, 0)
        var datosMision = ""

        fragSimulacionViewModel.nivel.observe(viewLifecycleOwner){
            if (it != null) {
                binding.tvNivelUsuarioSimulacion.text = it
                nivel = it
                fragSimulacionViewModel.obtenerMisionAsignacionPorIdVM(idAsignacion!!)
            }

        }
        fragSimulacionViewModel.mision.observe(viewLifecycleOwner){
            if (it != null) {
                mision = it
                datosMision += " -Nombre: ${it.nombre}\n -Experiencia: ${it.exp}\n -Nave: ${it.naveAsig}"
                when (it.tipo) {
                    "Vuelo" -> fragSimulacionViewModel.obtenerVueloPorIdVM(it.id)
                    "Bombardeo" -> fragSimulacionViewModel.obtenerBombardeoPorIdVM(it.id)
                    "Combate" -> fragSimulacionViewModel.obtenerCazaPorIdVM(it.id)
                }
            }
        }
        fragSimulacionViewModel.vuelo.observe(viewLifecycleOwner){
            if (it != null){
                var carga = "Si"
                var pasajeros = "Si"
                if (it.carga == 0){
                    carga = "No"
                }
                if (it.pasajeros == 0){
                    pasajeros = "No"
                }
                datosMision += "\n -Tipo: Vuelo\n -Duracion: ${it.duracion}\n- Carga: ${carga}\n- Pasajeros: ${pasajeros}"
                binding.tvDatosMisionSimulacion.text = datosMision
                CoroutineScope(Dispatchers.Main).launch {
                    fragSimulacionViewModel.terminarSimulacionVM(simulacionVuelo(nivel, it.duracion))
                }
            }
        }
        fragSimulacionViewModel.bombardeo.observe(viewLifecycleOwner){
            if (it != null){
                var carga = "Si"
                var pasajeros = "Si"
                if (it.carga == 0){
                    carga = "No"
                }
                if (it.pasajeros == 0){
                    pasajeros = "No"
                }
                datosMision += "\n -Tipo: Bombardeo\n -Objetivos: ${it.objetivos}\n -Carga: ${carga}\n -Pasajeros: ${pasajeros}"
                binding.tvDatosMisionSimulacion.text = datosMision

                CoroutineScope(Dispatchers.Main).launch {
                    fragSimulacionViewModel.terminarSimulacionVM(simulacionObjetivos(it.objetivos,nivel,mision.tipo))
                }
            }
        }
        fragSimulacionViewModel.caza.observe(viewLifecycleOwner) {
            if (it != null) {
                datosMision += "\n -Tipo: Combate de caza\n -Objetivos: ${it.objetivos}"
                binding.tvDatosMisionSimulacion.text = datosMision

                CoroutineScope(Dispatchers.Main).launch {
                    fragSimulacionViewModel.terminarSimulacionVM(simulacionObjetivos(it.objetivos,nivel,mision.tipo))
                }
            }
        }
        fragSimulacionViewModel.resSimulacion.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it){
                    fragSimulacionViewModel.modificarExperienciaUsuarioVM(mainViewModel.usuarioLogeado.value!!.id, mision.exp)
                    fragSimulacionViewModel.actualizarEstadoVM(idAsignacion!!, 2)
                }else{
                    fragSimulacionViewModel.actualizarEstadoVM(idAsignacion!!, 3)
                }
                binding.btnVolverSimulacion.visibility = View.VISIBLE
            }
        }
        fragSimulacionViewModel.errorCode.observe(viewLifecycleOwner){error->
            if (error != null){
                when(error){
                    400 -> Toast.makeText(requireContext(), "Error al actualizar el estado de la mision", Toast.LENGTH_SHORT).show()
                    409 -> Toast.makeText(requireContext(), "Error al añadir la experiencia al piloto", Toast.LENGTH_SHORT).show()
                }
            }
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragSimulacionViewModel.obtenerNivelPorIdVM(mainViewModel.usuarioLogeado.value!!.id)

        binding.btnVolverSimulacion.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.nav_fragMisionesPendientes)
            (requireActivity() as AppCompatActivity).supportActionBar?.title = "MISIONES PENDIENTES"
        }
    }

    suspend fun simulacionVuelo(nivel: String, duracion: Int): Boolean{
        var min = 0
        var haGanado = false
        var posibilidades:Int

        binding.tvSimulacion.text = " *** Iniciando simulacion... ***"
        delay(2000)
        while (true){
            delay(1000)
            min += 1
            binding.tvSimulacion.append("\n - Min: "+min)
            bajarScrollBar()

            if (min%10 == 0) {
                binding.tvSimulacion.append("\n - Se acerca una tormenta solar")
                bajarScrollBar()
                delay(2000)
                posibilidades = (0..100).random()
                if ((posibilidades <= 10 && nivel == "Experto")||
                    (posibilidades <= 30 && nivel == "Intermedio")||
                    (posibilidades <= 50 && nivel == "Novato")) {
                    binding.tvSimulacion.append("\n - LA TORMENTA ES MAS DESTRUCTIVA DE LO QUE CREIAS")
                    binding.tvSimulacion.append("\n *** HAS MUERTO, MISION FALLIDA ***")
                    bajarScrollBar()
                    break
                } else {
                    binding.tvSimulacion.append("\n - Consigues superar la tormenta solar")
                    bajarScrollBar()
                }
            }


            if (min%20 == 0){
                posibilidades = (0..100).random()
                if (posibilidades <= 30){
                    binding.tvSimulacion.append("\n - Un equipo de rebeldes te a visto y se disponen a atacar")
                    bajarScrollBar()
                    delay(2000)
                    posibilidades = (0..100).random()
                    if ((posibilidades <= 20 && nivel == "Experto")||
                        (posibilidades <= 40 && nivel == "Intermedio")||
                        (posibilidades <= 60 && nivel == "Novato")){
                        binding.tvSimulacion.append("\n - NO HAS PODIDO ESQUIVAR TODOS LOS PROYECTILES ENEMIGOS")
                        binding.tvSimulacion.append("\n *** HAS MUERTO, MISION FALLIDA ***")
                        bajarScrollBar()
                        break
                    }else{
                        binding.tvSimulacion.append("\n - Consigues derrotar a los enemigos")
                        bajarScrollBar()
                    }

                }
            }
            binding.pbarProgresoSimulacion.progress = (min.toDouble()/duracion.toDouble()*100.0).toInt()
            if (min == duracion){
                binding.tvSimulacion.append("\n - TODAS LAS MANIOBRAS DE VUELO REALIZADAS")
                binding.tvSimulacion.append("\n *** MISION COMPLETADA CON EXITO ***")
                bajarScrollBar()
                haGanado = true
                break
            }
        }
        return haGanado
    }

    suspend fun simulacionObjetivos(objetivos: Int, nivel: String, tipo: String): Boolean{
        var min = 0
        var haGanado = false
        var posibilidades:Int
        var objetivosDestruidos = 0

        binding.tvSimulacion.text = " *** Iniciando simulacion... ***"
        delay(2000)
        while (true){
            delay(1000)
            min += 1
            binding.tvSimulacion.append("\n - Min: "+min)
            bajarScrollBar()

            if (min%5 == 0) {
                if (tipo == "Combate"){
                    binding.tvSimulacion.append("\n - Se acerca un caza enemigo")
                }else{
                    binding.tvSimulacion.append("\n - Ves una base rebelde")
                }
                bajarScrollBar()
                delay(2000)
                posibilidades = (0..100).random()
                if ((posibilidades <= 20 && nivel == "Experto") ||
                    (posibilidades <= 50 && nivel == "Intermedio") ||
                    (posibilidades <= 70 && nivel == "Novato")) {
                    binding.tvSimulacion.append("\n - LA HABILIDAD DE LOS ENEMIGOS TE SUPERA Y TE ACABAN DERRIBADO")
                    binding.tvSimulacion.append("\n *** HAS MUERTO, MISION FALLIDA ***")
                    bajarScrollBar()
                    break
                } else {
                    if (tipo == "Combate"){
                        binding.tvSimulacion.append("\n - Destruyes al caza enemigo")
                    }else{
                        binding.tvSimulacion.append("\n - Consigues bombardear la base")
                    }
                    bajarScrollBar()
                    objetivosDestruidos++
                }
            }

            binding.pbarProgresoSimulacion.progress = (objetivosDestruidos.toDouble()/objetivos.toDouble()*100.0).toInt()
            if (objetivosDestruidos == objetivos){
                binding.tvSimulacion.append("\n - TODOS LOS OBJETIVOS DESTRUIDOS")
                binding.tvSimulacion.append("\n *** MISION COMPLETADA CON EXITO ***")
                bajarScrollBar()
                haGanado = true
                break
            }
        }
        return haGanado
    }

    fun bajarScrollBar(){
        binding.tvSimulacion.post {
            val scrollAmount = binding.tvSimulacion.layout.getLineTop(binding.tvSimulacion.lineCount) - binding.tvSimulacion.height
            if (scrollAmount > 0) {
                binding.tvSimulacion.scrollTo(0, scrollAmount)
            } else {
                binding.tvSimulacion.scrollTo(0, 0)
            }
        }
    }
}
