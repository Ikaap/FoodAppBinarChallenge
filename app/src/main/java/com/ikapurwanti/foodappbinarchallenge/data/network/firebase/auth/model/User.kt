package com.ikapurwanti.foodappbinarchallenge.data.network.firebase.auth.model

import com.google.firebase.auth.FirebaseUser

data class User(
    val fullName: String,
    val email: String,
    val photoUrl: String
)

fun FirebaseUser?.toUser(): User? = if (this != null) {
    User(
        fullName = this.displayName.orEmpty(),
        photoUrl = this.photoUrl.toString(),
        email = this.email.orEmpty(),
    )
} else null

