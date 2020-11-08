package org.wit.hillforts.activities

import android.os.Bundle
import android.util.Patterns
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillforts.R
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.UserModel

class SettingsActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app : MainApp

    lateinit var etEmail: EditText
    lateinit var etPassword:EditText
    lateinit var etRepeatPassword:EditText
    val MIN_PASSWORD_LENGTH = 6;
    var user = UserModel()
    var hillfortsVisited: Int = 0
    var hillfortsAssigned: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        app = application as MainApp
        user = intent.extras?.getParcelable("user")!!
        hillfortsVisited = intent.extras?.getInt("hillforts_visited")!!
        hillfortsAssigned = intent.extras?.getInt("hillforts_number")!!

       hillforts_visited.setText("Number of Hillforts Visited: ${hillfortsVisited}")
        hillforts_assigned.setText("Number of Hillforts Assigned: ${hillfortsAssigned}")
        viewInitializations()
    }

    private fun viewInitializations() {

        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        etRepeatPassword = findViewById(R.id.et_repeat_password)

        // To show back button in actionbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Checking if the input in form is valid
    private fun validateInput(): Boolean {

        if (etEmail.text.toString().equals("")) {
            etEmail.setError("Please Enter Email")
            return false
        }
        if (etPassword.text.toString().equals("")) {
            etPassword.setError("Please Enter Password")
            return false
        }
        if (etRepeatPassword.text.toString().equals("")) {
            etRepeatPassword.setError("Please Enter Repeat Password")
            return false
        }

        // checking the proper email format
        if (!isEmailValid(etEmail.text.toString())) {
            etEmail.setError("Please Enter Valid Email")
            return false
        }

        // checking minimum password Length
        if (etPassword.text.length < MIN_PASSWORD_LENGTH) {
            etPassword.setError("Password Length must be more than " + MIN_PASSWORD_LENGTH + "characters")
            return false
        }

        // Checking if repeat password is same
        if (!etPassword.text.toString().equals(etRepeatPassword.text.toString())) {
            etRepeatPassword.setError("Password does not match")
            return false
        }
        return true
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }



    fun performSave (view: View) {
        if (validateInput()) {




            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val repeatPassword = etRepeatPassword.text.toString()
            user.email = email
            user.password = password
            app.users.update(user.copy())
            setResult(RESULT_OK)
            finish()
            Toast.makeText(this,"Updated Successfully",Toast.LENGTH_SHORT).show()


        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }

}