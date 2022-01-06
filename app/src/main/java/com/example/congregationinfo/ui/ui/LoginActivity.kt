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
import com.google.firebase.auth.FirebaseAuth
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
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
                congRoomAll= CongregationDataRoom(null,"",1,0,Global.resultDate,Global.resultValues)
                AppDatabase.getInstance(this@LoginActivity).congDao().insertInfo(congRoomAll)
            }
            Global.name = congRoomAll.name
            Global.firstStartCounter = congRoomAll.firstStartCounter
            Global.counter = congRoomAll.counter
            Global.resultDate = congRoomAll.updateDate
            Global.resultValues=congRoomAll.resultValues

            runOnUiThread {
                if(Global.firstStartCounter==0) {
                    nextActivity(Intent(this@LoginActivity,StartActivity::class.java))
                }
                else{
                    binding.progressBarLogin.visibility=View.GONE
                    binding.btnLogin.visibility = View.VISIBLE
                    binding.textInputLayout.visibility = View.VISIBLE
                    //binding.loginTextView.visibility = View.VISIBLE
                    binding.textInputLayout2.visibility = View.VISIBLE
                    binding.btnRegistration.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun nextActivity(intent: Intent) {
        val activityToClose = this@LoginActivity
        startActivity(intent)
        activityToClose.finish()
    }

    private fun isFormValid(): Boolean{
      if(binding.etLoginName.text!!.isEmpty()){
            binding.etLoginName.error = "Ez a mező nem maradhat üres!"
            return false
        } else return true
    }

    fun loginClick(view: android.view.View) {
        if(!isFormValid()){
            return
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            binding.etLoginName.text.toString(), binding.etpassword.text.toString()
        ).addOnSuccessListener {
            if(it.user?.isEmailVerified!! == false) {
                binding.loginTextView.text= "Megerősítő linket küldtünk az email címedre.\n Kérjük erősítsd meg az email címedet, és utána próbáld újra a belépést!"
                binding.loginTextView.visibility= View.VISIBLE
                return@addOnSuccessListener
            }
            thread {
                   val congRoom = CongregationDataRoom(
                       null,
                       Global.name,
                       0,
                       0,
                       Global.resultDate,
                       Global.resultValues )
                   AppDatabase.getInstance(this@LoginActivity).congDao().deleteAll()
                   AppDatabase.getInstance(this@LoginActivity).congDao().insertInfo(congRoom)
               }
                Global.firstStartCounter = 0
                nextActivity(Intent(this@LoginActivity,NameActivity::class.java))
        }.addOnFailureListener {
            Toast.makeText(this@LoginActivity,"Login Error",Toast.LENGTH_LONG).show()
            Global.firstStartCounter++
            if (Global.firstStartCounter == 10){
                binding.textInputLayout.visibility=View.GONE
                binding.textInputLayout2.visibility=View.GONE
                binding.btnRegistration.visibility=View.GONE
                binding.btnLogin.visibility=View.GONE
                binding.loginTextView.visibility=View.VISIBLE
                binding.loginTextView.text = "Túl sokszor adtál meg halytelen kódot!"
            }
        }
    }

    fun registrationClick(view: android.view.View) {
        nextActivity(Intent(this@LoginActivity,RegistrationActivity::class.java))
    }
}