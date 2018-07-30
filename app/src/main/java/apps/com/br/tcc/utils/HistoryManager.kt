package apps.com.br.tcc.utils

import apps.com.br.tcc.models.History
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

class HistoryManager {

    companion object {
        const val HISTORY_KEY = "history"
        private var history: MutableList<History>? = null

        fun getHistory() {
            val favoritesString = SharedPreferencesManager.getString(Companion.HISTORY_KEY)

            history = if (favoritesString != null) {
                Gson().fromJson<MutableList<History>>(favoritesString, object : TypeToken<List<History>>(){}.type)
            } else {
                ArrayList()
            }
        }

        private fun saveHistory() {
            val historyJson = Gson().toJson(history)
            SharedPreferencesManager.setString(Companion.HISTORY_KEY, historyJson)
        }

        fun removeFromHistory(historyItem: History) {
            history?.remove(historyItem)
            saveHistory()
        }

        fun pushToHistory(historyItem: History) {
            history?.add(historyItem)
            saveHistory()
        }

        fun deleteHistory() {
            history = ArrayList()
            SharedPreferencesManager.delete(Companion.HISTORY_KEY)
        }

    }


}