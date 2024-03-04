package kz.sd.kotopes.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kz.sd.kotopes.firebase.Ad
import kz.sd.kotopes.firebase.Animal

abstract class BaseViewHolder<VB : ViewBinding, T>(protected open val binding: VB) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bindView(item: T)
}

abstract class BaseAnimalViewHolder<VB : ViewBinding>(override val binding: VB) :
    BaseViewHolder<VB, Animal>(binding)

abstract class BaseAdsViewHolder<VB:ViewBinding>(override val binding: VB):BaseViewHolder<VB, Ad>(binding)