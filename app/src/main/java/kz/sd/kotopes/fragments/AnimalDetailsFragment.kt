package kz.sd.kotopes.fragments

import android.opengl.Visibility
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kz.sd.kotopes.base.BaseFragment
import kz.sd.kotopes.databinding.FragmentAnimalDetailsBinding
import kz.sd.kotopes.firebase.Animal
import kz.sd.kotopes.firebase.UserDao
import javax.inject.Inject

@AndroidEntryPoint
class AnimalDetailsFragment:BaseFragment<FragmentAnimalDetailsBinding>(FragmentAnimalDetailsBinding::inflate) {
    @Inject
    lateinit var userDao: UserDao
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    private val args:AnimalDetailsFragmentArgs by navArgs()
    override fun onBindView() {
        super.onBindView()
        var animal = args.animal
        var key = ""
        userDao.getData()
        userDao.getDataLiveData.observe(this) { user ->
            val animalId = args.animal.id.toString()

            val isFavorite = user?.favoriteList?.any { it.value.id == animalId } == true
            user?.favoriteList?.forEach { item ->
                if(item.value.id == animalId){
                    key = item.key
                }
            }
            binding.removeFromFavorite.isVisible = isFavorite
            binding.favoriteButton.isVisible = !isFavorite
        }
        with(binding){
            description.text = animal.description
            animal.image?.let { imageView.setImageResource(it) }
            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }

            favoriteButton.setOnClickListener {
                userDao.saveAnimalToList(animal)
                showCustomDialog("Успешно" , "Добавлено в избранное")
            }
            removeFromFavorite.setOnClickListener {
                userDao.deleteAnimalFromList(key)
                showCustomDialog("Успешно" , "Удалено из избранных")

            }
        }
    }

}