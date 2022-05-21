package br.com.up.appmaps

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import br.com.up.appmaps.databinding.ActivityMapsPlaceBinding
import br.com.up.appmaps.model.Place
import br.com.up.appmaps.network.PlaceAPI
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.Marker

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val markers = ArrayList<Marker>()
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsPlaceBinding
    private var fusedLocationProviderClient :
            FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION),
                2)
        }
        else{

            getUserLastLocation()
        }

        PlaceAPI().requestPlaceBySearchTerm("Universidade+Positivo")

    }

    @SuppressLint("MissingPermission")
    fun getUserLastLocation(){

        fusedLocationProviderClient?.
        lastLocation?.addOnCompleteListener(this) { taskLocation ->

            val location = taskLocation.result

            if (location != null) {
           //     setUserLocationMarker(location)
            }

            requestUserLocation()
        }
    }

    @SuppressLint("MissingPermission")
    fun requestUserLocation(){

        val locationRequest = LocationRequest()
        //locationRequest.interval = 10
       // locationRequest.priority = LocationRequest.PRIORITY_LOW_POWER


        fusedLocationProviderClient?.requestLocationUpdates(
            locationRequest,
            object : LocationCallback(){

                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)

                    val location = locationResult?.locations?.get(0)
                    if(location != null){
                        setUserLocationMarker(location)
                    }
                }
                                       }, Looper.getMainLooper())



    }

    fun setUserLocationMarker(location: Location){

        val myLocation = LatLng(
            location.latitude,
            location.longitude
        )

        val marker = MarkerOptions()
            .position(myLocation)
            .title("Eu estou aqui")
            .snippet("Água a 12 reais")

        mMap.addMarker(marker)

        mMap.moveCamera(CameraUpdateFactory
            .newLatLngZoom(myLocation, 20f))

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if(/*requestCode == 2 &&*/
            grantResults[0] == PackageManager.PERMISSION_GRANTED){
            getUserLastLocation()
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
    }

}