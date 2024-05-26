package dav.project.shoestore.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import dav.project.shoestore.R

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)


        // Status Bar color
        window.statusBarColor = Color.WHITE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        mAuth = FirebaseAuth.getInstance()

        setContentView(R.layout.activity_splash_screen)

        val isLogin: Boolean = mAuth.currentUser != null

        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed({
            if (isLogin)
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            else
                startActivity(Intent(this@SplashScreenActivity, IntroActivity::class.java))
            finish()
        }, 1500)
}
}