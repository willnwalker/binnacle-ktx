package xyz.willnwalker.binnacle

import android.net.Uri
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navController: NavController

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            bottomNav.selectedItemId -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_readouts -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_binnacle -> {
                navController.navigate(R.id.binnacleFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_charts -> {
                navController.navigate(R.id.chartFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.navHostFragment)
        bottomNav = nav_view
        bottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        bottomNav.selectedItemId = R.id.navigation_charts
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 *
 *
 * See the Android Training lesson [Communicating with Other Fragments]
 * (http://developer.android.com/training/basics/fragments/communicating.html)
 * for more information.
 */
interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    fun onFragmentInteraction(uri: Uri)
}
