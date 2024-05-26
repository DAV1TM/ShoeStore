package dav.project.shoestore.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import dav.project.shoestore.R
import dav.project.shoestore.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Status Bar color
        window.statusBarColor = Color.WHITE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        firebaseAuth = FirebaseAuth.getInstance()

        val tvAlertMessage: TextView = binding.tvAlertMessage

        binding.btnSignUp.setOnClickListener {


            val username = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPass.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && username.isNotEmpty()) {
                if (password.equals(confirmPassword)) {

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { registrationTask ->

                            val user = FirebaseAuth.getInstance().currentUser

                            if (registrationTask.isSuccessful) {

                                val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                                startActivity(intent)

                                Toast.makeText(
                                    this,
                                    "Please verify your Email",
                                    Toast.LENGTH_SHORT
                                ).show()


                                firebaseAuth.currentUser?.sendEmailVerification()

                                finish()
                            } else {
                                tvAlertMessage.text = registrationTask.exception?.message
                                Handler().postDelayed({
                                    tvAlertMessage.text = ""
                                }, 3500)
                            }
                        }

                } else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty fields are not allowed!", Toast.LENGTH_SHORT).show()
            }
        }



        val tvSignIn: TextView = findViewById(R.id.tvSignIn)
        tvSignIn.setOnClickListener{
            startActivity(Intent(this@SignUpActivity, SignInActivity::class.java ))
        }
    }
}