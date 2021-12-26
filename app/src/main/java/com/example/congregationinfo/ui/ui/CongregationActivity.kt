package com.example.congregationinfo.ui.ui


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.congregationinfo.databinding.ActivityCongregationBinding
import com.example.congregationinfo.ui.adapter.CongregationAdapter
import java.text.SimpleDateFormat
import java.util.*


class CongregationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCongregationBinding
    private lateinit var congAdapter: CongregationAdapter
    private var congList=listOf("")
    private var viewCounter=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCongregationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val congregationViewModel: CongregationViewModel by viewModels()


        congregationViewModel.getCongregationLiveData().observe(this,
            {congregationResult -> render(congregationResult)})

        congregationViewModel.getCongregationData()

        binding.next.setOnClickListener {
            viewCounter ++
            viewChange()
        }

        binding.previous.setOnClickListener {
            viewCounter--
            viewChange()
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun render(result: CongregationViewState){
        when(result){
            is InProgress ->{

                binding.progressBar.visibility = View.VISIBLE
            }

            is CongregationResponseSuccess -> {
                val columnSize = (result.data.values?.size)?.minus(1)
                for (i in 0..columnSize!!) {
                    val rowSize = (result.data.values[i].size).minus(1)
                    for (j in 0..rowSize) {
                        congList = congList+listOf(result.data.values[i][j])
                    }
                }



                binding.progressBar.visibility = View.GONE
                binding.next.visibility = View.VISIBLE
                viewChange()


            }

            is CongregationResponseError -> {
                binding.previous.visibility= View.GONE
                binding.next.visibility= View.GONE

                binding.progressBar.visibility = View.GONE
                    if(result.exceptionMSG=="timeout"){
                        val activityToClose =  this@CongregationActivity
                        val intent = Intent(this@CongregationActivity, CongregationActivity::class.java)
                        startActivity(intent)
                        activityToClose.finish()
                        Toast.makeText(this@CongregationActivity,"Rendszerüzenet:\ntimeout"+result.exceptionMSG+"\n", Toast.LENGTH_LONG).show()

                    }else {
                        val activityToClose =  this@CongregationActivity
                        val intent = Intent(this@CongregationActivity, StartActivity::class.java)
                        startActivity(intent)
                        activityToClose.finish()
                        Toast.makeText(this@CongregationActivity,"Ellenőrizd az internetkapcsolatot!\n\nRendszerüzenet:\n"+result.exceptionMSG, Toast.LENGTH_LONG).show()

                    }

            }

        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun viewChange() {
        var start=0
        var end=0
        var spCongList= listOf("")

        if (viewCounter==0){
            var dataDate = congList[0]
            dataDate = dataDate.replace(".","")
            val dateFormat = SimpleDateFormat("MMdd")
            val currentDate = dateFormat.format(Date())
            if(dataDate < currentDate) viewCounter =2
        }

        when (viewCounter) {
            1 -> {
                binding.next.visibility= View.VISIBLE
                binding.previous.visibility= View.GONE
                start=2
                end=55
            }
            2 ->{
                binding.previous.visibility= View.VISIBLE
                binding.previous.visibility= View.VISIBLE
                start=56
                end=89
            }
            3 ->{
                start=90
                end=140
            }
            4 ->{
                start=141
                end=174
            }
            5 ->{
                start=175
                end=228
            }
            6 ->{
                start=229
                end=262
            }
            7 ->{
                binding.next.visibility= View.VISIBLE
                start=263
                end=315
            }
            8 ->{
                binding.next.visibility= View.GONE
                binding.previous.visibility= View.VISIBLE
                start=316
                end=congList.lastIndex
            }
        }
        for(i in start..end) spCongList = spCongList + listOf(congList[i])

        congAdapter = CongregationAdapter(this,spCongList)
        binding.congregationRecyclerview.adapter = congAdapter

    }
}