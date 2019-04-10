package xyz.willnwalker.binnacle

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_binnacle.*
import kotlinx.android.synthetic.main.fragment_readouts.*
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class ReadoutFragment : Fragment() {
    private lateinit var listener: OnFragmentInteractionListener
    private lateinit var mSpeedView: TextView
    private lateinit var mHeadingView: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnFragmentInteractionListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_readouts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSpeedView = speedView
        mHeadingView = headingView

        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener = object : LocationListener {

            override fun onLocationChanged(location: Location) {
                // Called when a new location is found by the network location provider.
                var speed = location.speed.div(1.151).toString()
                var heading = location.bearing.toString()
                mSpeedView.text = speed + " kts"
                mHeadingView.text = heading + "\t°"
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            }

            override fun onProviderEnabled(provider: String) {
            }

            override fun onProviderDisabled(provider: String) {
            }
        }

        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }

}
