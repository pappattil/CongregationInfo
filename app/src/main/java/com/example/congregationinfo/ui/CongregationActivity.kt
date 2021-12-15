package com.example.congregationinfo.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.congregationinfo.data.CongregationData
import com.example.congregationinfo.data.Global
import com.example.congregationinfo.databinding.ActivityCongregationBinding
import java.text.SimpleDateFormat
import java.util.*


class CongregationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCongregationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCongregationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var intentCount = intent.getIntExtra("intentArrayNext", 0)
        //binding.progressBar.visibility = View.VISIBLE

        val congregationViewModel: CongregationViewModel by viewModels()
        congregationViewModel.getCongregationData().observe(this,
            object : Observer<CongregationData> {
                override fun onChanged(congregationData: CongregationData?) {
                    val columnSize = (congregationData?.values?.size)?.minus(1)
                    var text = ""

                    for (i in 0..columnSize!!) {
                        val rowSize = (congregationData.values[i].size).minus(1)
                        for (j in 0..rowSize) {
                            text = text + congregationData.values[i][j] + "\n"

                        }
                    }
                    Global.DataArray = text.split(';').toTypedArray()
                    binding.congregationTextview.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    intentCount = globalToTextview(intentCount)
                }

            })

            binding.next.setOnClickListener {
                intentCount = intentCount + 1
                buttonVisible(intentCount)
                binding.congregationTextview.text = Global.DataArray[intentCount]
            }
            binding.previous.setOnClickListener {
                intentCount = intentCount - 1
                buttonVisible(intentCount)
                binding.congregationTextview.text = Global.DataArray[intentCount]
            }

    }

    private fun globalToTextview(intentCount: Int): Int {
        var intentCount1 = intentCount
        if (intentCount1 == 0) {
            var dataDate = Global.DataArray[intentCount1].substring(0, 6)
            dataDate = dataDate.replace(".", "")

            val dateFormat = SimpleDateFormat("MMdd")
            val currentDate = dateFormat.format(Date())

            if (dataDate < currentDate) {
                intentCount1 = intentCount1 + 1
            }
        }

        buttonVisible(intentCount1)
        binding.congregationTextview.text = Global.DataArray[intentCount1]
        return intentCount1
    }


    fun buttonVisible(actual: Int) {
        if(actual == 0){
            binding.previous.visibility= View.GONE
            binding.next.visibility= View.VISIBLE
        }
        else if(Global.DataArray.count() == actual+2){
            binding.previous.visibility= View.VISIBLE
            binding.next.visibility= View.GONE
        }
        else{
            binding.previous.visibility= View.VISIBLE
            binding.next.visibility= View.VISIBLE
        }
    }

}