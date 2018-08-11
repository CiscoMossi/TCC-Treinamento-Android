package apps.com.br.tcc.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.com.br.tcc.R

class UserDetailFragment : Fragment() {
    companion object {
        val TAG = "UserDetailFragment"
        val USERNAME_KEY = "USERNAME"

        fun newInstance(username: String?): UserDetailFragment {
            val args = Bundle()
            args.putString(USERNAME_KEY, username)

            val fragment = UserDetailFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.user_detail_fragment, container, false)
        val username = arguments?.getString(USERNAME_KEY)



        return view
    }
}