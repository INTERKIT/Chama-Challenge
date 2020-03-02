package com.chama.challenge.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chama.challenge.R
import com.chama.challenge.common.utils.popBackStack
import com.chama.challenge.heritages.model.Heritage
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_location.toolbar

class LocationFragment : Fragment(), OnMapReadyCallback {

    companion object {
        private const val VALUE_TO_ZOOM = 15F
        private const val EXTRA_HERITAGE = "EXTRA_HERITAGE"

        fun create(heritage: Heritage): LocationFragment {
            val fragment = LocationFragment()

            val bundle = Bundle()
            bundle.putParcelable(EXTRA_HERITAGE, heritage)

            fragment.arguments = bundle
            return fragment
        }
    }

    private val heritageExtra by lazy { arguments?.getParcelable(EXTRA_HERITAGE) as? Heritage }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_location, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        toolbar.setNavigationOnClickListener { popBackStack() }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val heritage = heritageExtra ?: return
        val target = LatLng(heritage.lat, heritage.lng)

        val icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)
        val markerOptions = MarkerOptions()
            .position(target)
            .title(heritage.name)
            .icon(icon)

        googleMap.addMarker(markerOptions)

        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(target, VALUE_TO_ZOOM)
        googleMap.moveCamera(cameraUpdate)
    }
}