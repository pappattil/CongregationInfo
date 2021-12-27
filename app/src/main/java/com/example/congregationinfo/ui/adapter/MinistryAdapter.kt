package com.example.congregationinfo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.congregationinfo.data.MinistryData
import com.example.congregationinfo.databinding.MinistryRowBinding


class  MinistryAdapter(var context: Context, resultItems: List<MinistryData>) : RecyclerView.Adapter<MinistryAdapter.ViewHolder>() {


        private var ministryItems = mutableListOf<MinistryData>()

        init {
            ministryItems.addAll(resultItems)


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = MinistryRowBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return ViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return ministryItems.size
        }



        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val listItem = ministryItems[position]
            holder.tvDate.text = listItem.date
            holder.tvConductor.text = listItem.conductor
            holder.tvGroup.text = listItem.group
            holder.tvHour.text = listItem.time
            holder.tvPlace.text = listItem.place
        }


        inner class ViewHolder(binding: MinistryRowBinding) : RecyclerView.ViewHolder(binding.root){
            var tvDate = binding.tvDate
            var tvConductor = binding.tvConductor
            var tvHour = binding.tvHour
            var tvPlace = binding.tvPlace
            var tvGroup = binding.tvGroup

        }

    }


