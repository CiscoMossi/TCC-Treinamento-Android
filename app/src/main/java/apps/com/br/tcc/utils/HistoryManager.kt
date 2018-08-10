package apps.com.br.tcc.utils

import apps.com.br.tcc.models.History
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

class HistoryManager {

    companion object {
        const val HISTORY_KEY = "history"
        var history: ArrayList<History> = ArrayList()

        fun loadHistory() {
            val historyString = SharedPreferencesManager.getString(Companion.HISTORY_KEY)

            if (historyString != null)
                history = Gson().fromJson<ArrayList<History>>(historyString, object : TypeToken<List<History>>(){}.type)

            history = ArrayList()
            history.add(History("Kiko", ""))
        }

        private fun saveHistory() {
            val historyJson = Gson().toJson(history)
            SharedPreferencesManager.setString(Companion.HISTORY_KEY, historyJson)
        }

        fun removeFromHistory(historyItem: History) {
            history.remove(historyItem)
            saveHistory()
        }

        fun pushToHistory(historyItem: History) {
            history.add(historyItem)
            saveHistory()
        }

        fun deleteHistory() {
            history = ArrayList()
            SharedPreferencesManager.delete(Companion.HISTORY_KEY)
        }
    }


}