package com.example.desafio1_appvader

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafio1_appvader.adaptadores.AdaptadorBajaPiloto
import com.example.desafio1_appvader.api.MainViewModel
import com.example.desafio1_appvader.databinding.FragmentFragBajaPilotoBinding
import com.example.desafio1_appvader.modelo.Usuario

class FragBajaPiloto : Fragment() {
    private var _binding: FragmentFragBajaPilotoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FragBajaPilotoViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

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

        mainViewModel.errorCode.observe(viewLifecycleOwner){error ->
            if (error!=null) {
                when (error) {
                    400 -> Toast.makeText(requireContext(), "Error al introducir los datos", Toast.LENGTH_SHORT).show()
                    404 -> Toast.makeText(requireContext(), "No existe el usuario", Toast.LENGTH_SHORT).show()
                    409 -> Toast.makeText(requireContext(), "Error al eliminar", Toast.LENGTH_SHORT).show()
                    202 -> Toast.makeText(requireContext(),"Usuario Eliminado",Toast.LENGTH_SHORT).show()
                }
                mainViewModel.restablecerError()
            }
        }
        mainViewModel.usuarios.observe(viewLifecycleOwner) {
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
        mainViewModel.obtenerPilotosVM()


    }
    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyViewBajaPilotos.layoutManager = linearLayoutManager
        adaptador = AdaptadorBajaPiloto(datosRepresentar,requireContext(), mainViewModel)
        binding.recyViewBajaPilotos.adapter = adaptador
    }

}