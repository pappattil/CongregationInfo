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


    private var congList: Array<Array<String>> = arrayOf(
    arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""))

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

                var k=0
                val columnSize = (result.data.values?.size)?.minus(1)
                for (i in 0..columnSize!!) {
                    val rowSize = (result.data.values[i].size).minus(1)
                    for (j in 0..rowSize) {
                        congList[k] += arrayOf(result.data.values[i][j])
                        if(result.data.values[i][j] == ";")k++
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
                    //Toast.makeText(this@CongregationActivity,"Rendszerüzenet:\ntimeout"+result.exceptionMSG+"\n", Toast.LENGTH_LONG).show()
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

        var spCongList= listOf("")

        if (viewCounter==0){
            var dataDate = congList[1][1]
            dataDate = dataDate.replace(".","")
            val dateFormat = SimpleDateFormat("MMdd")
            val currentDate = dateFormat.format(Date())
            viewCounter = if(dataDate < currentDate) 2 else 1
        }

        when (viewCounter) {
            1 -> {
                binding.next.visibility= View.VISIBLE
                binding.previous.visibility= View.GONE

            }
            2 ->{
                binding.previous.visibility= View.VISIBLE
                binding.previous.visibility= View.VISIBLE
            }
            7 ->{
                binding.next.visibility= View.VISIBLE
            }
            8 ->{
                binding.next.visibility= View.GONE
                binding.previous.visibility= View.VISIBLE
            }
        }

        for(i in 0..congList[viewCounter].size.minus(1)) spCongList = spCongList + congList[viewCounter][i]


        congAdapter = CongregationAdapter(this,spCongList)
        binding.congregationRecyclerview.adapter = congAdapter

    }
}