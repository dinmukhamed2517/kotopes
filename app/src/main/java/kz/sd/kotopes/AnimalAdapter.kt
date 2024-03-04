package kz.sd.kotopes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kz.sd.kotopes.base.BaseAnimalViewHolder
import kz.sd.kotopes.databinding.ItemAnimalBinding
import kz.sd.kotopes.firebase.Animal

class AnimalAdapter: ListAdapter<Animal, BaseAnimalViewHolder<*>>(AnimalDiffUtils()) {

    var itemClick: ((Animal) -> Unit)? = null

    class AnimalDiffUtils: DiffUtil.ItemCallback<Animal>(){
        override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseAnimalViewHolder<*> {
        return AnimalViewHolder(
            ItemAnimalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseAnimalViewHolder<*>, position: Int) {
        holder.bindView(getItem(position))
    }
    inner class AnimalViewHolder(binding:ItemAnimalBinding):BaseAnimalViewHolder<ItemAnimalBinding>(binding) {
        override fun bindView(item: Animal) {
            item.image?.let { binding.image.setImageResource(it) }
            itemView.setOnClickListener {
                itemClick?.invoke(item)
            }
        }
    }
}