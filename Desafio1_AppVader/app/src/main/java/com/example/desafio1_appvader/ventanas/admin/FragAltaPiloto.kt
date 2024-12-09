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
import com.example.desafio1_appvader.R
import com.example.desafio1_appvader.databinding.FragmentFragAltaPilotoBinding
import com.example.desafio1_appvader.modelo.usuario.Usuario

class FragAltaPiloto : Fragment() {
    private var _binding: FragmentFragAltaPilotoBinding? = null
    private val binding get() = _binding!!
    private val fragAltaPilotoViewModel: FragAltaPilotoViewModel by viewModels()
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

        fragAltaPilotoViewModel.errorCode.observe(viewLifecycleOwner){error->
            if (error != null){
                when(error){
                    400 -> Toast.makeText(requireContext(), getString(R.string.errUsuario), Toast.LENGTH_SHORT).show()
                    201 -> Toast.makeText(requireContext(), getString(R.string.msjUsuarioRegistrado), Toast.LENGTH_SHORT).show()
                    409 -> Toast.makeText(requireContext(), getString(R.string.errRegistrar), Toast.LENGTH_SHORT).show()
                }
            }
        }

        fragAltaPilotoViewModel.resRegistro.observe(viewLifecycleOwner){
            if (it!=null && it==true) {
                Navigation.findNavController(requireView()).navigate(R.id.nav_fragBajaPiloto)
                fragAltaPilotoViewModel.restablecerResRegistro()
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
                Toast.makeText(requireContext(), getString(R.string.errRellenaCampos), Toast.LENGTH_SHORT).show()
            }else if(binding.etUsuarioAltaUs.text.toString().trim().contains(" ") || binding.etPasswordAltaUs.text.toString().contains(" ")){
                Toast.makeText(requireContext(), getString(R.string.errEspacion), Toast.LENGTH_SHORT).show()
            }
            else{
                fragAltaPilotoViewModel.registrarUsuarioVM(Usuario(0, binding.etUsuarioAltaUs.text.toString().trim(),  binding.etPasswordAltaUs.text.toString(), 0, "", binding.etEdadAltaUs.text.toString().toInt(), binding.etExperienciaAltaUs.text.toString().toInt(), 2))
            }
        }
        binding.btnCancelarRegistroUsuario.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.nav_fragBajaPiloto)
        }

    }
}