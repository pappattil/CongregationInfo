package com.example.congregationinfo.ui


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.congregationinfo.data.Global
import com.example.congregationinfo.databinding.ActivityCongregationBinding


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
                intentCount += 1
                buttonVisible(intentCount)
                binding.congregationTextview.text = Global.DataArray[intentCount]
            }
            binding.previous.setOnClickListener {
                intentCount -= 1
                buttonVisible(intentCount)
                binding.congregationTextview.text = Global.DataArray[intentCount]
            }

    }

    private fun buttonVisible(actual: Int) {
        when {
            actual == 0 -> {
                binding.previous.visibility= View.GONE
                binding.next.visibility= View.VISIBLE
            }
            Global.DataArray.count() == actual+2 -> {
                binding.previous.visibility= View.VISIBLE
                binding.next.visibility= View.GONE
            }
            else -> {
                binding.previous.visibility= View.VISIBLE
                binding.next.visibility= View.VISIBLE
            }
        }
    }

    private fun render(result: CongregationViewState){
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
                binding.previous.visibility= View.GONE
                binding.next.visibility= View.GONE
                binding.congregationTextview.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                    if(result.exceptionMSG=="timeout"){
                        val activityToClose =  this@CongregationActivity
                        val intent = Intent(this@CongregationActivity, CongregationActivity::class.java).apply {
                            putExtra("intentArrayNext", 0)
                        }
                        startActivity(intent)
                        activityToClose.finish()
                        Toast.makeText(this@CongregationActivity,"Rendszerüzenet:\ntimeout"+result.exceptionMSG, Toast.LENGTH_LONG).show()

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

}