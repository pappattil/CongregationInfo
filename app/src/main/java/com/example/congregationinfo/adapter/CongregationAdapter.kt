package com.example.congregationinfo.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.congregationinfo.data.CongregationData
import com.example.congregationinfo.databinding.CongregationRowBinding


class CongregationAdapter(var context: Context, var resultItems: List<List<String>>?) : RecyclerView.Adapter<CongregationAdapter.ViewHolder>() {


    private var congItems = mutableListOf<CongregationData>()
    private var counter:Int
    init {

        addAll(resultItems)
        counter =0

    }

    fun addAll(resultItems: List<List<String>>?) {
        val columnSize = (resultItems?.size)?.minus(1)
        for (i in 0..columnSize!!) {
            val rowSize = (resultItems[i].size).minus(1)
            for (j in 0..rowSize) {
                congItems.add(CongregationData((resultItems[i][j])))
            }
        }
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

        if (listItem.value == ";") {
            holder.row.text = " "
        }
        else {
            holder.row.text = listItem.value

        }
        holder.row.setTextColor(Color.BLACK)

    }

    inner class ViewHolder(binding: CongregationRowBinding) : RecyclerView.ViewHolder(binding.root){
        var row = binding.tvRow

    }

}

