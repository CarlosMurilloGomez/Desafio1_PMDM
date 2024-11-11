package com.example.desafio1_appvader

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.desafio1_appvader.api.MainViewModel
import com.example.desafio1_appvader.databinding.FragmentFragLoginBinding
import com.example.desafio1_appvader.modelo.UsuarioLogIn

class fragLogin : Fragment() {
    private var _binding: FragmentFragLoginBinding? = null
    private val binding get() = _binding!!
    private val fragloginViewModel: FragLoginViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    companion object {
        fun newInstance() = fragLogin()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mainViewModel.cerrarSesionVM()
        mainViewModel.errorCode.observe(viewLifecycleOwner){error ->
            if (error!=null) {
                when (error) {
                    400 -> Toast.makeText(requireContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                    404 -> Toast.makeText(requireContext(), "No existe el usuario", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(requireContext(),"Error desconocido:" + error,Toast.LENGTH_SHORT).show()
                }
                mainViewModel.restablecerError()
            }
        }
        mainViewModel.usuario.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.rol == 1) {
                    //Ir a la activity de admin
                }else if (it.activo == 0){
                    Navigation.findNavController(requireView()).navigate(R.id.fragActivarCuenta)
                }else{
                    //Ir a la activity de piloto
                }
                mainViewModel.restablecerUsuario()
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

        binding.btnIniciarSesion.setOnClickListener {
            if (binding.etUsuarioLogin.text.isNullOrBlank() || binding.etPasswordLogin.text.isNullOrBlank()){
                Toast.makeText(requireContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            }else {
                val usuario = binding.etUsuarioLogin.text.toString()
                val password = binding.etPasswordLogin.text.toString()
                mainViewModel.loginVM(UsuarioLogIn(usuario, password))
            }
        }


        binding.btnSalir.setOnClickListener {
            requireActivity().finish()
        }

    }
}