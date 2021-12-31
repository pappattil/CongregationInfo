package com.example.congregationinfo.ui.ui



import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.congregationinfo.data.Global
import com.example.congregationinfo.data.StartData
import com.example.congregationinfo.databinding.ActivityStartBinding
import com.example.congregationinfo.ui.adapter.StartAdapter


class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    private lateinit var startAdapter: StartAdapter
    //@SuppressLint("SetTextI18n")
    var startCongList = listOf("")
    var startMinistryList = listOf("")

    var startDate = " "
    var spStartCongList : List<StartData> = emptyList()
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
        //var startList : List<String>
        if (Global.resultDate != "") {
            binding.tvDataStatus.text = "Adatbázis frissítve: " + Global.resultDate
            var k = 0
            val columnSize = (Global.resultValues.size).minus(1)
            for (i in 0..columnSize) {
                val rowSize = (Global.resultValues[i].size).minus(1)
                for (j in 0..rowSize) {
                    if (Global.resultValues[i][j] == "/n") k++
                    if (k < 1) startCongList =startCongList+ listOf(Global.resultValues[i][j])
                    else startMinistryList =startMinistryList + listOf(Global.resultValues[i][j])

                }
            }
            for (i in 0..(startCongList.size).minus(2)) {

                if (startCongList[i] == ";") {
                    startDate = startCongList[i+1]
                }
                if (startCongList[i] == Global.name) {
                    spStartCongList = spStartCongList+listOf(StartData(null,startDate,startCongList[i-1].dropLast(1)))
                }
            }
            for (i in 0..startMinistryList.lastIndex-1 ) {
                if (startMinistryList[i] == Global.name) {
                    startDate = startMinistryList[i-4]
                    spStartCongList = spStartCongList+ listOf(StartData(null,startDate,startMinistryList[i-3]+", Szántóföldi összejövetel"))
                }
            }

            startAdapter = StartAdapter(this, spStartCongList)
            binding.rvStart.adapter = startAdapter

            binding.rvStart.visibility = View.VISIBLE
            binding.textView.visibility = View.VISIBLE
            binding.textView2.visibility = View.VISIBLE

        }
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