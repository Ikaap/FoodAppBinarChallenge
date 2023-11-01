package com.ikapurwanti.foodappbinarchallenge.model

import com.google.firebase.auth.FirebaseUser

data class UserViewParam(
    val fullName: String,
    val email: String,
    val photoUrl: String
)

fun FirebaseUser?.toUserViewParam(): UserViewParam? = if (this != null) {
    UserViewParam(
        fullName = this.displayName.orEmpty(),
        photoUrl = this.photoUrl.toString(),
        email = this.email.orEmpty(),
    )
} else null
