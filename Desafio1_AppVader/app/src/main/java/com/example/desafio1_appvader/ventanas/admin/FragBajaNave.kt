package com.example.desafio1_appvader.ventanas.admin

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafio1_appvader.R
import com.example.desafio1_appvader.adaptadores.AdaptadorBajaPiloto
import com.example.desafio1_appvader.adaptadores.AdaptadorNaves
import com.example.desafio1_appvader.databinding.FragmentFragBajaNaveBinding
import com.example.desafio1_appvader.databinding.FragmentFragBajaPilotoBinding
import com.example.desafio1_appvader.modelo.Tipo
import com.example.desafio1_appvader.modelo.nave.Nave
import com.example.desafio1_appvader.modelo.nave.NaveMostrar
import com.example.desafio1_appvader.modelo.usuario.Usuario

class FragBajaNave : Fragment() {
    private var _binding: FragmentFragBajaNaveBinding? = null
    private val binding get() = _binding!!

    private val fragBajaNaveViewModel: FragBajaNaveViewModel by viewModels()

    var datosRepresentar : ArrayList<NaveMostrar> = ArrayList()
    lateinit var adaptador : AdaptadorNaves
    companion object {
        fun newInstance() = FragBajaNave()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragBajaNaveBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var tipos = ArrayList<Tipo>()
        fragBajaNaveViewModel.tipos.observe(viewLifecycleOwner){
            tipos = it as ArrayList<Tipo>
            fragBajaNaveViewModel.obtenerNavesVM()

        }
        fragBajaNaveViewModel.errorCode.observe(viewLifecycleOwner){error ->
            if (error!=null) {
                when (error) {
                    400 -> Toast.makeText(requireContext(), "Error al introducir los datos", Toast.LENGTH_SHORT).show()
                    404 -> Toast.makeText(requireContext(), "No existe la nave", Toast.LENGTH_SHORT).show()
                    409 -> Toast.makeText(requireContext(), "Error al eliminar", Toast.LENGTH_SHORT).show()
                    202 -> Toast.makeText(requireContext(),"Nave Eliminada", Toast.LENGTH_SHORT).show()
                }
                fragBajaNaveViewModel.restablecerError()
            }
        }
        fragBajaNaveViewModel.naves.observe(viewLifecycleOwner) {
            datosRepresentar.clear()
            for (nave in it) {
                var naveMostrar = NaveMostrar(nave.matricula, nave.foto, "", "", "")
                if (nave.carga == 0){
                    naveMostrar.carga = "No"
                }else{
                    naveMostrar.carga = "Si"
                }
                if (nave.pasajeros == 0){
                    naveMostrar.pasajeros = "No"
                }else{
                    naveMostrar.pasajeros = "Si"
                }
                for (tipo in tipos){
                    if (tipo.id == nave.tipo){
                        naveMostrar.tipo = tipo.tipo
                    }
                }
                datosRepresentar.add(naveMostrar)
            }
            adaptador.notifyDataSetChanged()
            if (datosRepresentar.isEmpty()){
                binding.lbNoHayNavesBajaNa.visibility = View.VISIBLE
            }
            else{
                binding.lbNoHayNavesBajaNa.visibility = View.GONE
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
        fragBajaNaveViewModel.obtenerTiposNavesVM()


        binding.btnRegistrarNave.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.nav_fragAltaNave)
        }

    }
    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyViewBajaNaves.layoutManager = linearLayoutManager
        adaptador = AdaptadorNaves(datosRepresentar,requireContext(), fragBajaNaveViewModel)
        binding.recyViewBajaNaves.adapter = adaptador
    }
}