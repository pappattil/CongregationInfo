package com.example.congregationinfo.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.congregationinfo.data.Global
import com.example.congregationinfo.databinding.CongregationRowBinding


class CongregationAdapter(var context: Context, resultItems: List<String>) : RecyclerView.Adapter<CongregationAdapter.ViewHolder>() {


    private var congItems = mutableListOf<String>()

    init {
        congItems.addAll(resultItems.toMutableList())


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CongregationRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return congItems.size
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = congItems[position]

        if (listItem == ";") {
            holder.row.text = " "
        }
        else {
            holder.row.text = listItem

        }
        if (Global.Name != "" && holder.row.text.toString().lowercase() == Global.Name.lowercase()) holder.row.setTextColor(
            Color.CYAN)
        else holder.row.setTextColor(
            Color.WHITE)
       /*
        when(holder.row.text){
            "" -> holder.row.setBackgroundColor()
        }

        */
    }


    inner class ViewHolder(binding: CongregationRowBinding) : RecyclerView.ViewHolder(binding.root){
        var row = binding.tvRow

    }

}

