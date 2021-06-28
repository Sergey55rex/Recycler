package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardAdBinding
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.AdModel
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.model.PostModel
import ru.netology.nmedia.view.load
import ru.netology.nmedia.view.loadCircleCrop


interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onShare(post: Post) {}
    fun onSeePhoto(post: Post){}
    fun onAdClicked(adModel: AdModel){}
}

class PostsAdapter(
    private val onInteractionListener: OnInteractionListener,
) : PagingDataAdapter<FeedModel, RecyclerView.ViewHolder>(PostDiffCallback()) {

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is AdModel -> R.layout.card_ad
            is PostModel -> R.layout.card_post
            null -> error("Unknown type at $position")
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.card_ad -> {
                val binding =
                    CardAdBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AdViewHolder(binding, onInteractionListener)
            }
            R.layout.card_post -> {
                val binding =
                    CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PostViewHolder(binding, onInteractionListener)
            }
            else -> error("unknown view type $viewType")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostViewHolder -> {
                val item = getItem(position) as PostModel
                holder.bind(item.post)
            }
            is AdViewHolder -> {
                val item = getItem(position) as AdModel
                holder.bind(item)

            }
        }
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            
            avatar.loadCircleCrop("${BuildConfig.BASE_URL}/avatars/${post.authorAvatar}")
            like.isChecked = post.likedByMe
            like.text = "${post.likes}"
            if (post.attachment?.url != null) {
                imagePlay.visibility= View.VISIBLE
                imagePlay.load("${BuildConfig.BASE_URL}/media/${post.attachment?.url}")

            }
            buttonPhoto.setOnClickListener(){
                onInteractionListener.onSeePhoto(post)
            }

            menu.visibility = if (post.ownedByMe) View.VISIBLE else View.INVISIBLE

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)

                    // TODO: if we don't have other options, just remove dots
                    menu.setGroupVisible(R.id.owned, post.ownedByMe)

                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }

            like.setOnClickListener {
                onInteractionListener.onLike(post)
            }

            share.setOnClickListener {
                onInteractionListener.onShare(post)
            }
        }
    }
}


class AdViewHolder(
    private val binding: CardAdBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(ad: AdModel) {
        binding.apply {

            image.load("${BuildConfig.BASE_URL}/media/${ad.picture}")

            root.setOnClickListener {
                onInteractionListener.onAdClicked(ad)
            }
        }
    }
}







class PostDiffCallback : DiffUtil.ItemCallback<FeedModel>() {
    override fun areItemsTheSame(oldItem: FeedModel, newItem: FeedModel): Boolean {
        if (oldItem.javaClass != newItem.javaClass){
            return false
        }
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FeedModel, newItem: FeedModel): Boolean {
        return oldItem == newItem
    }
}
