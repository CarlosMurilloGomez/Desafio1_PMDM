package com.example.desafio1_appvader.ventanas.admin

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.desafio1_appvader.R
import com.example.desafio1_appvader.databinding.FragmentFragAltaMisionesBinding
import com.example.desafio1_appvader.modelo.Tipo
import com.example.desafio1_appvader.modelo.mision.Mision
import com.example.desafio1_appvader.modelo.mision.MisionBombardeo
import com.example.desafio1_appvader.modelo.mision.MisionCaza
import com.example.desafio1_appvader.modelo.mision.MisionVuelo
import com.example.desafio1_appvader.modelo.nave.TipoCargaPasajeros
import java.io.File
import java.io.FileOutputStream

class FragAltaMisiones : Fragment() {
    private var _binding: FragmentFragAltaMisionesBinding? = null
    private val binding get() = _binding!!
    private val fragAltaMisionesViewModel: FragAltaMisionesViewModel by viewModels()

    companion object {
        fun newInstance() = FragAltaMisiones()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragAltaMisionesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var tipos = ArrayList<Tipo>()
        fragAltaMisionesViewModel.tipos.observe(viewLifecycleOwner){
            tipos = it as ArrayList<Tipo>
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tipos)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spTipoAltaMi.adapter = adapter
        }
        binding.spTipoAltaMi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position)
                when ((selectedItem as Tipo).id) {
                    1 -> {
                        binding.chBoxCargaAltaMi.visibility = View.VISIBLE
                        binding.chBoxPasajerosAltaMi.visibility = View.VISIBLE
                        binding.etDuracionAltaMi.visibility = View.VISIBLE
                        binding.textInputLayout2.visibility = View.VISIBLE

                        binding.etObjetivosAltaMi.visibility = View.GONE
                        binding.textInputLayout3.visibility = View.GONE
                        binding.etObjetivosAltaMi.setText("")
                    }
                    2 -> {
                        binding.chBoxCargaAltaMi.visibility = View.VISIBLE
                        binding.chBoxPasajerosAltaMi.visibility = View.VISIBLE
                        binding.etObjetivosAltaMi.visibility = View.VISIBLE
                        binding.textInputLayout3.visibility = View.VISIBLE

                        binding.etDuracionAltaMi.visibility = View.GONE
                        binding.textInputLayout2.visibility = View.GONE
                        binding.etDuracionAltaMi.setText("")
                    }
                    3 -> {
                        binding.etObjetivosAltaMi.visibility = View.VISIBLE
                        binding.textInputLayout3.visibility = View.VISIBLE

                        binding.chBoxCargaAltaMi.visibility = View.GONE
                        binding.chBoxCargaAltaMi.isChecked = false
                        binding.chBoxPasajerosAltaMi.visibility = View.GONE
                        binding.chBoxPasajerosAltaMi.isChecked = false
                        binding.etDuracionAltaMi.visibility = View.GONE
                        binding.textInputLayout2.visibility = View.GONE
                        binding.etDuracionAltaMi.setText("")
                    }
                }
                actualizarNaves()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                //Acción cuando no se selecciona nada
            }
        }
        var naves = ArrayList<String>()
        fragAltaMisionesViewModel.naves.observe(viewLifecycleOwner){
            if (it != null){
                Log.e("Carlos", it.toString())
                naves.clear()
                for (nave in it){
                    naves.add(nave.matricula)
                }
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, naves)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spNaveAltaMi.adapter = adapter
            }
        }
        fragAltaMisionesViewModel.errorCode.observe(viewLifecycleOwner){error->
            if (error != null){
                when(error){
                    201 -> Toast.makeText(requireContext(), "Mision registrada correctamente", Toast.LENGTH_SHORT).show()
                    409 -> Toast.makeText(requireContext(), "Error al registrar", Toast.LENGTH_SHORT).show()
                }
            }
        }
        fragAltaMisionesViewModel.idMisionInsertada.observe(viewLifecycleOwner){ id ->
            if (id != null){
                var carga = 0
                var pasajeros = 0
                if (binding.chBoxCargaAltaMi.isChecked){
                    carga= 1
                }
                if (binding.chBoxPasajerosAltaMi.isChecked){
                    pasajeros = 1
                }
                when ((binding.spTipoAltaMi.selectedItem as Tipo).id) {
                    1 -> fragAltaMisionesViewModel.registrarVueloVM(MisionVuelo(id, binding.etDuracionAltaMi.text.toString().toInt(), carga, pasajeros))
                    2 -> fragAltaMisionesViewModel.registrarBombardeoVM(MisionBombardeo(id, binding.etObjetivosAltaMi.text.toString().toInt(), carga, pasajeros))
                    3 -> fragAltaMisionesViewModel.registrarCazaVM(MisionCaza(id, binding.etObjetivosAltaMi.text.toString().toInt()))
                }
            }
        }
        fragAltaMisionesViewModel.resRegistro.observe(viewLifecycleOwner){
            if (it!=null && it==true) {
                Navigation.findNavController(requireView()).navigate(R.id.nav_fragBajaMisiones)
                fragAltaMisionesViewModel.restablecerResRegistro()
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

        fragAltaMisionesViewModel.obtenerTiposMisionVM()


        binding.chBoxCargaAltaMi.setOnCheckedChangeListener{ _,_ ->
            actualizarNaves()
        }
        binding.chBoxPasajerosAltaMi.setOnCheckedChangeListener{ _,_ ->
            actualizarNaves()
        }

        binding.btnRegistrarAltaMi.setOnClickListener {
            if (binding.etNombreAltaMi.text.isNullOrEmpty() || binding.spNaveAltaMi.selectedItem == null ||
                (binding.spTipoAltaMi.selectedItem as Tipo).id == 1 && binding.etDuracionAltaMi.text.isNullOrEmpty() ||
                ((binding.spTipoAltaMi.selectedItem as Tipo).id == 2 && binding.etObjetivosAltaMi.text.isNullOrEmpty()) ||
                (binding.spTipoAltaMi.selectedItem as Tipo).id == 3 && binding.etObjetivosAltaMi.text.isNullOrEmpty()){
                Toast.makeText(requireContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            }else {
                var exp = calcularExperiencia((binding.spTipoAltaMi.selectedItem as Tipo).id)
                fragAltaMisionesViewModel.registrarMisionVM(Mision(0, binding.etNombreAltaMi.text.toString(), exp, (binding.spNaveAltaMi.selectedItem as String), (binding.spTipoAltaMi.selectedItem as Tipo).id))
            }
        }


        binding.btnCancelarAltaMi.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.nav_fragBajaMisiones)
        }
    }

    fun calcularExperiencia(tipo : Int) : Int{
        var exp = 0
        if (tipo == 1) {
            exp += 10
            if (binding.chBoxCargaAltaMi.isChecked) {
                exp += 5
            }
            if (binding.chBoxPasajerosAltaMi.isChecked) {
                exp += 10
            }
        }else if (tipo == 2){
            exp += (5 * binding.etObjetivosAltaMi.text.toString().toInt())
            if (binding.chBoxCargaAltaMi.isChecked) {
                exp += 5
            }
            if (binding.chBoxPasajerosAltaMi.isChecked) {
                exp += 10
            }
        }else if (tipo == 3){
            exp += (10 * binding.etObjetivosAltaMi.text.toString().toInt())
        }
        return exp
    }

    fun actualizarNaves(){
        var carga = 0
        var pasajeros = 0
        if (binding.chBoxCargaAltaMi.isChecked){
            carga= 1
        }
        if (binding.chBoxPasajerosAltaMi.isChecked){
            pasajeros = 1
        }
        var tipo = 0
        when ((binding.spTipoAltaMi.selectedItem as Tipo).id) {
            1 -> tipo = 3
            2 -> tipo = 2
            3 -> tipo = 1
        }
        fragAltaMisionesViewModel.obtenerNavesPorTipoCargaPasajerosVM(TipoCargaPasajeros(tipo, carga, pasajeros))
    }
}