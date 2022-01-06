package com.example.congregationinfo.ui.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.congregationinfo.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun nextActivity(intent: Intent) {
        val activityToClose = this@RegistrationActivity
        startActivity(intent)
        activityToClose.finish()
    }

    private fun isFormValid(): Boolean{
        if(binding.etRegEmail.text!!.isEmpty()){
            binding.etRegEmail.error = "Ez a mező nem maradhat üres!"
            return false
        } else return true
    }

    fun regClick(view: android.view.View) {
        if(!isFormValid()){
            return
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            binding.etRegEmail.editableText.toString(),binding.etRegPassword.editableText.toString()
        ).addOnSuccessListener {
            it.user?.sendEmailVerification()
            Toast.makeText(this@RegistrationActivity,"Sikeres Regisztráció!",Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(this@RegistrationActivity,"A regisztráció nem sikerült!\n"+it.message,Toast.LENGTH_LONG).show()
        }

        nextActivity(Intent(this@RegistrationActivity,LoginActivity::class.java))
    }

}

