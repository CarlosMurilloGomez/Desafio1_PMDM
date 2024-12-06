package com.example.desafio1_appvader.ventanas

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafio1_appvader.R
import com.example.desafio1_appvader.adaptadores.AdaptadorBajaPiloto
import com.example.desafio1_appvader.adaptadores.AdaptadorRanking
import com.example.desafio1_appvader.api.MainViewModel
import com.example.desafio1_appvader.databinding.FragmentFragRankingBinding
import com.example.desafio1_appvader.modelo.usuario.Usuario

class FragRanking : Fragment() {
    private var _binding: FragmentFragRankingBinding? = null
    private val binding get() = _binding!!
    private val fragRankingViewModel: FragRankingViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    var datosRepresentar : ArrayList<Usuario> = ArrayList()
    lateinit var adaptador : AdaptadorRanking
    companion object {
        fun newInstance() = FragRanking()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragRankingBinding.inflate(inflater, container, false)
        val root: View = binding.root


        fragRankingViewModel.usuarios.observe(viewLifecycleOwner) {
            datosRepresentar.clear()
            datosRepresentar.addAll(it)
            adaptador.notifyDataSetChanged()
            if (datosRepresentar.isEmpty()){
                binding.lbNoHayUsuariosRanking.visibility = View.VISIBLE
            }
            else{
                binding.lbNoHayUsuariosRanking.visibility = View.GONE
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
        fragRankingViewModel.obtenerRankingVM()



    }
    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyViewRanking.layoutManager = linearLayoutManager
        adaptador = AdaptadorRanking(datosRepresentar,requireContext(), fragRankingViewModel, mainViewModel)
        binding.recyViewRanking.adapter = adaptador
    }
}