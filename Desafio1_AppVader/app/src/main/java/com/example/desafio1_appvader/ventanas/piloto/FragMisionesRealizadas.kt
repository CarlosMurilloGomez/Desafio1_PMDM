package com.example.desafio1_appvader.ventanas.piloto

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafio1_appvader.R
import com.example.desafio1_appvader.adaptadores.AdaptadorMisionesPiloto
import com.example.desafio1_appvader.api.MainViewModel
import com.example.desafio1_appvader.databinding.FragmentFragMisionesPendientesBinding
import com.example.desafio1_appvader.databinding.FragmentFragMisionesRealizadasBinding
import com.example.desafio1_appvader.modelo.mision.MisionMostrar

class FragMisionesRealizadas : Fragment() {
    private var _binding: FragmentFragMisionesRealizadasBinding? = null
    private val binding get() = _binding!!
    private val fragMisionesRealizadasViewModel: FragMisionesPendientesViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    var datosRepresentar : ArrayList<MisionMostrar> = ArrayList()
    lateinit var adaptador : AdaptadorMisionesPiloto
    companion object {
        fun newInstance() = FragMisionesRealizadas()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragMisionesRealizadasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        fragMisionesRealizadasViewModel.misiones.observe(viewLifecycleOwner) {
            datosRepresentar.clear()
            datosRepresentar.addAll(it)
            adaptador.notifyDataSetChanged()
            if (datosRepresentar.isEmpty()) {
                binding.lbNoHayMisionesRealizadas.visibility = View.VISIBLE
            } else {
                binding.lbNoHayMisionesRealizadas.visibility = View.GONE
            }
        }

        fragMisionesRealizadasViewModel.vuelo.observe(viewLifecycleOwner){
            if (it != null){
                var carga = "Si"
                var pasajeros = "Si"
                if (it.carga == 0){
                    carga = "No"
                }
                if (it.pasajeros == 0){
                    pasajeros = "No"
                }
                AlertDialog.Builder(context)
                    .setTitle("Vuelo con id: ${it.idMision}")
                    .setMessage(" -Carga: $carga\n\n -Pasajeros: $pasajeros\n\n -Duracion: ${it.duracion}min")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener(function = { dialog: DialogInterface, which: Int ->
                        dialog.dismiss()
                    }))
                    .show()
                fragMisionesRealizadasViewModel.restablecerVuelo()
            }
        }
        fragMisionesRealizadasViewModel.bombardeo.observe(viewLifecycleOwner){
            if (it != null){
                var carga = "Si"
                var pasajeros = "Si"
                if (it.carga == 0){
                    carga = "No"
                }
                if (it.pasajeros == 0){
                    pasajeros = "No"
                }
                AlertDialog.Builder(context)
                    .setTitle("Bombardeo con id: ${it.idMision}")
                    .setMessage(" -Carga: $carga\n\n -Pasajeros: $pasajeros\n\n -Objetivos: ${it.objetivos}")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener(function = { dialog: DialogInterface, which: Int ->
                        dialog.dismiss()
                    }))
                    .show()
                fragMisionesRealizadasViewModel.restablecerBombardeo()
            }
        }
        fragMisionesRealizadasViewModel.caza.observe(viewLifecycleOwner){
            if (it != null){
                AlertDialog.Builder(context)
                    .setTitle("Combate de caza con id: ${it.idMision}")
                    .setMessage(" -Objetivos: ${it.objetivos}")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener(function = { dialog: DialogInterface, which: Int ->
                        dialog.dismiss()
                    }))
                    .show()
                fragMisionesRealizadasViewModel.restablecerCaza()
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

        setupRecyclerView()
        fragMisionesRealizadasViewModel.obtenerMisionAsignacionesRealizadasPorUsuarioVM(mainViewModel.usuarioLogeado.value!!.id)
    }
    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyViewMisionesRealizadas.layoutManager = linearLayoutManager
        adaptador = AdaptadorMisionesPiloto(datosRepresentar,requireContext(), fragMisionesRealizadasViewModel)
        binding.recyViewMisionesRealizadas.adapter = adaptador
    }
}