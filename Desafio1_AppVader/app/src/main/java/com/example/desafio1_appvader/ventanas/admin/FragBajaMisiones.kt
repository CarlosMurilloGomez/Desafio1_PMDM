package com.example.desafio1_appvader.ventanas.admin

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafio1_appvader.R
import com.example.desafio1_appvader.adaptadores.AdaptadorMisionesAdmin
import com.example.desafio1_appvader.adaptadores.AdaptadorNaves
import com.example.desafio1_appvader.api.MainViewModel
import com.example.desafio1_appvader.databinding.FragmentFragBajaMisionesBinding
import com.example.desafio1_appvader.databinding.FragmentFragBajaNaveBinding
import com.example.desafio1_appvader.modelo.Tipo
import com.example.desafio1_appvader.modelo.mision.Mision
import com.example.desafio1_appvader.modelo.mision.MisionMostrar
import com.example.desafio1_appvader.modelo.nave.NaveMostrar

class FragBajaMisiones : Fragment() {
    private var _binding: FragmentFragBajaMisionesBinding? = null
    private val binding get() = _binding!!

    private val fragBajaMisionesViewModel: FragBajaMisionesViewModel by viewModels()
    var datosRepresentar : ArrayList<MisionMostrar> = ArrayList()
    lateinit var adaptador : AdaptadorMisionesAdmin
    companion object {
        fun newInstance() = FragBajaMisiones()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragBajaMisionesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var tipos = ArrayList<Tipo>()
        fragBajaMisionesViewModel.tipos.observe(viewLifecycleOwner){
            tipos = it as ArrayList<Tipo>
            fragBajaMisionesViewModel.obtenerMisionesVM()
        }
        fragBajaMisionesViewModel.errorCode.observe(viewLifecycleOwner){error ->
            if (error!=null) {
                when (error) {
                    404 -> Toast.makeText(requireContext(), "No existe la mision", Toast.LENGTH_SHORT).show()
                    409 -> Toast.makeText(requireContext(), "Error al eliminar", Toast.LENGTH_SHORT).show()
                    202 -> Toast.makeText(requireContext(),"Mision Eliminada", Toast.LENGTH_SHORT).show()
                }
                fragBajaMisionesViewModel.restablecerError()
            }
        }
        fragBajaMisionesViewModel.misiones.observe(viewLifecycleOwner) {
            datosRepresentar.clear()
            for (mision in it) {
                var misionMostrar = MisionMostrar(mision.id, mision.nombre, mision.exp, mision.naveAsig, "", 0, 0)
                for (tipo in tipos){
                    if (tipo.id == mision.tipo){
                        misionMostrar.tipo = tipo.tipo
                    }
                }

                datosRepresentar.add(misionMostrar)
            }
            adaptador.notifyDataSetChanged()
            if (datosRepresentar.isEmpty()){
                binding.lbNoHayMisionesBajaMi.visibility = View.VISIBLE
            }
            else {
                binding.lbNoHayMisionesBajaMi.visibility = View.GONE
            }
        }

        fragBajaMisionesViewModel.vuelo.observe(viewLifecycleOwner){
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
                fragBajaMisionesViewModel.restablecerVuelo()
            }
        }
        fragBajaMisionesViewModel.bombardeo.observe(viewLifecycleOwner){
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
                fragBajaMisionesViewModel.restablecerBombardeo()
            }
        }
        fragBajaMisionesViewModel.caza.observe(viewLifecycleOwner){
            if (it != null){
                AlertDialog.Builder(context)
                    .setTitle("Combate de caza con id: ${it.idMision}")
                    .setMessage(" -Objetivos: ${it.objetivos}")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener(function = { dialog: DialogInterface, which: Int ->
                        dialog.dismiss()
                    }))
                    .show()
                fragBajaMisionesViewModel.restablecerCaza()
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
        fragBajaMisionesViewModel.obtenerTiposMisionVM()

        binding.btnRegistrarMision.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.nav_fragAltaMisiones)
            (requireActivity() as AppCompatActivity).supportActionBar?.title = "REGISTRAR MISION"
        }

    }
    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyViewBajaMisiones.layoutManager = linearLayoutManager
        adaptador = AdaptadorMisionesAdmin(datosRepresentar,requireContext(), fragBajaMisionesViewModel)
        binding.recyViewBajaMisiones.adapter = adaptador
    }
}