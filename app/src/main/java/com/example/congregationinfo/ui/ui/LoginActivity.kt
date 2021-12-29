package com.example.congregationinfo.ui.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.congregationinfo.data.AppDatabase
import com.example.congregationinfo.data.CongregationDataRoom
import com.example.congregationinfo.data.Global
import com.example.congregationinfo.databinding.ActivityLoginBinding
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        thread {
            var congRoomAll: CongregationDataRoom
            try{
                congRoomAll =
                AppDatabase.getInstance(this@LoginActivity).congDao().getAllInfo().last()
            }catch(e: Exception){
                congRoomAll= CongregationDataRoom(null,"",0,"3197",Global.resultDate,Global.resultValues)
                AppDatabase.getInstance(this@LoginActivity).congDao().insertInfo(congRoomAll)
            }

            Global.name = congRoomAll.name
            Global.firstStartCounter = congRoomAll.firstStartCounter
            Global.HARDD_CODE = congRoomAll.hardCode
            Global.resultDate = congRoomAll.updateDate
            Global.resultValues=congRoomAll.resultValues


            runOnUiThread {
                if(Global.firstStartCounter==1) {
                    nextActivity(Intent(this@LoginActivity,StartActivity::class.java))
                }
                else{
                    binding.progressBarLogin.visibility=View.GONE
                    binding.btnLogin.visibility = View.VISIBLE
                    binding.textInputLayout.visibility = View.VISIBLE
                    binding.loginTextView.visibility = View.VISIBLE
                }
            }
        }

        binding.loginTextView.text="HARDD_CODE = 3197"

        binding.btnLogin.setOnClickListener {
            when {

                Global.HARDD_CODE == binding.etLogin.text.toString() -> {
                    thread{
                        val congRoom = CongregationDataRoom(null,"",1,"3197",Global.resultDate,Global.resultValues)
                        AppDatabase.getInstance(this@LoginActivity).congDao().deleteAll()
                        AppDatabase.getInstance(this@LoginActivity).congDao().insertInfo(congRoom)
                    }
                    Global.firstStartCounter = 1
                    nextActivity(Intent(this@LoginActivity,NameActivity::class.java))
                }

                Global.firstStartCounter > 10 -> {
                    binding.loginTextView.text="Túl sokszor adtál meg helytelen kódot. "
                    binding.btnLogin.visibility = View.GONE
                    binding.textInputLayout.visibility = View.GONE
                    binding.loginTextView.visibility = View.VISIBLE
                }

                else -> {
                    Global.firstStartCounter++
                    Toast.makeText(this, "Nem megfelelő kód!\n ",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun nextActivity(intent: Intent) {
        val activityToClose = this@LoginActivity
        //val intent = Intent(this@LoginActivity,StartActivity::class.java )
        startActivity(intent)
        activityToClose.finish()
    }
}