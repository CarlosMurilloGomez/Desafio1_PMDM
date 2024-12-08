package com.example.desafio1_appvader.adaptadores

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafio1_appvader.R
import com.example.desafio1_appvader.modelo.mision.MisionMostrar
import com.example.desafio1_appvader.modelo.nave.NaveMostrar
import com.example.desafio1_appvader.ventanas.admin.FragBajaMisionesViewModel
import com.example.desafio1_appvader.ventanas.admin.FragBajaNaveViewModel

class AdaptadorMisionesAdmin (var misiones: ArrayList<MisionMostrar>, var context: Context, var fragBajaMisionesViewModel: FragBajaMisionesViewModel) : RecyclerView.Adapter<AdaptadorMisionesAdmin.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = misiones.get(position)
        holder.bind(item, context, position, this, fragBajaMisionesViewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card_baja_mision, parent, false)
        val viewHolder = ViewHolder(vista)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return misiones.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre = view.findViewById(R.id.tvNombreBajaMi) as TextView
        val exp = view.findViewById(R.id.tvExpBajaMi) as TextView
        val tipo = view.findViewById(R.id.tvTipoBajaMi) as TextView
        val nave = view.findViewById(R.id.tvNaveBajaMi) as TextView

        val eliminar = view.findViewById(R.id.btnEliminarMision) as Button
        val asignar = view.findViewById(R.id.btnAsignarMision) as Button

        fun bind(mision: MisionMostrar, context: Context, pos: Int, adaptadorMisionesAdmin: AdaptadorMisionesAdmin, fragBajaMisionesViewModel: FragBajaMisionesViewModel){
            nombre.text = mision.nombre
            tipo.text = mision.tipo
            exp.text = mision.exp.toString()
            nave.text = mision.naveAsig


            itemView.setOnClickListener {
                when (mision.tipo){
                    "Vuelo" -> fragBajaMisionesViewModel.obtenerVueloPorIdVM(mision.id)
                    "Bombardeo" -> fragBajaMisionesViewModel.obtenerBombardeoPorIdVM(mision.id)
                    "Combate" -> fragBajaMisionesViewModel.obtenerCazaPorIdVM(mision.id)
                }
            }

            eliminar.setOnClickListener {
                AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.eliminarMision))
                    .setMessage(context.getString(R.string.eliminarMisionPregunta)+" ${mision.nombre}?")
                    .setPositiveButton(context.getString(R.string.si), DialogInterface.OnClickListener(function = { dialog: DialogInterface, which: Int ->
                        fragBajaMisionesViewModel.eliminarMisionVM(mision.id)
                    }))
                    .setNegativeButton(context.getString(R.string.no), ({ dialog: DialogInterface, which: Int ->
                        Toast.makeText(context,context.getString(R.string.eliminacionCancelada), Toast.LENGTH_SHORT).show()
                    }))
                    .show()
            }

            asignar.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.nav_fragAsignarMisiones, bundleOf(Pair("idMision", mision.id)))
            }



        }
    }
}