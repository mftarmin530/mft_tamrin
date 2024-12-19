package com.example.mft_public

import LoginModel
import UserModel
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.mft_public.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rememberSharedPreferences = getSharedPreferences("Remember", Context.MODE_PRIVATE)

        // actions
        binding.loginTv.setOnClickListener {
            // وقتی روی دکمه ثبت نام کلیک میشود اول چک میشود که همه موارد به درستی وارد شده اند یا نه
            var validate = true
            if (binding.userNameTIET.text.toString().isEmpty()) {
                validate = true
                binding.userNameTIL.error = getString(R.string.username_cannot_be_empty)
            }
            if (binding.passwordTIET.text.toString().isEmpty()) {
                validate = false
                binding.passwordTIL.error = getString(R.string.password_cannot_be_empty)
            }

            // در این مرحله اطلاعات وارد شده کاربر با اطلاعات ذخیره شده در صفحه ثبت نام چک میشود
            if (validate) {

                val loginModel = LoginModel(
                    binding.userNameTIET.text.toString(),
                    binding.passwordTIET.text.toString()
                )

                val retrofit = RetrofitProvider.getRetrofit()
                val call = retrofit.create(ApiService::class.java).login(loginModel)

                call.enqueue(object : Callback<UserModel> {
                    override fun onResponse(
                        call: Call<UserModel>,
                        response: Response<UserModel>
                    ) {
                        if (response.isSuccessful) {

                            val userModel = response.body()

                            if (userModel != null) {
                                val sharedPreferences =
                                    getSharedPreferences("login", Context.MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putString("username", userModel.userName)
                                editor.putString("password", userModel.password)
                                editor.putInt("id", userModel.id)
                                editor.putString("name", userModel.name)
                                editor.putString("phone", userModel.phone)
                                editor.putString("email", userModel.email)
                                if (binding.rememberMeChbx.isChecked) {
                                    editor.putBoolean("rememberMe", true)
                                } else {
                                    editor.putBoolean("rememberMe", false)
                                }
                                editor.apply()
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }


                        } else {
                            Snackbar.make(
                                binding.root,
                                response.errorBody()?.string().toString(),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<UserModel>, t: Throwable) {
                        Snackbar.make(binding.root, t.stackTraceToString(), Snackbar.LENGTH_LONG)
                            .show()
                    }

                })

            }
        }
        binding.signUpTv.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }


        // text changes events
        binding.userNameTIET.addTextChangedListener {
            binding.userNameTIL.error = null
        }
        binding.passwordTIET.addTextChangedListener {
            binding.passwordTIL.error = null
        }

        // remember me
        val saveLoginPreferences = rememberSharedPreferences.getBoolean("rememberMe", false)
        val saveUserName = rememberSharedPreferences.getString("username", null)
        val savedPassword = rememberSharedPreferences.getString("password", null)

        if (saveLoginPreferences) {
            binding.userNameTIET.setText(saveUserName)
            binding.passwordTIET.setText(savedPassword)
            binding.rememberMeChbx
                .isChecked = true
        }
    }
}