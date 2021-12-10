package com.example.congregationinfo


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.congregationinfo.data.AppDatabase
import com.example.congregationinfo.data.CongregationData
import com.example.congregationinfo.data.CongregationDataRoom
import com.example.congregationinfo.databinding.ActivityScrollingBinding
import com.example.congregationinfo.network.GoogleAPI
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.concurrent.thread

class ScrollingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScrollingBinding
    //lateinit var congInfoResult: List<List<String>>
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

        val retrofit = Retrofit.Builder()
                .baseUrl("https://sheets.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create()).build()

        val googleAPI = retrofit.create(GoogleAPI::class.java)

        val congregationInfoCall = googleAPI.getData("AIzaSyA4gXS5fL81WnHO98SiwsmBGiYb9V1ceQQ")
        congregationInfoCall.enqueue(object : Callback<CongregationData>{

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
                        /* Ez a text változó ideiglenes, amíg nincs meg a UI.
                         ugyanakkor a következő sorban már beraktam a room-ba az adatot,
                         ahonnan majd kiszedjük a UI szerinti megjelenítéshez */
                        //roomAddItem(congInfoResult.values, i, j)
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
                val intent = Intent(this@ScrollingActivity, ScrollingActivity::class.java).apply {
                    putExtra("intentArrayNext", 0)
                }
                startActivity(intent)
            }
            Toast.makeText(this@ScrollingActivity,"Frissítés folyamatban. Próbáld újra!", Toast.LENGTH_LONG).show()
        }

        binding.startNev.setOnClickListener {
            val intent = Intent(this@ScrollingActivity, NameActivity::class.java)
            startActivity(intent)
        }
    }

    private fun roomAddItem(values: List<List<String>>, i: Int, j: Int) {
        thread {
            val item = CongregationDataRoom(
                    null,
                    values[i][j]
            )

            AppDatabase.getInstance(this@ScrollingActivity).congDao().insertInfo(item)


        }
    }

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