package com.example.congregationinfo.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.RecyclerView
import com.example.congregationinfo.data.StartData
import com.example.congregationinfo.databinding.StartRowBinding
import okhttp3.internal.parseCookie

class StartAdapter(var context: Context,items:List<StartData>) : RecyclerView.Adapter<StartAdapter.ViewHolder>() {
    private var startItems = mutableListOf<StartData>()
    init{
        startItems.addAll(items)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StartRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return startItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = startItems[position]
        holder.tvDate.text = listItem.date
        holder.tvTask.text = listItem.task


        if( holder.tvTask.text == "Gyülekezeti Feladatok"||holder.tvTask.text == "Szántóföldi feladatok") {
            holder.row.setBackgroundColor(Color.parseColor("#3B56D8"))
        }else holder.row.setBackgroundColor(Color.parseColor("#5b5959"))
    }

    inner class ViewHolder(binding: StartRowBinding) : RecyclerView.ViewHolder(binding.root){
        var tvDate = binding.tvReminderDate
        var tvTask = binding.tvReminderTask
        var row= binding.startRow

    }
}