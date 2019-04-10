package xyz.willnwalker.binnacle

import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener, PermissionsListener {

    private val TAG: String = "MainActivity"
    private lateinit var navController: NavController
    private lateinit var mBottomNav: BottomNavigationView
    private lateinit var permissionsManager: PermissionsManager

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            mBottomNav.selectedItemId -> {
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
        mBottomNav = bottomNavigationView
        mBottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        mBottomNav.selectedItemId = R.id.navigation_binnacle
        permissionsManager = PermissionsManager(this)
        if(!PermissionsManager.areLocationPermissionsGranted(this)){
            permissionsManager.requestLocationPermissions(this)
        }
    }

    /// Location stuff

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Log.d(TAG, "onRequestPermissionsResult called.")
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
        permissionsToExplain!!.forEach {
            Log.d(TAG, it)
        }
        MaterialDialog(this).show {
            title(text = "Location Permission Required")
            message(text = "Binnacle needs your device's location to show where you are on nautical charts.")
        }
    }

    override fun onPermissionResult(granted: Boolean) {
        Log.d(TAG, "onPermissionResult called.")
        if(granted){
            Log.d(TAG, "Location permission granted by user.")
        }
        else{
            Log.d(TAG, "Location permission denied by user.")
            MaterialDialog(this).show {
                title(text = "Can't get Device Location")
                message(text = "Since you denied the Location Permission, Binnacle can't show your location. You can enable this permission from Device Settings -> App Info")
                onDismiss { finish() }
            }
        }
    }

    override fun requestLocationPermissions() {
        return
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
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
    fun requestLocationPermissions()
}
