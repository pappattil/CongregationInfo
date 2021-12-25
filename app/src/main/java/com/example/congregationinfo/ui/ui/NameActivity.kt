package com.example.congregationinfo.ui.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import com.example.congregationinfo.data.AppDatabase
import com.example.congregationinfo.data.CongregationDataRoom
import com.example.congregationinfo.data.Global
import com.example.congregationinfo.databinding.ActivityNameBinding
import kotlin.concurrent.thread

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
                thread{
                    val congRoom = CongregationDataRoom(null,Global.Name,Global.firstStartCounter,Global.HARDD_CODE)
                    AppDatabase.getInstance(this@NameActivity).congDao().deleteAll()
                    AppDatabase.getInstance(this@NameActivity).congDao().insertInfo(congRoom)

                }
                val activityToClose = this@NameActivity
                //val intent = Intent(this@LoginActivity,StartActivity::class.java )
                val intent = Intent(this@NameActivity, StartActivity::class.java)
                startActivity(intent)
                activityToClose.finish()


            }
        }



    }
}