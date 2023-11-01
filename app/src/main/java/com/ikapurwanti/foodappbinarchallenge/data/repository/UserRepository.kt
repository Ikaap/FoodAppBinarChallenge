package com.ikapurwanti.foodappbinarchallenge.data.repository

import android.net.Uri
import com.ikapurwanti.foodappbinarchallenge.data.network.firebase.auth.datasource.FirebaseAuthDataSource
import com.ikapurwanti.foodappbinarchallenge.model.UserViewParam
import com.ikapurwanti.foodappbinarchallenge.model.toUserViewParam
import com.ikapurwanti.foodappbinarchallenge.utils.ResultWrapper
import com.ikapurwanti.foodappbinarchallenge.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun doLogin(email: String, password: String): Flow<ResultWrapper<Boolean>>

    suspend fun doRegister(fullName: String, email: String, password: String): Flow<ResultWrapper<Boolean>>

    fun doLogout(): Boolean

    fun isLoggedIn(): Boolean

    fun getCurrentUser(): UserViewParam?

    suspend fun updateProfile(fullName: String? = null, photoUri: Uri? = null): Flow<ResultWrapper<Boolean>>

    suspend fun updatePassword(newPassword: String): Flow<ResultWrapper<Boolean>>
    suspend fun updateEmail(newEmail: String): Flow<ResultWrapper<Boolean>>

    fun sendChangePasswordRequestByEmail(): Boolean
}

class UserRepositoryImpl(private val firebaseAuthDataSource: FirebaseAuthDataSource) : UserRepository {
    override suspend fun doLogin(email: String, password: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { firebaseAuthDataSource.doLogin(email, password) }
    }

    override suspend fun doRegister(
        fullName: String,
        email: String,
        password: String
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { firebaseAuthDataSource.doRegister(fullName, email, password) }
    }

    override fun doLogout(): Boolean {
        return firebaseAuthDataSource.doLogout()
    }

    override fun isLoggedIn(): Boolean {
        return firebaseAuthDataSource.isLoggedIn()
    }

    override fun getCurrentUser(): UserViewParam? {
        return firebaseAuthDataSource.getCurrentUser().toUserViewParam()
    }

    override suspend fun updateProfile(
        fullName: String?,
        photoUri: Uri?
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { firebaseAuthDataSource.updateProfile(fullName, photoUri) }
    }

    override suspend fun updatePassword(newPassword: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { firebaseAuthDataSource.updatePassword(newPassword) }
    }
    override suspend fun updateEmail(newEmail: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { firebaseAuthDataSource.updateEmail(newEmail) }
    }
    override fun sendChangePasswordRequestByEmail(): Boolean {
        return firebaseAuthDataSource.sendChangePasswordRequestByEmail()
    }
}
