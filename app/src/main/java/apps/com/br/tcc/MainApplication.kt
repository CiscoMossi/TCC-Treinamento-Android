package apps.com.br.tcc

import android.app.Application
import apps.com.br.tcc.utils.HistoryManager
import apps.com.br.tcc.utils.SharedPreferencesManager
import apps.com.br.tcc.utils.UserDetailManager

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManager.bind(this)
        HistoryManager.loadHistory()
    }
}