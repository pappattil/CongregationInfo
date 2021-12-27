package com.example.congregationinfo.ui.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.congregationinfo.data.MinistryData
import com.example.congregationinfo.databinding.ActivityMinistryBinding
import com.example.congregationinfo.ui.adapter.MinistryAdapter
import java.util.*

class MinistryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMinistryBinding
    private lateinit var ministryAdapter: MinistryAdapter

    private var ministryList: Array<Array<String>> = arrayOf(
        arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMinistryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val congregationViewModel: CongregationViewModel by viewModels()

        congregationViewModel.getCongregationLiveData().observe(this,
            { congregationResult -> render(congregationResult) })

        congregationViewModel.getCongregationData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun render(result: CongregationViewState) {
        when (result) {
            is InProgress -> {
                binding.pbMinistry.visibility = View.VISIBLE
            }

            is CongregationResponseSuccess -> {
                var k=0
                val columnSize = (result.data.values?.size)?.minus(1)
                for (i in 0..columnSize!!) {
                    val rowSize = (result.data.values[i].size).minus(1)
                    for (j in 0..rowSize) {
                        ministryList[k] += arrayOf(result.data.values[i][j])
                        if(result.data.values[i][j] == "/n")k++
                    }
                }
                var spCongList = listOf<MinistryData>()

                for(i in 1..11 step 5){
                    println("XXXXX____"+MinistryData(
                        null,
                        ministryList[1][i],
                        ministryList[1][i+1],
                        ministryList[1][i+2],
                        ministryList[1][i+3],
                        ministryList[1][i+4]
                    ))
                    spCongList = spCongList + listOf(
                        MinistryData(
                            null,
                            ministryList[1][i],
                            ministryList[1][i+1],
                            ministryList[1][i+2],
                            ministryList[1][i+3],
                            ministryList[1][i+4]
                        )
                    )
                }

                binding.pbMinistry.visibility = View.GONE
                binding.rvMinistry.visibility = View.VISIBLE
                 //for (i in 348..congList.last().toInt() ) spCongList = spCongList + listOf(congList[i])

                ministryAdapter = MinistryAdapter(this, spCongList)
                binding.rvMinistry.adapter = ministryAdapter
                //viewChange()
            }

            is CongregationResponseError -> {
                binding.rvMinistry.visibility = View.GONE
                binding.btnBack.visibility = View.GONE

                binding.pbMinistry.visibility = View.GONE
                if (result.exceptionMSG == "timeout") {
                    val activityToClose = this@MinistryActivity
                    val intent = Intent(this@MinistryActivity, MinistryActivity::class.java)
                    startActivity(intent)
                    activityToClose.finish()
                    Toast.makeText(
                        this@MinistryActivity,
                        "Rendszerüzenet:\ntimeout" + result.exceptionMSG + "\n",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val activityToClose = this@MinistryActivity
                    val intent = Intent(this@MinistryActivity, StartActivity::class.java)
                    startActivity(intent)
                    activityToClose.finish()
                    Toast.makeText(
                        this@MinistryActivity,
                        "Ellenőrizd az internetkapcsolatot!\n\nRendszerüzenet:\n" + result.exceptionMSG,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
/*
    @SuppressLint("SimpleDateFormat")
    private fun viewChange() {
        var start = 0
        var end = 0
        var spCongList = listOf("")

        if (viewCounter == 0) {
            var dataDate = congList[0]
            dataDate = dataDate.replace(".", "")
            val dateFormat = SimpleDateFormat("MMdd")
            val currentDate = dateFormat.format(Date())
            viewCounter = if (dataDate < currentDate) 2 else 1
        }

        when (viewCounter) {
            1 -> {
                binding.next.visibility = View.VISIBLE
                binding.previous.visibility = View.GONE
                start = 2
                end = 55
            }
            2 -> {
                binding.previous.visibility = View.VISIBLE
                binding.previous.visibility = View.VISIBLE
                start = 56
                end = 89
            }
            3 -> {
                start = 90
                end = 140
            }
            4 -> {
                start = 141
                end = 174
            }
            5 -> {
                start = 175
                end = 228
            }
            6 -> {
                start = 229
                end = 262
            }
            7 -> {
                binding.next.visibility = View.VISIBLE
                start = 263
                end = 315
            }
            8 -> {
                binding.next.visibility = View.GONE
                binding.previous.visibility = View.VISIBLE
                start = 316
                end = congList.lastIndex
            }
        }
        for (i in start..end) spCongList = spCongList + listOf(congList[i])

        congAdapter = CongregationAdapter(this, spCongList)
        binding.congregationRecyclerview.adapter = congAdapter

    }
*/
}
