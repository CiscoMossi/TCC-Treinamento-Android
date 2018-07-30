package apps.com.br.tcc.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesManager {

    companion object {
        private var sharedPreferences: SharedPreferences? = null
        const val PREFERENCES_NAME = "TCC_SHARED_PREFS"

        fun bind(context: Context) {
            sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
        }

        fun setString(key: String, value: String) {
            val editor = sharedPreferences?.edit()

            editor?.putString(key, value)

            editor?.apply()
        }

        fun getString(key: String): String? {
            return sharedPreferences?.getString(key, null)
        }

        fun delete(key: String) {
            sharedPreferences?.edit()?.remove(key)?.apply()
        }
    }
}