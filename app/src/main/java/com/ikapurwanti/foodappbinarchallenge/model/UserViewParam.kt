package com.ikapurwanti.foodappbinarchallenge.model

import android.net.Uri
import com.google.firebase.auth.FirebaseUser

data class UserViewParam(
    val fullName: String?,
//    val photoUrl: String,
    val photoUrl: Uri?,
    val email: String?
)

fun FirebaseUser?.toUserViewParam(): UserViewParam? = if (this != null) {
    UserViewParam(
        fullName = this.displayName.orEmpty(),
//        photoUrl = this.photoUrl.toString(),
        photoUrl = this.photoUrl,
        email = this.email.orEmpty()
    )
} else {
    null
}
