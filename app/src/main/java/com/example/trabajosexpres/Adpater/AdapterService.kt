package com.example.trabajosexpres.Adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.trabajosexpres.Model.Service
import com.example.trabajosexpres.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class AdapterService (contexto: Context, listService:List<Service>) : BaseAdapter() {

    private val contexto: Context
    private val listService: List<Service>

    init {
        this.contexto = contexto;
        this.listService = listService
    }

    override fun getView(position: Int, p1: View?, viewGroup: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(contexto)
        val listRowLayout = layoutInflater.inflate(R.layout.row_service, viewGroup, false)
        val txtName = listRowLayout.findViewById<TextView>(R.id.TextViewNameService)
        val txtSlogan = listRowLayout.findViewById<TextView>(R.id.TextViewSlogan)
        val image = listRowLayout.findViewById<ImageView>(R.id.ImageViewService)
        txtName.text = listService.get(position).name
        txtSlogan.text = listService.get(position).slogan
        val numberIndex:Int = position%2
        if(numberIndex!=0){
            image.setImageResource(R.drawable.carpintero_1);
        }else{
            image.setImageResource(R.drawable.plomero);
        }
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
        return listService.size
    }
}