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
                when (it){
                    "Novato" -> binding.tvNivelUsuarioSimulacion.text = resources.getString(R.string.novato)
                    "Intermedio" -> binding.tvNivelUsuarioSimulacion.text = resources.getString(R.string.intermedio)
                    "Experto" -> binding.tvNivelUsuarioSimulacion.text = resources.getString(R.string.experto)
                }
                nivel = it
                fragSimulacionViewModel.obtenerMisionAsignacionPorIdVM(idAsignacion!!)
            }

        }
        fragSimulacionViewModel.mision.observe(viewLifecycleOwner){
            if (it != null) {
                mision = it
                datosMision += " -"+resources.getString(R.string.nombre)+": ${it.nombre}\n -"+resources.getString(R.string.exp)+": ${it.exp}\n -"+resources.getString(R.string.nave)+": ${it.naveAsig}"
                when (it.tipo) {
                    "Vuelo" -> fragSimulacionViewModel.obtenerVueloPorIdVM(it.id)
                    "Bombardeo" -> fragSimulacionViewModel.obtenerBombardeoPorIdVM(it.id)
                    "Combate" -> fragSimulacionViewModel.obtenerCazaPorIdVM(it.id)
                }
            }
        }
        fragSimulacionViewModel.vuelo.observe(viewLifecycleOwner){
            if (it != null){
                var carga = resources.getString(R.string.si)
                var pasajeros = resources.getString(R.string.si)
                if (it.carga == 0){
                    carga = resources.getString(R.string.no)
                }
                if (it.pasajeros == 0){
                    pasajeros = resources.getString(R.string.no)
                }
                datosMision += "\n -"+resources.getString(R.string.tipo)+": "+resources.getString(R.string.tipoVuelo)+"\n -"+resources.getString(R.string.duracion)+": ${it.duracion}\n- "+resources.getString(R.string.carga)+": ${carga}\n- "+resources.getString(R.string.pasajeros)+": ${pasajeros}"
                binding.tvDatosMisionSimulacion.text = datosMision
                CoroutineScope(Dispatchers.Main).launch {
                    fragSimulacionViewModel.terminarSimulacionVM(simulacionVuelo(nivel, it.duracion))
                }
            }
        }
        fragSimulacionViewModel.bombardeo.observe(viewLifecycleOwner){
            if (it != null){
                var carga = resources.getString(R.string.si)
                var pasajeros = resources.getString(R.string.si)
                if (it.carga == 0){
                    carga = resources.getString(R.string.no)
                }
                if (it.pasajeros == 0){
                    pasajeros = resources.getString(R.string.no)
                }
                datosMision += "\n -"+resources.getString(R.string.tipo)+": "+resources.getString(R.string.tipoBombardeo)+"\n -"+resources.getString(R.string.objetivos)+": ${it.objetivos}\n -"+resources.getString(R.string.carga)+": ${carga}\n -"+resources.getString(R.string.pasajeros)+": ${pasajeros}"
                binding.tvDatosMisionSimulacion.text = datosMision

                CoroutineScope(Dispatchers.Main).launch {
                    fragSimulacionViewModel.terminarSimulacionVM(simulacionObjetivos(it.objetivos,nivel,mision.tipo))
                }
            }
        }
        fragSimulacionViewModel.caza.observe(viewLifecycleOwner) {
            if (it != null) {
                datosMision += "\n -"+resources.getString(R.string.tipo)+": "+resources.getString(R.string.tipoCombate)+"\n -"+resources.getString(R.string.objetivos)+": ${it.objetivos}"
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
                    400 -> Toast.makeText(requireContext(), getString(R.string.errActualizarEstado), Toast.LENGTH_SHORT).show()
                    409 -> Toast.makeText(requireContext(), getString(R.string.errAddExperiencia), Toast.LENGTH_SHORT).show()
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
        }
    }

    suspend fun simulacionVuelo(nivel: String, duracion: Int): Boolean{
        var min = 0
        var haGanado = false
        var posibilidades:Int

        binding.tvSimulacion.text = " *** "+resources.getString(R.string.inicioSim)+"... ***"
        delay(2000)
        while (true){
            delay(1000)
            min += 1
            binding.tvSimulacion.append("\n - Min: "+min)
            bajarScrollBar()

            if (min%10 == 0) {
                binding.tvSimulacion.append("\n - "+resources.getString(R.string.tormentaSolar))
                bajarScrollBar()
                delay(2000)
                posibilidades = (0..100).random()
                if ((posibilidades <= 10 && nivel == "Experto")||
                    (posibilidades <= 30 && nivel == "Intermedio")||
                    (posibilidades <= 50 && nivel == "Novato")) {
                    binding.tvSimulacion.append("\n - "+resources.getString(R.string.tormentaSolarMal))
                    binding.tvSimulacion.append("\n *** "+resources.getString(R.string.hasMuerto)+" ***")
                    bajarScrollBar()
                    break
                } else {
                    binding.tvSimulacion.append("\n - "+resources.getString(R.string.tormentaSolarBien))
                    bajarScrollBar()
                }
            }


            if (min%20 == 0){
                posibilidades = (0..100).random()
                if (posibilidades <= 30){
                    binding.tvSimulacion.append("\n - "+resources.getString(R.string.ataqueVuelo))
                    bajarScrollBar()
                    delay(2000)
                    posibilidades = (0..100).random()
                    if ((posibilidades <= 20 && nivel == "Experto")||
                        (posibilidades <= 40 && nivel == "Intermedio")||
                        (posibilidades <= 60 && nivel == "Novato")){
                        binding.tvSimulacion.append("\n - "+resources.getString(R.string.ataqueVueloMal))
                        binding.tvSimulacion.append("\n *** "+resources.getString(R.string.hasMuerto)+" ***")
                        bajarScrollBar()
                        break
                    }else{
                        binding.tvSimulacion.append("\n - "+resources.getString(R.string.ataqueVueloBien))
                        bajarScrollBar()
                    }

                }
            }
            binding.pbarProgresoSimulacion.progress = (min.toDouble()/duracion.toDouble()*100.0).toInt()
            if (min == duracion){
                binding.tvSimulacion.append("\n - "+resources.getString(R.string.vueloCompletado))
                binding.tvSimulacion.append("\n *** "+resources.getString(R.string.misionCompletada)+" ***")
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

        binding.tvSimulacion.text = " *** "+resources.getString(R.string.inicioSim)+"... ***"
        delay(2000)
        while (true){
            delay(1000)
            min += 1
            binding.tvSimulacion.append("\n - Min: "+min)
            bajarScrollBar()

            if (min%5 == 0) {
                if (tipo == "Combate"){
                    binding.tvSimulacion.append("\n - "+resources.getString(R.string.ataqueCombate))
                }else{
                    binding.tvSimulacion.append("\n - "+resources.getString(R.string.ataqueBombardeo))
                }
                bajarScrollBar()
                delay(2000)
                posibilidades = (0..100).random()
                if ((posibilidades <= 20 && nivel == "Experto") ||
                    (posibilidades <= 50 && nivel == "Intermedio") ||
                    (posibilidades <= 70 && nivel == "Novato")) {
                    binding.tvSimulacion.append("\n - "+resources.getString(R.string.ataqueObjetivosMal))
                    binding.tvSimulacion.append("\n *** "+resources.getString(R.string.hasMuerto)+" ***")
                    bajarScrollBar()
                    break
                } else {
                    if (tipo == "Combate"){
                        binding.tvSimulacion.append("\n - "+resources.getString(R.string.ataqueCombateBien))
                    }else{
                        binding.tvSimulacion.append("\n - "+resources.getString(R.string.ataqueBombardeoBien))
                    }
                    bajarScrollBar()
                    objetivosDestruidos++
                }
            }

            binding.pbarProgresoSimulacion.progress = (objetivosDestruidos.toDouble()/objetivos.toDouble()*100.0).toInt()
            if (objetivosDestruidos == objetivos){
                binding.tvSimulacion.append("\n - "+resources.getString(R.string.objetivosCompletado))
                binding.tvSimulacion.append("\n *** "+resources.getString(R.string.misionCompletada)+" ***")
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
