package com.example.mft_public

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // views
        val fullNameTIL = findViewById<TextInputLayout>(R.id.fullNameTIL)
        val fullNameTIET = findViewById<TextInputEditText>(R.id.fullNameTIET)
        val emailTIL = findViewById<TextInputLayout>(R.id.emailTIL)
        val emailTIET = findViewById<TextInputEditText>(R.id.emailTIET)
        val phoneTIL = findViewById<TextInputLayout>(R.id.phoneTIL)
        val phoneTIET = findViewById<TextInputEditText>(R.id.phoneTIET)
        val signUpUsernameTIL = findViewById<TextInputLayout>(R.id.signUpUsernameTIL)
        val signUpUsernameTIET = findViewById<TextInputEditText>(R.id.signUpUsernameTIET)
        val singUpPasswordTIL = findViewById<TextInputLayout>(R.id.singUpPasswordTIL)
        val singUpPasswordTIET = findViewById<TextInputEditText>(R.id.singUpPasswordTIET)
        val singUpRepeatPasswordTIL = findViewById<TextInputLayout>(R.id.singUpRepeatPasswordTIL)
        val singUpRepeatPasswordTIET =
            findViewById<TextInputEditText>(R.id.singUpRepeatPasswordTIET)
        val singUpSingUpTv = findViewById<TextView>(R.id.singUpSingUpTv)
        val singUpLoginTv = findViewById<TextView>(R.id.singUpLoginTv)


        // actions
        singUpLoginTv.setOnClickListener {
            finish()
        }
        singUpSingUpTv.setOnClickListener {

            // وقتی روی دکمه ثبت نام کلیک میشود اول چک میشود که همه موارد به درستی وارد شده اند یا نه
            var validate = true
            if (fullNameTIET.text.toString().isEmpty()) {
                validate = false
                fullNameTIL.error = getString(R.string.full_name_cannot_be_empty)
            }
            if (signUpUsernameTIET.text.toString().isEmpty()) {
                validate = false
                signUpUsernameTIL.error = getString(R.string.username_cannot_be_empty)
            }
            if (singUpPasswordTIET.text.toString().isEmpty()) {
                validate = false
                singUpPasswordTIL.error = getString(R.string.password_cannot_be_empty)
            }
            if (singUpRepeatPasswordTIET.text.toString() != singUpPasswordTIET.text.toString()) {
                validate = false
                singUpRepeatPasswordTIL.error =
                    getString(R.string.repeatPassword_and_password_not_same)
            }


            // اگر همه موارد به درستی وارده شده بودند این شرط برقرار می شود
            if (validate) {

                // ابتدا همه موارد ذخیره می شوند
                val sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("fullname", fullNameTIET.text.toString())
                editor.putString("email", emailTIET.text.toString())
                editor.putString("phone", phoneTIET.text.toString())
                editor.putString("username", signUpUsernameTIET.text.toString())
                editor.putString("password", singUpPasswordTIET.text.toString())
                editor.apply()

                // سپس پیغام ثبت نام موفق به کاربر نمایش داده میشود و با تایید کاربر به صفحه لاگین برمیگردد
                val builder = AlertDialog.Builder(this)
                builder.setTitle(R.string.signup)
                builder.setMessage(R.string.signup_successfully)
                builder.setNeutralButton(R.string.ok) { a, b ->
                    finish()
                }
                builder.show()
            }
        }


        // text changes
        fullNameTIET.addTextChangedListener {
            fullNameTIL.error = null
        }
        signUpUsernameTIET.addTextChangedListener {
            signUpUsernameTIL.error = null
        }
        singUpPasswordTIET.addTextChangedListener {
            singUpPasswordTIL.error = null
        }
        singUpRepeatPasswordTIET.addTextChangedListener {
            singUpRepeatPasswordTIL.error = null
        }
    }
}