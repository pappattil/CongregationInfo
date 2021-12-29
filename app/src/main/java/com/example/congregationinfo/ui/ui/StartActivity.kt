package com.example.congregationinfo.ui.ui



import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.congregationinfo.data.Global
import com.example.congregationinfo.databinding.ActivityStartBinding


class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        */
        if (Global.resultDate != "") binding.tvDataStatus.text="Adatok frissítve: "+Global.resultDate

        binding.btnCongregation.setOnClickListener {
            val intent = Intent(this@StartActivity, CongregationActivity::class.java)
            startActivity(intent)
        }

        binding.btnName.setOnClickListener {
            val intent = Intent(this@StartActivity, NameActivity::class.java)
            startActivity(intent)

        }

        binding.btnMinisry.setOnClickListener {
            val intent = Intent(this@StartActivity, MinistryActivity::class.java)
            startActivity(intent)
        }
    }
}
