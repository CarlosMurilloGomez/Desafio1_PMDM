package com.example.desafio1_appvader.ventanas.admin

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
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
import com.example.desafio1_appvader.adaptadores.AdaptadorBajaPiloto
import com.example.desafio1_appvader.api.MainViewModel
import com.example.desafio1_appvader.databinding.FragmentFragBajaPilotoBinding
import com.example.desafio1_appvader.modelo.usuario.Usuario

class FragBajaPiloto : Fragment() {
    private var _binding: FragmentFragBajaPilotoBinding? = null
    private val binding get() = _binding!!

    private val fragBajaPilotoViewModel: FragBajaPilotoViewModel by viewModels()

    var datosRepresentar : ArrayList<Usuario> = ArrayList()
    lateinit var adaptador : AdaptadorBajaPiloto
    companion object {
        fun newInstance() = FragBajaPiloto()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragBajaPilotoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        fragBajaPilotoViewModel.errorCode.observe(viewLifecycleOwner){error ->
            if (error!=null) {
                when (error) {
                    400 -> Toast.makeText(requireContext(), "Error al introducir los datos", Toast.LENGTH_SHORT).show()
                    404 -> Toast.makeText(requireContext(), "No existe el usuario", Toast.LENGTH_SHORT).show()
                    409 -> Toast.makeText(requireContext(), "Error al eliminar", Toast.LENGTH_SHORT).show()
                    202 -> Toast.makeText(requireContext(),"Usuario Eliminado",Toast.LENGTH_SHORT).show()
                }
                fragBajaPilotoViewModel.restablecerError()
            }
        }
        fragBajaPilotoViewModel.usuarios.observe(viewLifecycleOwner) {
            datosRepresentar.clear()
            datosRepresentar.addAll(it)
            adaptador.notifyDataSetChanged()
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
        fragBajaPilotoViewModel.obtenerPilotosVM()

        binding.btnRegistrarUsuario.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.nav_fragAltaPiloto)
            (requireActivity() as AppCompatActivity).supportActionBar?.title = "REGISTRAR USUARIO"
        }

    }
    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyViewBajaPilotos.layoutManager = linearLayoutManager
        adaptador = AdaptadorBajaPiloto(datosRepresentar,requireContext(), fragBajaPilotoViewModel)
        binding.recyViewBajaPilotos.adapter = adaptador
    }

}

