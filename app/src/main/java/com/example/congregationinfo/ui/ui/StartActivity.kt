package com.example.congregationinfo.ui.ui



import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.congregationinfo.data.Global
import com.example.congregationinfo.databinding.ActivityStartBinding


class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    //@SuppressLint("SetTextI18n")
    var startCongList = listOf("")
    var startMinistryList = listOf("")

    var date=""
    var spStartCongList = startCongList
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
            binding.tvDataStatus.text = "Adatbázis frissítve:\n" + Global.resultDate

          
            var k = 0
            val columnSize = (Global.resultValues.size).minus(1)
            for (i in 0..columnSize) {
                val rowSize = (Global.resultValues[i].size).minus(1)
                for (j in 0..rowSize) {
                    if (Global.resultValues[i][j] == "/n") k++
                    if (k < 1) startCongList = listOf(Global.resultValues[i][j])
                    else startMinistryList = listOf(Global.resultValues[i][j])

                }
            }
            for (i in 0..(startCongList.size).minus(1)) {
                if (startCongList[i] == ";") date = startCongList[i + 1]
                if (startCongList[i] == Global.name) {
                    spStartCongList = listOf(date)
                    spStartCongList = listOf(startCongList[i - 1])
                }
            }
            for (i in 0..(startMinistryList.size).minus(1) step 5) {
                date = startMinistryList[i] + " " + startMinistryList[i + 1]
                if (startMinistryList[i + 5] == Global.name) {
                    spStartCongList = listOf(date)
                    spStartCongList = listOf(startMinistryList[i + 1] + "Szántóföldi összejövetel")
                }
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
}
