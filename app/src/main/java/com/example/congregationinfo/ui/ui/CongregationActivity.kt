package com.example.congregationinfo.ui.ui


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.congregationinfo.data.AppDatabase
import com.example.congregationinfo.data.CongregationDataRoom
import com.example.congregationinfo.data.Global
import com.example.congregationinfo.databinding.ActivityCongregationBinding
import com.example.congregationinfo.ui.adapter.CongregationAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread


class CongregationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCongregationBinding
    private lateinit var congAdapter: CongregationAdapter


    private var congList: Array<Array<String>> = arrayOf(
    arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""),arrayOf(""))

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

    @SuppressLint("NotifyDataSetChanged", "SimpleDateFormat")
    private fun render(result: CongregationViewState){
        when(result){
            is InProgress ->{
                binding.progressBar.visibility = View.VISIBLE
            }

            is CongregationResponseSuccess -> {
                val date: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
                Global.resultDate = date
                Global.resultValues = result.data.values!!
                    thread{
                        val congRoom = CongregationDataRoom(null,
                            Global.name,
                            Global.firstStartCounter,
                            Global.counter,
                            Global.resultDate,
                            Global.resultValues
                        )
                        AppDatabase.getInstance(this@CongregationActivity).congDao().deleteAll()
                        AppDatabase.getInstance(this@CongregationActivity).congDao().insertInfo(congRoom)
                    }

               dataHandler(Global.resultValues)

            }

            is CongregationResponseError -> {
                //binding.previous.visibility= View.GONE
                //binding.next.visibility= View.GONE
                binding.progressBar.visibility = View.GONE
                //newStartActivity()
                if (result.exceptionMSG == "timeout") {
                    if(Global.counter < 4){
                        Global.counter++
                        newCongregationActivity()
                    }
                    Toast.makeText(
                        this@CongregationActivity,
                        "Rendszerüzenet:\ntimeout" + result.exceptionMSG + "\n",
                        Toast.LENGTH_LONG
                    ).show()
                    newStartActivity()
                }else{
                    Toast.makeText(
                        this@CongregationActivity,
                        "Ellenőrizd az internetkapcsolatot!\n\nLegutóbbi frissítés::\n" + Global.resultDate,
                        Toast.LENGTH_LONG
                    ).show()
                    if(Global.resultValues.isNotEmpty())dataHandler(Global.resultValues)
                    else newStartActivity()

                }
                dataHandler(Global.resultValues)
            }
        }
    }

    private fun dataHandler(values: List<List<String>>) {
        var pageSeparator = 0
        val columnSize = (values.size).minus(1)
        for (i in 0..columnSize) {
            val rowSize = (values[i].size).minus(1)
            for (j in 0..rowSize) {
                congList[pageSeparator] += arrayOf(values[i][j])
                if (values[i][j] == ";") pageSeparator++
            }
        }

        binding.progressBar.visibility = View.GONE
        binding.next.visibility = View.VISIBLE
        viewChange()
    }

    @SuppressLint("SimpleDateFormat")
    private fun viewChange() {
        var spCongList= listOf("")
        if (viewCounter==0) viewCounter = if(dateExam(congList[1][1])) 2 else 1
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
    private fun dateExam(value:String):Boolean{
        var dataDate=value
        dataDate = dataDate.replace(".","")
        val dateFormat = SimpleDateFormat("MMdd")
        val currentDate = dateFormat.format(Date())
        return if(dataDate < currentDate) true else false
    }
    private fun newStartActivity() {
        val activityToClose = this@CongregationActivity
        val intent = Intent(this@CongregationActivity, StartActivity::class.java)
        startActivity(intent)
        activityToClose.finishAffinity()
    }
    private fun newCongregationActivity() {
        val activityToClose = this@CongregationActivity
        val intent = Intent(this@CongregationActivity, CongregationActivity::class.java)
        startActivity(intent)
        activityToClose.finish()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        newStartActivity()
    }
}