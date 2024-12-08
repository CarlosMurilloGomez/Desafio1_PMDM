package com.example.desafio1_appvader.ventanas.admin

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafio1_appvader.R
import com.example.desafio1_appvader.adaptadores.AdaptadorMisionesAdmin
import com.example.desafio1_appvader.adaptadores.AdaptadorNaves
import com.example.desafio1_appvader.api.MainViewModel
import com.example.desafio1_appvader.databinding.FragmentFragBajaMisionesBinding
import com.example.desafio1_appvader.databinding.FragmentFragBajaNaveBinding
import com.example.desafio1_appvader.modelo.Tipo
import com.example.desafio1_appvader.modelo.mision.Mision
import com.example.desafio1_appvader.modelo.mision.MisionMostrar
import com.example.desafio1_appvader.modelo.nave.NaveMostrar

class FragBajaMisiones : Fragment() {
    private var _binding: FragmentFragBajaMisionesBinding? = null
    private val binding get() = _binding!!

    private val fragBajaMisionesViewModel: FragBajaMisionesViewModel by viewModels()
    var datosRepresentar : ArrayList<MisionMostrar> = ArrayList()
    lateinit var adaptador : AdaptadorMisionesAdmin
    companion object {
        fun newInstance() = FragBajaMisiones()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragBajaMisionesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var tipos = ArrayList<Tipo>()
        fragBajaMisionesViewModel.tipos.observe(viewLifecycleOwner){
            for (tipo in it){
                when (tipo.id) {
                    1 -> tipos.add(Tipo(1, resources.getString(R.string.tipoVuelo)))
                    2 -> tipos.add(Tipo(2, resources.getString(R.string.tipoBombardeo)))
                    3 -> tipos.add(Tipo(3, resources.getString(R.string.tipoCombate)))
                }
            }
            fragBajaMisionesViewModel.obtenerMisionesVM()
        }
        fragBajaMisionesViewModel.errorCode.observe(viewLifecycleOwner){error ->
            if (error!=null) {
                when (error) {
                    404 -> Toast.makeText(requireContext(), getString(R.string.errMision), Toast.LENGTH_SHORT).show()
                    409 -> Toast.makeText(requireContext(), getString(R.string.errEliminar), Toast.LENGTH_SHORT).show()
                    202 -> Toast.makeText(requireContext(),getString(R.string.msjMisionEliminada), Toast.LENGTH_SHORT).show()
                }
                fragBajaMisionesViewModel.restablecerError()
            }
        }
        fragBajaMisionesViewModel.misiones.observe(viewLifecycleOwner) {
            datosRepresentar.clear()
            for (mision in it) {
                var misionMostrar = MisionMostrar(mision.id, mision.nombre, mision.exp, mision.naveAsig, "", 0, 0)
                for (tipo in tipos){
                    if (tipo.id == mision.tipo){
                        misionMostrar.tipo = tipo.tipo
                    }
                }

                datosRepresentar.add(misionMostrar)
            }
            adaptador.notifyDataSetChanged()
            if (datosRepresentar.isEmpty()){
                binding.lbNoHayMisionesBajaMi.visibility = View.VISIBLE
            }
            else {
                binding.lbNoHayMisionesBajaMi.visibility = View.GONE
            }
        }

        fragBajaMisionesViewModel.vuelo.observe(viewLifecycleOwner){
            if (it != null){
                var carga = resources.getString(R.string.si)
                var pasajeros = resources.getString(R.string.si)
                if (it.carga == 0){
                    carga = resources.getString(R.string.no)
                }
                if (it.pasajeros == 0){
                    pasajeros = resources.getString(R.string.no)
                }
                AlertDialog.Builder(context)
                    .setTitle(resources.getString(R.string.tipoVuelo)+" "+resources.getString(R.string.con)+" id: ${it.idMision}")
                    .setMessage(" -"+resources.getString(R.string.carga)+": $carga\n\n -"+resources.getString(R.string.pasajeros)+": $pasajeros\n\n -"+resources.getString(R.string.duracion)+": ${it.duracion}min")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener(function = { dialog: DialogInterface, which: Int ->
                        dialog.dismiss()
                    }))
                    .show()
                fragBajaMisionesViewModel.restablecerVuelo()
            }
        }
        fragBajaMisionesViewModel.bombardeo.observe(viewLifecycleOwner){
            if (it != null){
                var carga = resources.getString(R.string.si)
                var pasajeros = resources.getString(R.string.si)
                if (it.carga == 0){
                    carga = resources.getString(R.string.no)
                }
                if (it.pasajeros == 0){
                    pasajeros = resources.getString(R.string.no)
                }
                AlertDialog.Builder(context)
                    .setTitle(resources.getString(R.string.tipoBombardeo)+" "+resources.getString(R.string.con)+" id: ${it.idMision}")
                    .setMessage(" -"+resources.getString(R.string.carga)+": $carga\n\n -"+resources.getString(R.string.pasajeros)+": $pasajeros\n\n -"+resources.getString(R.string.objetivos)+": ${it.objetivos}")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener(function = { dialog: DialogInterface, which: Int ->
                        dialog.dismiss()
                    }))
                    .show()
                fragBajaMisionesViewModel.restablecerBombardeo()
            }
        }
        fragBajaMisionesViewModel.caza.observe(viewLifecycleOwner){
            if (it != null){
                AlertDialog.Builder(context)
                    .setTitle(resources.getString(R.string.tipoCombate)+" "+resources.getString(R.string.con)+" id: ${it.idMision}")
                    .setMessage(" -"+resources.getString(R.string.objetivos)+": ${it.objetivos}")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener(function = { dialog: DialogInterface, which: Int ->
                        dialog.dismiss()
                    }))
                    .show()
                fragBajaMisionesViewModel.restablecerCaza()
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
        fragBajaMisionesViewModel.obtenerTiposMisionVM()

        binding.btnRegistrarMision.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.nav_fragAltaMisiones)
        }

    }
    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyViewBajaMisiones.layoutManager = linearLayoutManager
        adaptador = AdaptadorMisionesAdmin(datosRepresentar,requireContext(), fragBajaMisionesViewModel)
        binding.recyViewBajaMisiones.adapter = adaptador
    }
}