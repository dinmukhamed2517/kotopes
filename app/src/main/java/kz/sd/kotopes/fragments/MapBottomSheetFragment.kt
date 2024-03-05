package kz.sd.kotopes.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kz.sd.kotopes.databinding.FragmentMapBottomSheetBinding
import kz.sd.kotopes.firebase.Location

class MapBottomSheetFragment:BottomSheetDialogFragment() {
    private lateinit var binding:FragmentMapBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val location = arguments?.getParcelable<Location>("location")
        location?.let {
            binding.locationName.text = it.name
            binding.locationDescription.text = it.description
            binding.img.setImageResource(it.img)
            binding.saveBtn.setOnClickListener {it1 ->
                startDialer(it.phoneNumber)
            }
        }
    }
    private fun startDialer(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(intent)
    }
}