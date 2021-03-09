package com.example.congregationinfo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.congregationinfo.databinding.ActivityCongregationBinding

class CongregationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCongregationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCongregationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.congregationTextview.text = Global.DataArray[0]
    }


}