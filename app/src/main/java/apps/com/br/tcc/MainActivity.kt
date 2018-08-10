package apps.com.br.tcc

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.bt_login as submitButton
import kotlinx.android.synthetic.main.activity_main.et_invocador as usernameInput

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions

        submitButton.setOnClickListener{ _ ->
            val intent = Intent(this@MainActivity, NavigationActivity::class.java)
            intent.putExtra("USERNAME", usernameInput?.text)
            startActivity(intent)
        }
    }
}

