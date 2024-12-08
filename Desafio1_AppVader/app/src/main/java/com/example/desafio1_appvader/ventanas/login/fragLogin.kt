package com.example.desafio1_appvader.ventanas.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.desafio1_appvader.R
import com.example.desafio1_appvader.ventanas.admin.VentanaAdmin
import com.example.desafio1_appvader.api.MainViewModel
import com.example.desafio1_appvader.databinding.FragmentFragLoginBinding
import com.example.desafio1_appvader.modelo.usuario.UsuarioLogIn
import com.example.desafio1_appvader.ventanas.piloto.VentanaPiloto

class fragLogin : Fragment() {
    private var _binding: FragmentFragLoginBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    companion object {
        fun newInstance() = fragLogin()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.cerrarSesionVM()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root


        mainViewModel.errorCode.observe(viewLifecycleOwner){error ->
            if (error!=null) {
                when (error) {
                    400 -> Toast.makeText(requireContext(), getString(R.string.errPassword2), Toast.LENGTH_SHORT).show()
                    404 -> Toast.makeText(requireContext(), getString(R.string.errUsuario2), Toast.LENGTH_SHORT).show()
                    200 -> Toast.makeText(requireContext(),getString(R.string.msjSesionIniciada),Toast.LENGTH_SHORT).show()
                }
                mainViewModel.restablecerError()
            }
        }
        mainViewModel.usuario.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.activo == 0){
                    Navigation.findNavController(requireView()).navigate(R.id.nav_fragActivarCuenta)
                    limpiarCampos()
                }else if (it.rol == 1) {
                    val intent = Intent(requireContext(), VentanaAdmin::class.java)
                    intent.putExtra("idUsuario", it.id)
                    startActivity(intent)
                    limpiarCampos()
                }else if (it.rol == 2){
                    val intent = Intent(requireContext(), VentanaPiloto::class.java)
                    intent.putExtra("idUsuario", it.id)
                    startActivity(intent)
                    limpiarCampos()
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
                Toast.makeText(requireContext(), resources.getString(R.string.errRellenaCampos), Toast.LENGTH_SHORT).show()
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

    fun limpiarCampos(){
        binding.etUsuarioLogin.setText("")
        binding.etPasswordLogin.setText("")
    }
}