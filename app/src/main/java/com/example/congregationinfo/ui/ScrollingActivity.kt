package com.example.congregationinfo.ui



import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.congregationinfo.R
import com.example.congregationinfo.data.CongregationData
import com.example.congregationinfo.data.Global
import com.example.congregationinfo.databinding.ActivityScrollingBinding
import com.google.android.material.appbar.CollapsingToolbarLayout


class ScrollingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        /*findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/
        val congregationViewModel: CongregationViewModel by viewModels()
        congregationViewModel.getCongregationData().observe(this,
            object: Observer<CongregationData> {
                override fun onChanged(congregationData: CongregationData?) {
                    val columnSize = (congregationData?.values?.size)?.minus(1)
                    var text = ""

                    for (i in 0..columnSize!!) {
                        val rowSize = (congregationData.values[i].size).minus(1)
                        for (j in 0..rowSize) {
                            text = text + congregationData.values[i][j] + "\n"

                        }
                    }
                    Global.DataArray = text.split(';').toTypedArray()
                }

            })


        binding.startBeosztas.setOnClickListener {
            val intent = Intent(this@ScrollingActivity, CongregationActivity::class.java).apply {
                putExtra("intentArrayNext", 0)
            }
            startActivity(intent)
        }

        /*val congregationInfoCall = googleAPI.getData("AIzaSyA4gXS5fL81WnHO98SiwsmBGiYb9V1ceQQ")
        // ---

           //congregationInfoCall.enqueue(object : Callback<CongregationData> {

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<CongregationData>,
                response: Response<CongregationData>
            ) {
                val congInfoResult = response.body()
                val columnSize = (congInfoResult?.values?.size)?.minus(1)
                var text =""

                for(i in 0..columnSize!!){
                    val rowSize = (congInfoResult.values[i].size).minus(1)
                    for(j in 0..rowSize){
                        text= text + congInfoResult.values[i][j] +"\n"

                    }
                }

                        thread {
                            AppDatabase.getInstance(this@ScrollingActivity).congDao().deleteAll()

                            for(i in 0..columnSize){
                                val rowSize = (congInfoResult.values[i].size).minus(1)
                                for(j in 0..rowSize) {
                            val item = CongregationDataRoom(
                                    null,
                                    congInfoResult.values[i][j]
                            )

                            AppDatabase.getInstance(this@ScrollingActivity).congDao().insertInfo(item)


                        } }
                        }

                Global.DataArray = text.split(';').toTypedArray()
               //binding.easyView.text = "Loading"
            }
            override fun onFailure(call: Call<CongregationData>, t: Throwable) {
                //binding.easyView.text= t.message
            }

        })




        //setContentView(R.layout.activity_scrolling)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        /*findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/

        if(Global.DataArray.isNotEmpty()){
            binding.startBeosztas.setOnClickListener {
                val intent = Intent(this@ScrollingActivity, CongregationActivity::class.java).apply {
                    putExtra("intentArrayNext", 0)
                }
                startActivity(intent)
            }
        }
        else{
            binding.startBeosztas.setOnClickListener {
                val activityToClose =  this@ScrollingActivity
                val intent = Intent(this@ScrollingActivity, ScrollingActivity::class.java).apply {
                    putExtra("intentArrayNext", 0)
                }
                startActivity(intent)
                Toast.makeText(this@ScrollingActivity,"Frissítés folyamatban. Próbáld újra!", Toast.LENGTH_LONG).show()
                activityToClose.finish()
            }
        }
*/
        binding.startNev.setOnClickListener {
            val intent = Intent(this@ScrollingActivity, NameActivity::class.java)
            startActivity(intent)
        }
    }
/*
    private fun roomAddItem(values: List<List<String>>, i: Int, j: Int) {
        thread {
            val item = CongregationDataRoom(
                    null,
                    values[i][j]
            )

            AppDatabase.getInstance(this@ScrollingActivity).congDao().insertInfo(item)


        }
    }*/

        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            // Inflate the menu; this adds items to the action bar if it is present.
            menuInflater.inflate(R.menu.menu_scrolling, menu)
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.

            return when (item.itemId) {
                R.id.action_settings -> true
                else -> super.onOptionsItemSelected(item)
            }
        }
    }
