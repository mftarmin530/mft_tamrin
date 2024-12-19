package com.example.mft_public

import MessageModel
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mft_public.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBtn.setOnClickListener {
            val intent = Intent(this, AddMessageActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        val retrofit = RetrofitProvider.getRetrofit()
        val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", null)
        val password = sharedPreferences.getString("password", null)
        val id = sharedPreferences.getInt("id", 0)
        if (userName != null && password != null) {
            val call = retrofit.create(ApiService::class.java).getMessages(id, userName, password)
            call.enqueue(object : Callback<List<MessageModel>> {
                override fun onResponse(
                    call: Call<List<MessageModel>>,
                    response: Response<List<MessageModel>>
                ) {
                    if (response.isSuccessful) {
                        val list = response.body()
                        if (list != null) {
                            val adapter = MessageAdapter(list)
                            binding.messageRv.layoutManager = LinearLayoutManager(this@MainActivity)
                            binding.messageRv.adapter = adapter
                        }

                    } else {
                        Snackbar.make(
                            binding.root,
                            response.errorBody()?.string().toString(),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<MessageModel>>, t: Throwable) {
                    Snackbar.make(binding.root, t.stackTraceToString(), Snackbar.LENGTH_LONG)
                        .show()
                }

            })
        }
    }
}