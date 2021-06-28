package ru.netology.nmedia.model

import ru.netology.nmedia.dto.Post

//data class FeedModel(
//    val posts: List<Post> = emptyList(),
////    val empty: Boolean = false,
//)

sealed interface FeedModel  {
    val id: Long
}

data class PostModel(
    val post: Post,
): FeedModel {
    override val id: Long = post.id
}

data class AdModel(
    override val id: Long,
    val picture: String
): FeedModel
