package apps.com.br.tcc

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import apps.com.br.tcc.adapters.HistoryAdapter
import apps.com.br.tcc.fragments.SearchFragment
import apps.com.br.tcc.fragments.UserDetailFragment
import apps.com.br.tcc.models.History
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.fragment_search.et_search as search

class NavigationActivity : AppCompatActivity(), HistoryAdapter.Listener, TextView.OnEditorActionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val username = intent.extras["USERNAME"] as String

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        callFragment(UserDetailFragment.newInstance(username))
    }

    override fun onHistoryItemClicked(history: History) {
        val fragment : UserDetailFragment = UserDetailFragment.newInstance(history.usename)

        callFragment(fragment)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if(actionId == EditorInfo.IME_ACTION_DONE) {
            val fragment : UserDetailFragment = UserDetailFragment.newInstance(search.text.toString())
            callFragment(fragment)

            return true
        }

        return false
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_user -> {
                callFragment(UserDetailFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_search -> {
                callFragment(SearchFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun callFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.fl_content, fragment)
        fragmentTransaction.commit()
    }
}