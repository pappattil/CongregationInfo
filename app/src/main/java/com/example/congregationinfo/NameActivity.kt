package com.example.congregationinfo

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import com.example.congregationinfo.databinding.ActivityNameBinding

class NameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textviewName.text = Editable.Factory.getInstance().newEditable(Global.Name)

        binding.save.setOnClickListener {
            if(binding.textviewName.text.isNotEmpty()){
                Global.Name = binding.textviewName.text.toString()
                val intent = Intent(this@NameActivity, ScrollingActivity::class.java)
                startActivity(intent)
            }
        }



    }
}