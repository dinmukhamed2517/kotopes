package kz.sd.kotopes.fragments

import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.RoundCap
import kz.sd.kotopes.R
import kz.sd.kotopes.base.BaseFragment
import kz.sd.kotopes.databinding.FragmentMapBinding
import kz.sd.kotopes.firebase.Location

class MapFragment: BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private val locations = listOf(
        Location("1", "Зоомагазин TheМиска", "улица Ауэзова 179, Алматы 050057", LatLng(43.227795914949475, 76.90664416060342), R.drawable.zoo_1, "87072172020"),
        Location("2", "Зоомагазин ZooZverOK", "улица Панфилова 32, Алматы 050016", LatLng(43.268665537202445, 76.94539104708745),R.drawable.zoo_2,"87072172020"),
        Location("3", "Kotopes.kz", "пр-т. Сейфуллина 531, Алматы 050000", LatLng(43.25897934060725, 76.93213463136838), R.drawable.zoo_3,"87072172020"),
        Location("4", "ZooOptTorg.KZ", "улица Толе Би 243б, Алматы", LatLng(43.25310179386142, 76.88245697362674), R.drawable.zoo_4,"87072172020"),
        Location("5", "Зоомагазин TheМиска", "TheMiska, Розыбакиева, 247а ТРЦ Mega almaty, Алматы 050060", LatLng(43.20346725116032, 76.89470391600855), R.drawable.zoo_5,"87072172020"),
        Location("6", "ЗооГурман", "дом 46, улица Улугбека 46, Алматы 050000", LatLng(43.23112363780568, 76.85786099424996), R.drawable.zoo_6,"87072172020"),
    )
    private var currentLocationIndex = 0

    override fun onBindView() {
        super.onBindView()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.zoomIn.setOnClickListener {
            mMap.animateCamera(CameraUpdateFactory.zoomIn())
        }

        binding.zoomOut.setOnClickListener {
            mMap.animateCamera(CameraUpdateFactory.zoomOut())
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val zoomLevel = 17.0f

        locations.forEach { location ->
            val marker = mMap.addMarker(MarkerOptions().position(location.latLng).title(location.name))
            marker?.tag = location
        }

        mMap.setOnMarkerClickListener { marker ->
            val location = marker.tag as? Location
            location?.let {
                showBottomSheet(it)
            }
            true // Return true to indicate that we have consumed the event and no further processing is required.
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locations[0].latLng, zoomLevel))

        setupNavigationControls()
    }
    private fun showBottomSheet(location: Location) {
        val bottomSheetFragment = MapBottomSheetFragment().apply {
            arguments = Bundle().apply {
                putParcelable("location", location)
            }
        }
        bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
    }

    private fun setupNavigationControls() {
        binding.nextLocationButton.setOnClickListener {
            navigateToNextLocation()
        }
        binding.previousLocationButton.setOnClickListener {
            navigateToPreviousLocation()
        }
    }

    private fun navigateToPreviousLocation() {
        currentLocationIndex = if (currentLocationIndex > 0) currentLocationIndex - 1 else locations.size - 1
        moveCameraToLocationIndex(currentLocationIndex)
    }

    private fun navigateToNextLocation() {
        currentLocationIndex = (currentLocationIndex + 1) % locations.size
        moveCameraToLocationIndex(currentLocationIndex)
    }

    private fun moveCameraToLocationIndex(index: Int, zoomLevel: Float = 17.0f) {
        if (index in locations.indices) {
            val location = locations[index]
            val targetLatLng = location.latLng
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(targetLatLng, zoomLevel))
        } else {
            Log.e("MapFragment", "Index out of bounds: $index")
        }
    }
}