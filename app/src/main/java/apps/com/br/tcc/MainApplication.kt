package apps.com.br.tcc

import android.app.Application
import apps.com.br.tcc.utils.HistoryManager
import apps.com.br.tcc.utils.SharedPreferencesManager

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManager.bind(this)
        HistoryManager.getHistory()
    }
}