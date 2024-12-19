package com.example.mft_public

import MessageModel
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mft_public.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val messageId = intent.getIntExtra("messageId", 0)

        val retrofit = RetrofitProvider.getRetrofit()
        val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", null)
        val password = sharedPreferences.getString("password", null)
        val id = sharedPreferences.getInt("id", 0)
        if (userName != null && password != null) {
            val call =
                retrofit.create(ApiService::class.java)
                    .getSingleMessage(id, userName, password, messageId)

            call.enqueue(object : Callback<MessageModel> {
                override fun onResponse(
                    call: Call<MessageModel>,
                    response: Response<MessageModel>
                ) {
                    if (response.isSuccessful) {
                        val message = response.body()
                        if (message != null) {
                            binding.nameTv.text = message.name
                            binding.titleTv.text = message.title
                            binding.bodyTv.text = message.body
                            binding.likeTv.text = message.likes.size.toString()
                            binding.commentTv.text = message.comments.size.toString()
                            binding.commentsRv.layoutManager = LinearLayoutManager(this@DetailActivity)
                            val adapter = CommentAdapter(message.comments)
                            binding.commentsRv.adapter = adapter
                        }
                    } else {
                        Snackbar.make(
                            binding.root,
                            response.errorBody()?.string().toString(),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<MessageModel>, t: Throwable) {
                    Snackbar.make(binding.root, t.stackTraceToString(), Snackbar.LENGTH_LONG)
                        .show()
                }

            })

        }


        binding.addTv.setOnClickListener {
            val requestCommentModel = SendCommentModel(binding.addCommentTIET.text.toString())
            if (userName != null && password != null) {
                val addCommentCall = retrofit.create(ApiService::class.java)
                    .sendComment(id, userName, password, messageId, requestCommentModel)

                addCommentCall.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {

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