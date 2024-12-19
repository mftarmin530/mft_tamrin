package com.example.mft_public

import UserModel
import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.mft_public.databinding.ActivitySignUpBinding
import com.google.android.material.snackbar.Snackbar
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // actions
        binding.singUpLoginTv.setOnClickListener {
            finish()
        }
        binding.singUpSingUpTv.setOnClickListener {

            // وقتی روی دکمه ثبت نام کلیک میشود اول چک میشود که همه موارد به درستی وارد شده اند یا نه
            var validate = true
            if (binding.fullNameTIET.text.toString().isEmpty()) {
                validate = false
                binding.fullNameTIL.error = getString(R.string.full_name_cannot_be_empty)
            }
            if (binding.signUpUsernameTIET.text.toString().isEmpty()) {
                validate = false
                binding.signUpUsernameTIL.error = getString(R.string.username_cannot_be_empty)
            }
            if (binding.singUpPasswordTIET.text.toString().isEmpty()) {
                validate = false
                binding.singUpPasswordTIL.error = getString(R.string.password_cannot_be_empty)
            }
            if (binding.singUpRepeatPasswordTIET.text.toString() != binding.singUpPasswordTIET.text.toString()) {
                validate = false
                binding.singUpRepeatPasswordTIL.error =
                    getString(R.string.repeatPassword_and_password_not_same)
            }


            // اگر همه موارد به درستی وارده شده بودند این شرط برقرار می شود
            if (validate) {

                // ثبت نام با استفاده از وبسرویس

                val userModel = UserModel(
                    id = 0,
                    name = binding.fullNameTIET.text.toString(),
                    email = binding.emailTIET.text.toString(),
                    phone = binding.phoneTIET.text.toString(),
                    userName = binding.signUpUsernameTIET.text.toString(),
                    password = binding.singUpPasswordTIET.text.toString()
                )

                val retrofit = RetrofitProvider.getRetrofit()
                val call = retrofit.create(ApiService::class.java).signup(userModel)

                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            // سپس پیغام ثبت نام موفق به کاربر نمایش داده میشود و با تایید کاربر به صفحه لاگین برمیگردد
                            val builder = AlertDialog.Builder(this@SignUpActivity)
                            builder.setTitle(R.string.signup)
                            builder.setMessage(R.string.signup_successfully)
                            builder.setNeutralButton(R.string.ok) { a, b ->
                                finish()
                            }
                            builder.show()
                        } else {
                            Snackbar.make(binding.root, response.errorBody()?.string().toString(), Snackbar.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Snackbar.make(binding.root, t.stackTraceToString(), Snackbar.LENGTH_LONG).show()
                    }

                })


            }
        }


        // text changes
        binding.fullNameTIET.addTextChangedListener {
            binding.fullNameTIL.error = null
        }
        binding.signUpUsernameTIET.addTextChangedListener {
            binding.signUpUsernameTIL.error = null
        }
        binding.singUpPasswordTIET.addTextChangedListener {
            binding.singUpPasswordTIL.error = null
        }
        binding.singUpRepeatPasswordTIET.addTextChangedListener {
            binding.singUpRepeatPasswordTIL.error = null
        }
    }
}