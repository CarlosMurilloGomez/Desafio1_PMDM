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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafio1_appvader.R
import com.example.desafio1_appvader.modelo.nave.Nave
import com.example.desafio1_appvader.modelo.nave.NaveMostrar
import com.example.desafio1_appvader.modelo.usuario.Usuario
import com.example.desafio1_appvader.ventanas.admin.FragBajaNaveViewModel
import com.example.desafio1_appvader.ventanas.admin.FragBajaPilotoViewModel

class AdaptadorNaves (var naves: ArrayList<NaveMostrar>, var context: Context, var fragBajaNaveViewModel: FragBajaNaveViewModel) : RecyclerView.Adapter<AdaptadorNaves.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = naves.get(position)
        holder.bind(item, context, position, this, fragBajaNaveViewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card_nave, parent, false)
        val viewHolder = ViewHolder(vista)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return naves.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val matricula = view.findViewById(R.id.tvMatriculaBajaNa) as TextView
        val foto = view.findViewById(R.id.ivBajaNa) as ImageView

        val tipo = view.findViewById(R.id.tvTipoBajaNa) as TextView
        val carga = view.findViewById(R.id.tvCargaBajaNa) as TextView
        val pasajeros = view.findViewById(R.id.tvPasajerosBajaNa) as TextView
        val eliminar = view.findViewById(R.id.btnEliminarNave) as Button

        fun bind(nave: NaveMostrar, context: Context, pos: Int, adaptadorNaves: AdaptadorNaves, fragBajaNaveViewModel: FragBajaNaveViewModel){
            matricula.text = nave.matricula
            Glide.with(requireNotNull(context)).load(nave.foto).into(foto)
            tipo.text = nave.tipo
            carga.text = nave.carga
            pasajeros.text = nave.pasajeros

            eliminar.setOnClickListener {
                AlertDialog.Builder(context)
                    .setTitle("Eliminar nave")
                    .setMessage("¿Deseas eliminar la nave ${nave.matricula}?")
                    .setPositiveButton("Si", DialogInterface.OnClickListener(function = { dialog: DialogInterface, which: Int ->
                        fragBajaNaveViewModel.eliminarNaveVM(nave.matricula)
                    }))
                    .setNegativeButton("No", ({ dialog: DialogInterface, which: Int ->
                        Toast.makeText(context,"Eliminacion cancelada", Toast.LENGTH_SHORT).show()
                    }))
                    .show()
            }



        }
    }
}