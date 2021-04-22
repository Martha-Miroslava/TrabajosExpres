package com.example.trabajosexpres

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class miAdaptadorLista1(contexto: Context) : BaseAdapter() {

    private val miContexto: Context

    init {
        miContexto = contexto;
    }

    //generar filas
    override fun getView(position: Int, p1: View?, viewGroup: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(miContexto)
        val listRowLayout = layoutInflater.inflate(R.layout.row_service, viewGroup, false)
        /*val txtHead = listRowLayout.findViewById<TextView>(R.id.TextEncabezado)
        val txtDescription = listRowLayout.findViewById<TextView>(R.id.textDescripcion)
        val image = listRowLayout.findViewById<ImageView>(R.id.ImageViewService)
        txtDescription.text = "Hola soy la fila # $position"
        txtHead.text = "Soy el encabezado #$position"
        Glide.with(miContexto)
            .load("https://cf.ltkcdn.net/gatos/images/std/236641-699x450-etapas-desarrollo-gatitos.jpg")
            .apply(RequestOptions().override(200, 200))
            .into(image)*/
        return listRowLayout
    }

    override fun getItem(position: Int): Any {
        return position.toLong()
    }

    //regresa el id de cada elemento de la lista
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //cuantas filas hay
    override fun getCount(): Int {
        return 10
    }
}