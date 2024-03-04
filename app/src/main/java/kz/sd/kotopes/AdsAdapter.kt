package kz.sd.kotopes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import kz.sd.kotopes.base.BaseAdsViewHolder
import kz.sd.kotopes.databinding.ItemAdBinding
import kz.sd.kotopes.firebase.Ad

class AdsAdapter:ListAdapter<Ad, BaseAdsViewHolder<*>>(AdDiffUtils()) {

    class AdDiffUtils:DiffUtil.ItemCallback<Ad>(){
        override fun areItemsTheSame(oldItem: Ad, newItem: Ad): Boolean {
            return oldItem.title != newItem.title
        }

        override fun areContentsTheSame(oldItem: Ad, newItem: Ad): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseAdsViewHolder<*> {
        return AdsViewHolder(
            ItemAdBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseAdsViewHolder<*>, position: Int) {
        holder.bindView(getItem(position))
    }
    inner class AdsViewHolder(binding:ItemAdBinding):BaseAdsViewHolder<ItemAdBinding>(binding){
        override fun bindView(item: Ad) {
            with(binding){
                title.text = item.title
                location.text = item.location
                item.imageUrl?.let { img.setImageResource(it) }
            }
        }
    }
}