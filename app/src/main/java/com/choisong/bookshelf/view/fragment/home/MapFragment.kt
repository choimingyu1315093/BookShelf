package com.choisong.bookshelf.view.fragment.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentMapBinding
import com.choisong.bookshelf.model.IWishBookHaveUserDataResult
import com.choisong.bookshelf.model.TestBestsellerModel
import com.choisong.bookshelf.view.activity.HomeActivity
import com.choisong.bookshelf.view.adapter.NearBookAdapter
import com.choisong.bookshelf.view.dialog.NearBookDialog
import com.choisong.bookshelf.viewmodel.MapViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback, NearBookAdapter.OnClickListener, NearBookDialog.OnDialogClose {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private val mapViewModel: MapViewModel by viewModels()

    companion object {
        const val TAG = "MapFragment"
    }

    private lateinit var map: GoogleMap
    private lateinit var currentLocation: LatLng
    private var latitude = MyApplication.prefs.getLatitude("lat", 0f)
    private var longitude = MyApplication.prefs.getLongitude("lng", 0f)
    private var marker = MarkerOptions()
    private var image: String? = null

    private val rvNearBook: RecyclerView by lazy {
        requireActivity().findViewById(R.id.rvNearBook)
    }

    private lateinit var nearBookAdapter: NearBookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        observeViewModel()
    }
    
    private fun init() = with(binding){
        currentLocation = LatLng(latitude.toDouble(), longitude.toDouble())
        setupGoogleMap()
    }

    private fun setupGoogleMap() = with(binding){
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this@MapFragment)
    }
    
    private fun observeViewModel() = with(binding){
        mapViewModel.iWishBookHaveUserList.observe(viewLifecycleOwner){
            if(it.data.size != 0){
                nearBookAdapter = NearBookAdapter(it.data, this@MapFragment)
                rvNearBook.apply {
                    layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                    adapter = nearBookAdapter
                }

                CoroutineScope(Dispatchers.IO).launch {
                    for(i in 0 until it.data.size){
                        val imageUrl = URL(it.data[i].book_image)
                        val connection = imageUrl.openConnection()
                        val iconStream = connection.getInputStream()
                        val bitmap = BitmapFactory.decodeStream(iconStream)
                        val desiredWidth = 100
                        val desiredHeight = 150
                        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, desiredWidth, desiredHeight, true)

                        val nearBookLocation = LatLng(it.data[i].current_latitude!!.toDouble(), it.data[i].current_longitude!!.toDouble())
                        withContext(Dispatchers.Main){
                            marker = MarkerOptions().apply {
                                position(nearBookLocation)
                                title("${it.data[i].book_name}^^${it.data[i].book_image}^^${it.data[i].user_name}^^${it.data[i].user_idx}^^${it.data[i].book_isbn}")
                                icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap))
                            }

                            map.addMarker(marker)
                            map.setOnMarkerClickListener { marker ->
                                val title = marker.title.split("^^")[0]
                                val img = marker.title.split("^^")[1]
                                val name = marker.title.split("^^")[2]
                                val userIdx = marker.title.split("^^")[3]
                                val isbn = marker.title.split("^^")[4]
                                image = img
                                val dialog = NearBookDialog(title, img, name, userIdx.toInt(), isbn, this@MapFragment)
                                dialog.show(requireActivity().supportFragmentManager, "NearBookDialog")
                                true
                            }

                            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLocation, 13f)
                            map.animateCamera(cameraUpdate)
                            map.apply {
                                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    return@withContext
                                }
                                isMyLocationEnabled = true
                                uiSettings.isTiltGesturesEnabled = true
                                uiSettings.isMyLocationButtonEnabled = false
                                uiSettings.isCompassEnabled = false
                            }
                        }
                    }
                }
            }
        }
    }

    override fun nearBookClick(nearBook: IWishBookHaveUserDataResult) {
        Log.d(TAG, "nearBookClick: nearBook $nearBook")
        val dialog = NearBookDialog(nearBook.book_name!!, nearBook.book_image!!, nearBook.user_name!!, nearBook.user_idx!!.toInt(), nearBook.book_isbn!!, this@MapFragment)
        dialog.show(requireActivity().supportFragmentManager, "NearBookDialog")

        var clickLocation = LatLng(nearBook.current_latitude!!.toDouble(), nearBook.current_longitude!!.toDouble())
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(clickLocation, 13f)
        map.animateCamera(cameraUpdate)
    }

    @SuppressLint("PotentialBehaviorOverride")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }

    override fun isClose(b: Boolean, chatroomIdx: Int, name: String) {
        if(b){
            val action = MapFragmentDirections.actionMapFragmentToChatroomFragment(chatroomIdx, image, name)
            Navigation.findNavController(binding.root).navigate(action)
            (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}