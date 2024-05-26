package dav.project.shoestore.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dav.project.shoestore.R
import dav.project.shoestore.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()

        // Status Bar color
        window.statusBarColor = Color.WHITE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        val tvAlertMessage = binding.tvAlertMessage

        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { signInTask ->
                        if (signInTask.isSuccessful) {
                            val user: FirebaseUser? = auth.currentUser
                            if (user != null && user.isEmailVerified) {
                                startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                                finish()
                            } else {
                                tvAlertMessage.text = "Your email is not verifed"
                                Handler().postDelayed({
                                    tvAlertMessage.text = ""
                                }, 3500)
                            }
                        } else {
                            tvAlertMessage.text = signInTask.exception?.message
                            Handler().postDelayed({
                                tvAlertMessage.text = ""
                            }, 3500)
                        }
                    }
            } else {
                tvAlertMessage.text = "Empty Fields are not allowed!"
                Handler().postDelayed({
                    tvAlertMessage.text = ""
                }, 3500)
            }
        }

        val tvSignUp: TextView = findViewById(R.id.tvSignUp)
        tvSignUp.setOnClickListener{
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java ))
        }



    }
}