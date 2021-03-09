package com.example.congregationinfo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.congregationinfo.databinding.ActivityWeekdayBinding

class WeekdayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeekdayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeekdayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.textView2.text = Global.DataArray[1]
    }


}