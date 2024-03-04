package kz.sd.kotopes.fragments

import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import kz.sd.kotopes.base.BaseFragment
import kz.sd.kotopes.databinding.FragmentSettingsBinding
import kz.sd.kotopes.firebase.UserDao
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment:BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var storageReference: StorageReference
    private var imageUri: Uri? = null
    @Inject
    lateinit var userDao: UserDao
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        binding.ava.setImageURI(it)
        imageUri = it
    }


    override var showBottomNavigation: Boolean = false
    override fun onBindView() {
        super.onBindView()

        val user = firebaseAuth.currentUser
        val uid = user?.uid
        storageReference = FirebaseStorage.getInstance().getReference("Users/" + "${uid}")
        with(binding) {

            cardView.setOnClickListener {
                resultLauncher.launch("image/*")
            }

            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            saveBtn.setOnClickListener {
                if (imageUri != null) {
                    uploadProfilePic()
                }
                updateProfileInfo()
                showCustomDialog("Успешно", "Информация изменена")
            }
        }
    }
    private fun uploadProfilePic() {
        imageUri?.let {
            storageReference.putFile(it).addOnSuccessListener { task ->
                task.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri ->
                    val imgUrl = uri.toString()
                    userDao.saveProfilePic(imgUrl)
                }


            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Невозможно загрузить фото", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
    private fun updateProfileInfo() {
        if (binding.etName.text?.isNotEmpty() == true) {
            binding.tilName.isErrorEnabled = false
            userDao.saveName(binding.etName.text.toString())

        } else {
            binding.tilName.error = "Заполните"
            binding.tilName.isErrorEnabled = true
        }

    }


}