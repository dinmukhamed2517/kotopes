package kz.sd.kotopes.fragments

import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kz.sd.kotopes.AnimalAdapter
import kz.sd.kotopes.R
import kz.sd.kotopes.base.BaseFragment
import kz.sd.kotopes.databinding.FragmentProfileBinding
import kz.sd.kotopes.firebase.Animal
import kz.sd.kotopes.firebase.UserDao
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment:BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var userDao: UserDao
    var animals: MutableList<Animal> = mutableListOf()

    override fun onBindView() {
        userDao.getData()
        val adapter = AnimalAdapter()
        super.onBindView()
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.signOutBtn.setOnClickListener {
            signOut()
        }
        adapter.itemClick = {
            findNavController().navigate(ProfileFragmentDirections.actionProfileToAnimalDetailsFragment(it))
        }
        binding.email.text = firebaseAuth.currentUser?.email;
        binding.settingBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_settingsFragment)
        }
        binding.paymentBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_paymentFragment)
        }
        binding.favoriteRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.favoriteRecycler.adapter = adapter
        userDao.getDataLiveData.observe(this){
            val animalMap = it?.favoriteList
            animalMap?.forEach { item ->
                animals.add(item.value)
            }
            binding.name.text = it?.name
            if (it?.pictureUrl != null) {
                Glide.with(requireContext())
                    .load(it?.pictureUrl)
                    .into(binding.ava)
            } else {
                binding.ava.setImageResource(R.drawable.profile_icon)
            }
            adapter.submitList(animals)
        }

    }

    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser == null){
            findNavController().navigate(R.id.action_profile_to_loginFragment)
        }

    }
    private fun signOut() {
        var alertDialog: AlertDialog? = null
        alertDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Выйти с аккаунта")
            .setMessage("Вы уверены что хотите выйти с аккаунта?")
            .setPositiveButton("Да") { _, _ ->
                firebaseAuth.signOut()
                alertDialog?.dismiss()
                findNavController().navigate(
                    R.id.action_profile_to_loginFragment
                )
            }
            .setNegativeButton("Отмена") { _, _ ->
                alertDialog?.dismiss()
            }
            .show()
    }

}