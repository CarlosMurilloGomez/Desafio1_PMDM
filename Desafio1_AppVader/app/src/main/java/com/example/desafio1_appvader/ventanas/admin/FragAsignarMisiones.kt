package com.example.desafio1_appvader.ventanas.admin

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.desafio1_appvader.R
import com.example.desafio1_appvader.databinding.FragmentFragAltaMisionesBinding
import com.example.desafio1_appvader.databinding.FragmentFragAsignarMisionesBinding
import com.example.desafio1_appvader.modelo.Tipo
import com.example.desafio1_appvader.modelo.mision.Asignacion
import com.example.desafio1_appvader.modelo.usuario.Usuario

class FragAsignarMisiones : Fragment() {
    private var _binding: FragmentFragAsignarMisionesBinding? = null
    private val binding get() = _binding!!
    private val fragAsignarMisionesViewModel: FragAsignarMisionesViewModel by viewModels()

    companion object {
        fun newInstance() = FragAsignarMisiones()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragAsignarMisionesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val idMision = arguments?.getInt("idMision")
        var datosMision = ""
        var tipos = ArrayList<Tipo>()
        fragAsignarMisionesViewModel.obtenerTiposMisionVM()
        fragAsignarMisionesViewModel.tipos.observe(viewLifecycleOwner){
            tipos = it as ArrayList<Tipo>
            fragAsignarMisionesViewModel.obtenerMisionPorIdVM(idMision!!)
        }

        fragAsignarMisionesViewModel.mision.observe(viewLifecycleOwner){
            if (it != null) {
                datosMision += " -Nombre: ${it.nombre}\n -Experiencia: ${it.exp}\n -Nave: ${it.naveAsig}"
                when (it.tipo) {
                    1 -> fragAsignarMisionesViewModel.obtenerVueloPorIdVM(it.id)
                    2 -> fragAsignarMisionesViewModel.obtenerBombardeoPorIdVM(it.id)
                    3 -> fragAsignarMisionesViewModel.obtenerCazaPorIdVM(it.id)
                }
            }
        }
        fragAsignarMisionesViewModel.vuelo.observe(viewLifecycleOwner){
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
                binding.tvDatosMisionAsigMi.text = datosMision
            }
        }
        fragAsignarMisionesViewModel.bombardeo.observe(viewLifecycleOwner){
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
                binding.tvDatosMisionAsigMi.text = datosMision
            }
        }
        fragAsignarMisionesViewModel.caza.observe(viewLifecycleOwner) {
            if (it != null) {
                datosMision += "\n -Tipo: Combate de caza\n -Objetivos: ${it.objetivos}"
                binding.tvDatosMisionAsigMi.text = datosMision
            }
        }

        var pilotos = ArrayList<Tipo>()
        fragAsignarMisionesViewModel.obtenerPilotosVM()
        fragAsignarMisionesViewModel.pilotos.observe(viewLifecycleOwner){
            for (piloto in it){
                pilotos.add(Tipo(piloto.id, piloto.nombre))
            }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, pilotos)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spElegirPilotoAsigMi.adapter = adapter
        }
        binding.spElegirPilotoAsigMi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position)
                fragAsignarMisionesViewModel.obtenerPilotoPorIdVM((selectedItem as Tipo).id)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                //Acción cuando no se selecciona nada
            }
        }
        fragAsignarMisionesViewModel.piloto.observe(viewLifecycleOwner){
            if (it != null){
                binding.tvDatosPilotoAsigMi.text = "\n -Nombre: ${it.nombre}\n -Edad: ${it.edad}\n -Experiencia: ${it.experiencia}"
                Glide.with(requireContext()).load(it.foto).into(binding.ivFotoPilotoAsigMi)
            }

        }
        fragAsignarMisionesViewModel.resAsignacion.observe(viewLifecycleOwner){
            if (it!=null && it==true) {
                Navigation.findNavController(requireView()).navigate(R.id.nav_fragBajaMisiones)
                (requireActivity() as AppCompatActivity).supportActionBar?.title = "LISTA DE MISIONES"
                fragAsignarMisionesViewModel.restablecerResAsignacion()
            }
        }

        fragAsignarMisionesViewModel.errorCode.observe(viewLifecycleOwner){error->
            if (error != null){
                when(error){
                    201 -> Toast.makeText(requireContext(), "Mision asignada correctamente", Toast.LENGTH_SHORT).show()
                    409 -> Toast.makeText(requireContext(), "Error al asignar", Toast.LENGTH_SHORT).show()
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
        val idMision = arguments?.getInt("idMision")

        binding.btnAsignarAsigMi.setOnClickListener {
            if (binding.spElegirPilotoAsigMi.selectedItem == null){
                Toast.makeText(requireContext(), "Selecciona un piloto", Toast.LENGTH_SHORT).show()
            }else {
                fragAsignarMisionesViewModel.asignarMisionVM(Asignacion(0, idMision!!, fragAsignarMisionesViewModel.piloto.value!!.id, 0))
            }
        }
        binding.btnCancelarAsigMi.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.nav_fragBajaMisiones)
            (requireActivity() as AppCompatActivity).supportActionBar?.title = "LISTA DE MISIONES"
        }
    }
}