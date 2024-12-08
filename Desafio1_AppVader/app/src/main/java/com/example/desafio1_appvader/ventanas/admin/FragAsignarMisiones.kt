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
        fragAsignarMisionesViewModel.tipos.observe(viewLifecycleOwner){
            for (tipo in it){
                when (tipo.id) {
                    1 -> tipos.add(Tipo(1, getString(R.string.tipoVuelo)))
                    2 -> tipos.add(Tipo(2, getString(R.string.tipoBombardeo)))
                    3 -> tipos.add(Tipo(3, getString(R.string.tipoCombate)))
                }
            }
            fragAsignarMisionesViewModel.obtenerMisionPorIdVM(idMision!!)
        }

        fragAsignarMisionesViewModel.mision.observe(viewLifecycleOwner){
            if (it != null) {
                datosMision += " -"+getString(R.string.nombre)+": ${it.nombre}\n -"+getString(R.string.exp)+": ${it.exp}\n -"+getString(R.string.nave)+": ${it.naveAsig}"
                when (it.tipo) {
                    1 -> fragAsignarMisionesViewModel.obtenerVueloPorIdVM(it.id)
                    2 -> fragAsignarMisionesViewModel.obtenerBombardeoPorIdVM(it.id)
                    3 -> fragAsignarMisionesViewModel.obtenerCazaPorIdVM(it.id)
                }
            }
        }
        fragAsignarMisionesViewModel.vuelo.observe(viewLifecycleOwner){
            if (it != null){
                var carga = getString(R.string.si)
                var pasajeros = getString(R.string.si)
                if (it.carga == 0){
                    carga = getString(R.string.no)
                }
                if (it.pasajeros == 0){
                    pasajeros = getString(R.string.no)
                }
                datosMision += "\n -"+getString(R.string.tipo)+": "+getString(R.string.tipoVuelo)+"\n -"+getString(R.string.duracion)+": ${it.duracion}\n- "+getString(R.string.carga)+": ${carga}\n- "+getString(R.string.pasajeros)+": ${pasajeros}"
                binding.tvDatosMisionAsigMi.text = datosMision
            }
        }
        fragAsignarMisionesViewModel.bombardeo.observe(viewLifecycleOwner){
            if (it != null){
                var carga = getString(R.string.si)
                var pasajeros = getString(R.string.si)
                if (it.carga == 0){
                    carga = getString(R.string.no)
                }
                if (it.pasajeros == 0){
                    pasajeros = getString(R.string.no)
                }
                datosMision += "\n -"+getString(R.string.tipo)+": "+getString(R.string.tipoBombardeo)+"\n -"+getString(R.string.objetivos)+": ${it.objetivos}\n -"+getString(R.string.carga)+": ${carga}\n -"+getString(R.string.pasajeros)+": ${pasajeros}"
                binding.tvDatosMisionAsigMi.text = datosMision
            }
        }
        fragAsignarMisionesViewModel.caza.observe(viewLifecycleOwner) {
            if (it != null) {
                datosMision += "\n -"+getString(R.string.tipo)+": "+getString(R.string.tipoCombate)+"\n -"+getString(R.string.objetivos)+": ${it.objetivos}"
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
                binding.tvDatosPilotoAsigMi.text = "\n -"+getString(R.string.nombre)+": ${it.nombre}\n -"+getString(R.string.edad)+": ${it.edad}\n -"+getString(R.string.exp)+": ${it.experiencia}"
                Glide.with(requireContext()).load(it.foto).into(binding.ivFotoPilotoAsigMi)
            }

        }
        fragAsignarMisionesViewModel.resAsignacion.observe(viewLifecycleOwner){
            if (it!=null && it==true) {
                Navigation.findNavController(requireView()).navigate(R.id.nav_fragBajaMisiones)
                fragAsignarMisionesViewModel.restablecerResAsignacion()
            }
        }

        fragAsignarMisionesViewModel.errorCode.observe(viewLifecycleOwner){error->
            if (error != null){
                when(error){
                    201 -> Toast.makeText(requireContext(), getString(R.string.msjMisionAsignada), Toast.LENGTH_SHORT).show()
                    409 -> Toast.makeText(requireContext(), getString(R.string.errAsignar), Toast.LENGTH_SHORT).show()
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

        fragAsignarMisionesViewModel.obtenerTiposMisionVM()

        binding.btnAsignarAsigMi.setOnClickListener {
            if (binding.spElegirPilotoAsigMi.selectedItem == null){
                Toast.makeText(requireContext(), getString(R.string.errSeleccPiloto), Toast.LENGTH_SHORT).show()
            }else {
                fragAsignarMisionesViewModel.asignarMisionVM(Asignacion(0, idMision!!, fragAsignarMisionesViewModel.piloto.value!!.id, 0))
            }
        }
        binding.btnCancelarAsigMi.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.nav_fragBajaMisiones)
        }
    }
}