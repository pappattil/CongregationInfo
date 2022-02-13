package com.example.congregationinfo.ui.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.congregationinfo.data.*
import com.example.congregationinfo.databinding.ActivityMinistryBinding
import com.example.congregationinfo.ui.adapter.MinistryAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class MinistryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMinistryBinding
    private lateinit var ministryAdapter: MinistryAdapter

    private var ministryList: Array<Array<String>> = arrayOf(
        arrayOf(""), arrayOf(""), arrayOf(""), arrayOf(""), arrayOf("")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMinistryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val congregationViewModel: CongregationViewModel by viewModels()

        congregationViewModel.getCongregationLiveData().observe(this,
            { congregationResult -> render(congregationResult) })

        congregationViewModel.getCongregationData()
    }

    @SuppressLint("NotifyDataSetChanged", "SimpleDateFormat")
    private fun render(result: CongregationViewState) {
        when (result) {
            is InProgress -> {
                binding.pbMinistry.visibility = View.VISIBLE
            }
            is CongregationResponseSuccess -> {
                var firstSunday = result.data.values!![0][1]
                val columnSize = (result.data.values.size).minus(1)
                var i = 0
                while (i < columnSize) {
                    val rowSize = (result.data.values[i].size).minus(1)
                    for (j in 0..rowSize) {
                        if (result.data.values[i][j] == ";") {
                            firstSunday = result.data.values[i][j + 1]
                            i = columnSize
                        }
                    }
                    i++
                }
                if (dateExam(firstSunday) > 7) {
                    newMinistryActivity()
                } else {
                    val date: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
                    Global.resultDate = date
                    Global.resultValues = result.data.values
                    thread {
                        val congRoom = CongregationDataRoom(
                            null,
                            Global.name,
                            Global.firstStartCounter,
                            Global.counter,
                            Global.resultDate,
                            Global.resultValues
                        )
                        AppDatabase.getInstance(this@MinistryActivity).congDao().deleteAll()
                        AppDatabase.getInstance(this@MinistryActivity).congDao()
                            .insertInfo(congRoom)
                    }
                    dataHandler(Global.resultValues)
                }
            }
            is CongregationResponseError -> {
                binding.rvMinistry.visibility = View.GONE
                binding.btnBack.visibility = View.GONE
                binding.pbMinistry.visibility = View.GONE

                if (result.exceptionMSG == "timeout") {
                    Toast.makeText(
                        this@MinistryActivity,
                        "Rendszerüzenet:\ntimeout" + result.exceptionMSG + "\n",
                        Toast.LENGTH_LONG
                    ).show()
                    newMinistryActivity()
                } else {
                    Toast.makeText(
                        this@MinistryActivity,
                        "Ellenőrizd az internetkapcsolatot!\n\nLegutóbbi frissítés::\n" + Global.resultDate,
                        Toast.LENGTH_LONG
                    ).show()
                    if (Global.resultValues.isNotEmpty()) dataHandler(Global.resultValues)
                    else newStartActivity()
                }
            }
        }
    }

    private fun newMinistryActivity() {
        val activityToClose = this@MinistryActivity
        val intent = Intent(this@MinistryActivity, MinistryActivity::class.java)
        startActivity(intent)
        activityToClose.finish()
    }

    private fun newStartActivity() {
        val activityToClose = this@MinistryActivity
        val intent = Intent(this@MinistryActivity, StartActivity::class.java)
        startActivity(intent)
        activityToClose.finishAffinity()
    }

    private fun dataHandler(values: List<List<String>>) {
        var k = 0
        val columnSize = (values.size).minus(1)
        for (i in 0..columnSize) {
            val rowSize = (values[i].size).minus(1)
            for (j in 0..rowSize) {
                ministryList[k] += arrayOf(values[i][j])
                if (values[i][j] == "/n") k++
            }
        }
        var spMinistryList = listOf<MinistryData>()
        val ministryLast = ministryList[1].lastIndex / 5 * 5
        for (i in 1..ministryLast step 5) {
            spMinistryList = spMinistryList + listOf(
                MinistryData(
                    null,
                    ministryList[1][i],
                    ministryList[1][i + 1],
                    ministryList[1][i + 3],
                    ministryList[1][i + 2],
                    ministryList[1][i + 4]
                )
            )
        }

        binding.pbMinistry.visibility = View.GONE
        binding.rvMinistry.visibility = View.VISIBLE

        ministryAdapter = MinistryAdapter(this, spMinistryList)
        binding.rvMinistry.adapter = ministryAdapter
    }

    private fun dateExam(value: String): Int {
        var dataDate = value
        dataDate = dataDate.replace(".", "")
        val dateFormat = SimpleDateFormat("MMdd")
        val currentDate = dateFormat.format(Date())
        return (currentDate.toInt() - dataDate.toInt())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        newStartActivity()
    }
}
