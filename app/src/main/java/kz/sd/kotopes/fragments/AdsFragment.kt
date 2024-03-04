package kz.sd.kotopes.fragments

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kz.sd.kotopes.AdsAdapter
import kz.sd.kotopes.R
import kz.sd.kotopes.base.BaseFragment
import kz.sd.kotopes.databinding.FragmentAdsBinding
import kz.sd.kotopes.firebase.Ad

class AdsFragment:BaseFragment<FragmentAdsBinding>(FragmentAdsBinding::inflate) {
    override fun onBindView() {
        super.onBindView()
        val adapter = AdsAdapter()
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.addBtn.setOnClickListener {
            findNavController().navigate(R.id.action_ads_to_addFragment)
        }
        adapter.submitList(getList())
    }
    fun getList():List<Ad>{
        return listOf(
            Ad("Пропала Собака!!!", "город Алматы Бостандыский район", R.drawable.photo2),
            Ad("Потеряли кошку", "город Алматы Ауезовский район", R.drawable.photo1),
            Ad("Собака убежала из дома!", "город Алматы Алмалинский район", R.drawable.photo3),

        )
    }
}