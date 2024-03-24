package com.latihan.bakso

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.latihan.bakso.model.DataItem
import com.latihan.bakso.model.ResponseUser
import com.latihan.bakso.network.ApiConfig
import retrofit2.Call
import retrofit2.Response

class MainActivity: AppCompatActivity() {
    private lateinit var adapter: UserAdapter
    val rv_users: RecyclerView = findViewById(R.id.rv_users)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = UserAdapter(mutableListOf())
        rv_users.setHasFixedSize(true)
        rv_users.layoutManager = LinearLayoutManager(this)
        rv_users.adapter = adapter
        getUser()
    }
    private fun getUser() {
        val client = ApiConfig.getApiService().getListUsers("1")
        client.enqueue(object: retrofit2.Callback<ResponseUser> {
            override fun onResponse (call: Call<ResponseUser>, response : Response<ResponseUser>) {
                if (response.isSuccessful) {
                    val dataArray = response.body()?.data as List<DataItem>
                    for (data in dataArray) {
                        adapter.addUser(data)
                    }
                }
            }
            override fun onFailure (call: Call<ResponseUser>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }
}