package com.example.mft_public

import RequestMessageModel
import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.mft_public.databinding.ActivityAddMessageBinding
import com.google.android.material.snackbar.Snackbar
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.titleTIET.addTextChangedListener {
            binding.titleCountTv.text = it?.length.toString() + "/10"
        }
        binding.addTv.setOnClickListener {
            val requestMessageModel = RequestMessageModel(binding.titleTIET.text.toString(), binding.messageTIET.text.toString())

            val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
            val userName = sharedPreferences.getString("username", null)
            val password = sharedPreferences.getString("password", null)
            val id = sharedPreferences.getInt("id", 0)

            val retrofit = RetrofitProvider.getRetrofit()
            if (userName != null && password != null) {
                val call = retrofit.create(ApiService::class.java)
                    .sendAMessage(id, userName, password, requestMessageModel)

                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            finish()
                        } else {
                            Snackbar.make(
                                binding.root,
                                response.errorBody()?.string().toString(),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Snackbar.make(binding.root, t.stackTraceToString(), Snackbar.LENGTH_LONG)
                            .show()
                    }
                })
            }
        }
    }
}