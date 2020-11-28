package org.wit.hillforts.activities

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.hillforts.R
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.UserModel


class LoginActivity : AppCompatActivity() {
    lateinit var app : MainApp
    lateinit var etEmail: EditText
    lateinit var  etPassword: EditText
    val MIN_PASSWORD_LENGTH = 6
    var user = UserModel()
    val USER_REQUEST = 4


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        app = application as MainApp
        viewInitializations()
    }

    private fun viewInitializations() {
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)


    }

    // Checking if the input in form is valid
    private fun validateInput(): Boolean {
        if (etEmail.text.toString() == "") {
            etEmail.error = "Please Enter Email"
            return false
        }
        if (etPassword.text.toString() == "") {
            etPassword.error = "Please Enter Password"
            return false
        }

        // checking the proper email format
        if (!isEmailValid(etEmail.text.toString())) {
            etEmail.error = "Please Enter Valid Email"
            return false
        }

        // checking minimum password Length
        if (etPassword.text.length < MIN_PASSWORD_LENGTH) {
            etPassword.error = "Password Length must be more than " + MIN_PASSWORD_LENGTH + "characters"
            return false
        }
        return true
    }

    private fun isEmailValid(email: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isExistingUser(): Boolean{
        getUserFromFile()
        //Check if a user with that email exists
        return true

    }

    private fun isCorrectPassword(): Boolean{
        val returnedUser = getUserFromFile()
        var success = false
        if(returnedUser != null){
            if(returnedUser.password == etPassword!!.text.toString() )
            {
                success = true
            }
        }


            return success


    }

    private fun getUserFromFile(): UserModel{

        user = app.users.findOne(etEmail!!.text.toString())!!

        return user
    }

    fun performSignIn(v: View) {
        if (validateInput() && isExistingUser() && isCorrectPassword()) {


            user = app.users.findOne(etEmail!!.text.toString())!!
            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
            startActivityForResult (intentFor<HillfortListActivity>().putExtra("user", user), USER_REQUEST)

        }else
        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
    }

    fun goToSignup(v: View) {
        startActivityForResult<SignupActivity>(0)
    }
}