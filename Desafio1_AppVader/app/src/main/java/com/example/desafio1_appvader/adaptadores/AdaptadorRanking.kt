package com.example.desafio1_appvader.adaptadores

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.provider.CalendarContract.Colors
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafio1_appvader.R
import com.example.desafio1_appvader.api.MainViewModel
import com.example.desafio1_appvader.modelo.usuario.Usuario
import com.example.desafio1_appvader.ventanas.FragRankingViewModel
import com.example.desafio1_appvader.ventanas.admin.FragBajaPilotoViewModel

class AdaptadorRanking (var usuarios: ArrayList<Usuario>, var context: Context, var fragRankingViewModel: FragRankingViewModel, var mainViewModel: MainViewModel) : RecyclerView.Adapter<AdaptadorRanking.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = usuarios.get(position)
        holder.bind(item, context, position, this, fragRankingViewModel, mainViewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card_bajausuario, parent, false)
        val viewHolder = ViewHolder(vista)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return usuarios.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val posicion = view.findViewById(R.id.tvPosicionRanking) as TextView

        val nombreUsuario = view.findViewById(R.id.tvUsuarioBajaUs) as TextView
        val edad = view.findViewById(R.id.tvEdadBajaUs) as TextView
        val exp = view.findViewById(R.id.tvExpBajaUs) as TextView
        val foto = view.findViewById(R.id.ivBajaUs) as ImageView
        val eliminar = view.findViewById(R.id.btnEliminarUsuario) as Button

        fun bind(user: Usuario, context: Context, pos: Int, adaptadorRanking: AdaptadorRanking, fragRankingViewModel: FragRankingViewModel, mainViewModel: MainViewModel){
            nombreUsuario.text = user.nombre
            edad.text = user.edad.toString()
            exp.text = user.experiencia.toString()
            Glide.with(requireNotNull(context)).load(user.foto).into(foto)
            posicion.text = (pos+1).toString()+"º"
            eliminar.visibility = View.INVISIBLE

            if (user.id == mainViewModel.usuarioLogeado.value!!.id){
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.ranking))
            }


        }
    }
}