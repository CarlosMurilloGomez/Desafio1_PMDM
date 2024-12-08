package com.example.desafio1_appvader.ventanas.piloto

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafio1_appvader.R
import com.example.desafio1_appvader.adaptadores.AdaptadorMisionesAdmin
import com.example.desafio1_appvader.adaptadores.AdaptadorMisionesPiloto
import com.example.desafio1_appvader.adaptadores.AdaptadorNaves
import com.example.desafio1_appvader.api.MainViewModel
import com.example.desafio1_appvader.databinding.FragmentFragMisionesPendientesBinding
import com.example.desafio1_appvader.databinding.FragmentFragPerfilBinding
import com.example.desafio1_appvader.modelo.Tipo
import com.example.desafio1_appvader.modelo.mision.Asignacion
import com.example.desafio1_appvader.modelo.mision.MisionMostrar
import com.example.desafio1_appvader.modelo.nave.NaveMostrar
import com.example.desafio1_appvader.ventanas.FragPerfilViewModel

class FragMisionesPendientes : Fragment() {
    private var _binding: FragmentFragMisionesPendientesBinding? = null
    private val binding get() = _binding!!
    private val fragMisionesPendientesViewModel: FragMisionesPendientesViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    var datosRepresentar : ArrayList<MisionMostrar> = ArrayList()
    lateinit var adaptador : AdaptadorMisionesPiloto
    companion object {
        fun newInstance() = FragMisionesPendientes()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragMisionesPendientesBinding.inflate(inflater, container, false)
        val root: View = binding.root


        fragMisionesPendientesViewModel.misiones.observe(viewLifecycleOwner) {
            datosRepresentar.clear()
            datosRepresentar.addAll(it)
            adaptador.notifyDataSetChanged()
            if (datosRepresentar.isEmpty()) {
                binding.lbNoHayMisionesAsignadas.visibility = View.VISIBLE
            } else {
                binding.lbNoHayMisionesAsignadas.visibility = View.GONE
            }
        }

        fragMisionesPendientesViewModel.vuelo.observe(viewLifecycleOwner){
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
                fragMisionesPendientesViewModel.restablecerVuelo()

            }
        }
        fragMisionesPendientesViewModel.bombardeo.observe(viewLifecycleOwner){
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
                fragMisionesPendientesViewModel.restablecerBombardeo()
            }
        }
        fragMisionesPendientesViewModel.caza.observe(viewLifecycleOwner){
            if (it != null){
                AlertDialog.Builder(context)
                    .setTitle("Combate de caza con id: ${it.idMision}")
                    .setMessage(" -Objetivos: ${it.objetivos}")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener(function = { dialog: DialogInterface, which: Int ->
                        dialog.dismiss()
                    }))
                    .show()
                fragMisionesPendientesViewModel.restablecerCaza()
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
        while (mainViewModel.usuarioLogeado.value == null) {
            Thread.sleep(100)
        }
        fragMisionesPendientesViewModel.obtenerMisionAsignacionesPorUsuarioVM(mainViewModel.usuarioLogeado.value!!.id)
    }
    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyViewMisionesAsig.layoutManager = linearLayoutManager
        adaptador = AdaptadorMisionesPiloto(datosRepresentar,requireContext(), fragMisionesPendientesViewModel)
        binding.recyViewMisionesAsig.adapter = adaptador
    }
}