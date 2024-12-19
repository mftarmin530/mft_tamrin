data class MessageModel(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int,
    val name: String,
    val comments: MutableList<CommentModel>,
    val likes: MutableList<LikeModel>
)