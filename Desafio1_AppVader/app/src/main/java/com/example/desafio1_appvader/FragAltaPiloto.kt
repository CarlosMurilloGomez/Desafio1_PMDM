package com.example.desafio1_appvader

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.desafio1_appvader.api.MainViewModel
import com.example.desafio1_appvader.databinding.FragmentFragAltaPilotoBinding
import com.example.desafio1_appvader.databinding.FragmentFragLoginBinding
import com.example.desafio1_appvader.modelo.Usuario
import com.example.desafio1_appvader.modelo.UsuarioLogIn

class FragAltaPiloto : Fragment() {
    private var _binding: FragmentFragAltaPilotoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FragAltaPilotoViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    companion object {
        fun newInstance() = FragAltaPiloto()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragAltaPilotoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mainViewModel.errorCode.observe(viewLifecycleOwner){error->
            if (error != null){
                when(error){
                    400 -> Toast.makeText(requireContext(), "El usuario ya existe", Toast.LENGTH_SHORT).show()
                    201 -> Toast.makeText(requireContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                    409 -> Toast.makeText(requireContext(), "Error al registrar", Toast.LENGTH_SHORT).show()
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

        binding.btnRegistrarAltaUs.setOnClickListener {
            if (binding.etUsuarioAltaUs.text.isNullOrEmpty() || binding.etEdadAltaUs.text.isNullOrEmpty() || binding.etPasswordAltaUs.text.isNullOrEmpty() || binding.etExperienciaAltaUs.text.isNullOrEmpty()){
                Toast.makeText(requireContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            }else {
                mainViewModel.registrarUsuarioVM(Usuario(0, binding.etUsuarioAltaUs.text.toString(),  binding.etPasswordAltaUs.text.toString(), 0, "", binding.etEdadAltaUs.text.toString().toInt(), binding.etExperienciaAltaUs.text.toString().toInt(), 2))
            }
        }
    }
}