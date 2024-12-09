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
import android.widget.SeekBar
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
            for (tipo in it){
                when (tipo.id) {
                    1 -> tipos.add(Tipo(1, getString(R.string.tipoVuelo)))
                    2 -> tipos.add(Tipo(2, getString(R.string.tipoBombardeo)))
                    3 -> tipos.add(Tipo(3, getString(R.string.tipoCombate)))
                }
            }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tipos)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spTipoAltaMi.adapter = adapter
        }
        binding.spTipoAltaMi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position)
                when ((selectedItem as Tipo).id) {
                    1 -> {
                        binding.swCargaAltaMi.visibility = View.VISIBLE
                        binding.swPasajerosAltaMi.visibility = View.VISIBLE
                        binding.etDuracionAltaMi.visibility = View.VISIBLE
                        binding.textInputLayout2.visibility = View.VISIBLE

                        binding.lbNumObjetivosAltaMi.visibility = View.GONE
                        binding.sbNumObjetivosAltaMi.visibility = View.GONE
                        binding.tvNumObjetivosAltaMi.visibility = View.GONE
                        binding.tvNumObjetivosAltaMi.text = "0"
                    }
                    2 -> {
                        binding.swCargaAltaMi.visibility = View.VISIBLE
                        binding.swPasajerosAltaMi.visibility = View.VISIBLE
                        binding.lbNumObjetivosAltaMi.visibility = View.VISIBLE
                        binding.sbNumObjetivosAltaMi.visibility = View.VISIBLE
                        binding.tvNumObjetivosAltaMi.visibility = View.VISIBLE

                        binding.etDuracionAltaMi.visibility = View.GONE
                        binding.textInputLayout2.visibility = View.GONE
                        binding.etDuracionAltaMi.setText("")
                    }
                    3 -> {
                        binding.lbNumObjetivosAltaMi.visibility = View.VISIBLE
                        binding.sbNumObjetivosAltaMi.visibility = View.VISIBLE
                        binding.tvNumObjetivosAltaMi.visibility = View.VISIBLE

                        binding.swCargaAltaMi.visibility = View.GONE
                        binding.swCargaAltaMi.isChecked = false
                        binding.swPasajerosAltaMi.visibility = View.GONE
                        binding.swPasajerosAltaMi.isChecked = false
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
                if (it == emptyList<String>()){
                    binding.lbNoHayNavesAltaMi.visibility = View.VISIBLE
                }else{
                    binding.lbNoHayNavesAltaMi.visibility = View.GONE
                }
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
                    201 -> Toast.makeText(requireContext(), getString(R.string.msjMisionRegistrada), Toast.LENGTH_SHORT).show()
                    409 -> Toast.makeText(requireContext(), getString(R.string.errRegistrar), Toast.LENGTH_SHORT).show()
                }
            }
        }
        fragAltaMisionesViewModel.idMisionInsertada.observe(viewLifecycleOwner){ id ->
            if (id != null){
                var carga = 0
                var pasajeros = 0
                if (binding.swCargaAltaMi.isChecked){
                    carga= 1
                }
                if (binding.swPasajerosAltaMi.isChecked){
                    pasajeros = 1
                }
                when ((binding.spTipoAltaMi.selectedItem as Tipo).id) {
                    1 -> fragAltaMisionesViewModel.registrarVueloVM(MisionVuelo(id, binding.etDuracionAltaMi.text.toString().toInt(), carga, pasajeros))
                    2 -> fragAltaMisionesViewModel.registrarBombardeoVM(MisionBombardeo(id, binding.tvNumObjetivosAltaMi.text.toString().toInt(), carga, pasajeros))
                    3 -> fragAltaMisionesViewModel.registrarCazaVM(MisionCaza(id, binding.tvNumObjetivosAltaMi.text.toString().toInt()))
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

        binding.swCargaAltaMi.setOnCheckedChangeListener { _, _ ->
            actualizarNaves()
        }

        binding.swPasajerosAltaMi.setOnCheckedChangeListener{ _,_ ->
            actualizarNaves()
        }

        binding.sbNumObjetivosAltaMi.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvNumObjetivosAltaMi.text = "$progress"
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })

        binding.btnRegistrarAltaMi.setOnClickListener {
            if (binding.etNombreAltaMi.text.isNullOrEmpty() || binding.spNaveAltaMi.selectedItem == null ||
                (binding.spTipoAltaMi.selectedItem as Tipo).id == 1 && binding.etDuracionAltaMi.text.isNullOrEmpty() ||
                ((binding.spTipoAltaMi.selectedItem as Tipo).id == 2 && binding.tvNumObjetivosAltaMi.text.toString().toInt() == 0) ||
                (binding.spTipoAltaMi.selectedItem as Tipo).id == 3 && binding.tvNumObjetivosAltaMi.text.toString().toInt() == 0){
                Toast.makeText(requireContext(), getString(R.string.errRellenaCampos), Toast.LENGTH_SHORT).show()
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
            if (binding.swCargaAltaMi.isChecked) {
                exp += 5
            }
            if (binding.swPasajerosAltaMi.isChecked) {
                exp += 10
            }
        }else if (tipo == 2){
            exp += (5 * binding.tvNumObjetivosAltaMi.text.toString().toInt())
            if (binding.swCargaAltaMi.isChecked) {
                exp += 5
            }
            if (binding.swPasajerosAltaMi.isChecked) {
                exp += 10
            }
        }else if (tipo == 3){
            exp += (10 * binding.tvNumObjetivosAltaMi.text.toString().toInt())
        }
        return exp
    }

    fun actualizarNaves(){
        var carga = 0
        var pasajeros = 0
        if (binding.swCargaAltaMi.isChecked){
            carga= 1
        }
        if (binding.swPasajerosAltaMi.isChecked){
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