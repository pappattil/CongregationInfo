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

        binding.btnReg.setOnClickListener {

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                binding.etRegEmail.toString(),binding.etRegPassword.toString()
            ).addOnSuccessListener {
                it.user?.sendEmailVerification()
                Toast.makeText(this,
                    "Sikeres Regisztráció!",
                    Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this,
                    "A regisztráció nem sikerült!",
                    Toast.LENGTH_LONG).show()
            }

            nextActivity(Intent(this@RegistrationActivity,LoginActivity::class.java))
        }

    }
    private fun nextActivity(intent: Intent) {
        val activityToClose = this@RegistrationActivity
        startActivity(intent)
        activityToClose.finish()
    }

}