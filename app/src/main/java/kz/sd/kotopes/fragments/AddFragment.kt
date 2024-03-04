package kz.sd.kotopes.fragments

import androidx.navigation.fragment.findNavController
import kz.sd.kotopes.base.BaseFragment
import kz.sd.kotopes.databinding.FragmentAddBinding

class AddFragment:BaseFragment<FragmentAddBinding>(FragmentAddBinding::inflate) {
    override fun onBindView() {
        super.onBindView()
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}