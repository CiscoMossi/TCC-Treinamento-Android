package apps.com.br.tcc

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import apps.com.br.tcc.utils.UserDetailManager
import kotlinx.android.synthetic.main.activity_main.bt_login as submitButton
import kotlinx.android.synthetic.main.activity_main.ti_summoner_name as summonerName

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

            UserDetailManager.loadUserInfo("The Queen")//summonerName.text.toString())]
            UserDetailManager.loadMatchHistory()
            startActivity(intent)
        }
    }
}

