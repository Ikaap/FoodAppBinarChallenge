package com.ikapurwanti.foodappbinarchallenge.data.network.firebase.auth

import android.net.Uri
import kotlin.jvm.Throws

interface FirebaseAuthDataSource {
    @Throws(exceptionClasses = [Exception::class])
    suspend fun doLogin(email: String, password: String): Boolean

    @Throws(exceptionClasses = [Exception::class])
    suspend fun doRegister(fullName: String, email: String, password: String): Boolean

    suspend fun updateProfile(fullName: String? = null, photoUri: Uri? = null): Boolean

    suspend fun updatePassword(newPassword: String): Boolean

    fun sendChangePasswordRequestByEmail(): Boolean

    fun doLogout(): Boolean

    fun isLoggedIn(): Boolean

//    fun getCurrentUser(): FirebaseUser?


}