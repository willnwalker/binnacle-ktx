package xyz.willnwalker.binnacle

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.android.synthetic.main.fragment_chart.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class ChartFragment : Fragment(), PermissionsListener {
    private val TAG: String = "ChartFragment"
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var mMapView: MapView
    private lateinit var mMap: MapboxMap

    override fun onStart() {
        super.onStart()
        mMapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mMapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMapView.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(requireContext(), getString(R.string.access_token))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "ChartFragment view created.")
        mMapView = mapView
        mMapView.onCreate(savedInstanceState)
        mMapView.getMapAsync{ mapboxMap ->
            mMap = mapboxMap
            mapboxMap.setStyle(Style.LIGHT){
                // TODO: Add pins, other map decorations here
                showDeviceLocation(it)
            }
        }
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
        permissionsToExplain!!.forEach {
            Log.d(TAG, it)
        }
        MaterialDialog(requireContext()).show {
            title(text = "Location Permission Required")
            message(text = "Binnacle needs your device's location to show where you are on nautical charts.")
        }
    }

    override fun onPermissionResult(granted: Boolean) {
        if(granted){
            showDeviceLocation(mMap.style!!)
            Log.d(TAG, "Location permission granted.")
        }
        else{
            Log.d(TAG, "Location permission denied.")
            MaterialDialog(requireContext()).show {
                title(text = "Can't get Device Location")
                message(text = "Since you denied the Location Permission, Binnacle can't show your location. You can enable this permission from Device Settings -> App Info")
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun showDeviceLocation(loadedMapStyle: Style) {
        Log.d(TAG, "Attempting to show device location.")
        if(PermissionsManager.areLocationPermissionsGranted(requireContext())){
            Log.d(TAG, "Location permission previously granted. Showing device location.")
            /// TODO: Display User's current location
            val options = LocationComponentOptions.builder(requireContext())
                .trackingGesturesManagement(true)
                .accuracyColor(ContextCompat.getColor(requireContext(), R.color.mapbox_blue))
                .build()
            val locationComponent = mMap.locationComponent
            locationComponent.activateLocationComponent(requireContext(), loadedMapStyle)
            locationComponent.applyStyle(options)
            locationComponent.isLocationComponentEnabled = true
            locationComponent.cameraMode = CameraMode.TRACKING
            locationComponent.renderMode = RenderMode.COMPASS
        }
        else{
            PermissionsManager(this).requestLocationPermissions(requireActivity())
        }
    }
}
