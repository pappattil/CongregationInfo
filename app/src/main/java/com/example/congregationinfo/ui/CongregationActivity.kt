package com.example.congregationinfo.ui


import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

        val congregationViewModel: CongregationViewModel by viewModels()

        congregationViewModel.getCongregationLiveData().observe(this,
            {congregationResult -> render(congregationResult)})

        congregationViewModel.getCongregationData()

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
/*
    fun globalToTextview(intentCount: Int): Int {
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
*/

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

    fun render(result: CongregationViewState){
        when(result){
            is inProgress ->{
                binding.congregationTextview.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }

            is congregationResponseSuccess -> {

                val columnSize = ( result.data.values?.size)?.minus(1)
                var text = ""

                for (i in 0..columnSize!!) {
                    val rowSize = (result.data.values[i].size).minus(1)
                    for (j in 0..rowSize) {
                        text = text + result.data.values[i][j] + "\n"

                    }
                }
                Global.DataArray = text.split(';').toTypedArray()
                binding.congregationTextview.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.congregationTextview.text = Global.DataArray[0]
                buttonVisible(0)
            }

            is congregationResponseError -> {
                binding.congregationTextview.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.congregationTextview.text=result.exceptionMSG
                binding.previous.visibility= View.GONE
                binding.next.visibility= View.GONE
            }

        }
    }

}