package com.example.congregationinfo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.congregationinfo.databinding.ActivityCongregationBinding


class CongregationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCongregationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCongregationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var IntentCount = intent.getIntExtra("intentArrayNext",0)
        buttonVisible(IntentCount)
        binding.congregationTextview.text = Global.DataArray[IntentCount]

        binding.next.setOnClickListener {
            /*HAGYD BENNE KÉSŐBB AZ ACTIVITYWEEKEND ACTIVITYWEEKDAY-HEZ EZ FOG KELLENI SZTEM
            changeActivity(IntentCount+1);*/
            IntentCount = IntentCount+1
            buttonVisible(IntentCount)
            binding.congregationTextview.text = Global.DataArray[IntentCount]
        }
        binding.previous.setOnClickListener {
            /* SZINTÉN HAGYD
            changeActivity(IntentCount-1);
            }*/
            IntentCount = IntentCount-1
            buttonVisible(IntentCount)
            binding.congregationTextview.text = Global.DataArray[IntentCount]
        }
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

    fun changeActivity(change: Int) {
        if(change > 0){
            val intent = Intent(this@CongregationActivity, CongregationActivity::class.java).apply {
                putExtra("intentArrayNext", change-1)
            }
            startActivity(intent)
        }
    }
}