package com.example.desafio1_appvader.adaptadores

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio1_appvader.R
import com.example.desafio1_appvader.modelo.mision.MisionMostrar
import com.example.desafio1_appvader.ventanas.admin.FragBajaMisionesViewModel
import com.example.desafio1_appvader.ventanas.piloto.FragMisionesPendientesViewModel

class AdaptadorMisionesPiloto (var misiones: ArrayList<MisionMostrar>, var context: Context, var fragMisionesPendientesViewModel: FragMisionesPendientesViewModel) : RecyclerView.Adapter<AdaptadorMisionesPiloto.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = misiones.get(position)
        holder.bind(item, context, position, this, fragMisionesPendientesViewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card_asignacion, parent, false)
        val viewHolder = ViewHolder(vista)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return misiones.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val numAsig = view.findViewById(R.id.tvAsigMisionPiloto) as TextView
        val nombre = view.findViewById(R.id.tvNombreMisionPiloto) as TextView
        val exp = view.findViewById(R.id.tvExpMisionPiloto) as TextView
        val tipo = view.findViewById(R.id.tvTipoMisionPiloto) as TextView
        val nave = view.findViewById(R.id.tvNaveMisionPiloto) as TextView

        fun bind(mision: MisionMostrar, context: Context, pos: Int, adaptadorMisionesPiloto: AdaptadorMisionesPiloto, fragMisionesPendientesViewModel: FragMisionesPendientesViewModel){
            numAsig.text = mision.idAsignacion.toString()
            nombre.text = mision.nombre
            tipo.text = mision.tipo
            exp.text = mision.exp.toString()
            nave.text = mision.naveAsig

            if (mision.estado == 2){
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.conseguida))
            }
            else if (mision.estado == 3){
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.fallada))
            }

            itemView.setOnClickListener {
                when (mision.tipo){
                    "Vuelo" -> fragMisionesPendientesViewModel.obtenerVueloPorIdVM(mision.id)
                    "Bombardeo" -> fragMisionesPendientesViewModel.obtenerBombardeoPorIdVM(mision.id)
                    "Combate" -> fragMisionesPendientesViewModel.obtenerCazaPorIdVM(mision.id)
                }
            }

            itemView.setOnLongClickListener {
                AlertDialog.Builder(context)
                    .setTitle("Simular mision")
                    .setMessage("¿Deseas iniciar la simulación de la mision ${mision.nombre}?")
                    .setPositiveButton("Si", DialogInterface.OnClickListener(function = { dialog: DialogInterface, which: Int ->
                        //Navigation.findNavController(it).navigate(R.id.nav_fragSimulacion, bundleOf(Pair("idMision", mision.id)))
                        //(context as AppCompatActivity).supportActionBar?.title = "SIMULACION"
                        Toast.makeText(context,"Yendo a la simulacion", Toast.LENGTH_SHORT).show()
                    }))
                    .setNegativeButton("No", ({ dialog: DialogInterface, which: Int -> }))

                true
            }



        }
    }
}