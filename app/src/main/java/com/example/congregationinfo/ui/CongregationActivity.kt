package com.example.congregationinfo.ui


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.congregationinfo.adapter.CongregationAdapter
import com.example.congregationinfo.data.CongregationData
import com.example.congregationinfo.data.Global
import com.example.congregationinfo.databinding.ActivityCongregationBinding


class CongregationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCongregationBinding
    private lateinit var congAdapter: CongregationAdapter
    private lateinit var  values : List<List<String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCongregationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val congregationViewModel: CongregationViewModel by viewModels()

        congregationViewModel.getCongregationLiveData().observe(this,
            {congregationResult -> render(congregationResult)})

        congregationViewModel.getCongregationData()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun render(result: CongregationViewState){
        when(result){
            is inProgress ->{
                binding.congregationTextview.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }

            is congregationResponseSuccess -> {

                congAdapter = CongregationAdapter(this,result.data.values)
                binding.congregationRecyclerview.adapter = congAdapter
                binding.congregationTextview.visibility = View.GONE
                binding.progressBar.visibility = View.GONE

            }

            is congregationResponseError -> {
                binding.previous.visibility= View.GONE
                binding.next.visibility= View.GONE
                binding.congregationTextview.visibility = View.VISIBLE
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

}