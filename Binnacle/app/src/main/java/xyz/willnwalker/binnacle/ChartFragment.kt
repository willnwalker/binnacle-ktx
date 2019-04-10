package xyz.willnwalker.binnacle

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
class ChartFragment : Fragment() {

    private val TAG: String = "ChartFragment"
    private lateinit var listener: OnFragmentInteractionListener
    private lateinit var mMapView: MapView
    private lateinit var mMap: MapboxMap

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnFragmentInteractionListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(requireContext(), getString(R.string.mapbox_access_token))
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

    override fun onStart() {
        super.onStart()
        mMapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    /// Functions called while the fragment is running

    private fun showDeviceLocation(loadedMapStyle: Style) {
        Log.d(TAG, "Attempting to show device location.")
        Log.d(TAG, "Location permission previously granted. Showing device location.")
        /// TODO: Display User's current location
        val options = LocationComponentOptions.builder(requireContext())
            .trackingGesturesManagement(true)
            .accuracyColor(ContextCompat.getColor(requireContext(), R.color.mapbox_blue))
            .build()
        val locationComponent = mMap.locationComponent
        locationComponent.activateLocationComponent(requireContext(), loadedMapStyle)
        locationComponent.applyStyle(options)
        locationComponent.cameraMode = CameraMode.TRACKING
        locationComponent.renderMode = RenderMode.COMPASS
        locationComponent.isLocationComponentEnabled = true
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mMapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }
}
