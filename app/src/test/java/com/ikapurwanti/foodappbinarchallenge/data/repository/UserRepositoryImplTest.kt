package com.ikapurwanti.foodappbinarchallenge.data.repository

import android.net.Uri
import app.cash.turbine.test
import com.google.firebase.auth.FirebaseUser
import com.ikapurwanti.foodappbinarchallenge.data.network.firebase.auth.datasource.FirebaseAuthDataSource
import com.ikapurwanti.foodappbinarchallenge.model.UserViewParam
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {

    @MockK
    private lateinit var dataSource: FirebaseAuthDataSource

    private lateinit var userRepo: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userRepo = UserRepositoryImpl(dataSource)
    }

    @Test
    fun `test do login`() {
        runTest {
            coEvery { dataSource.doLogin(any(), any()) } returns true

            userRepo.doLogin("email", "password")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertEquals(result.payload, true)
                    coVerify { dataSource.doLogin(any(), any()) }
                }
        }
    }

    @Test
    fun `test do register`() {
        runTest {
            coEvery { dataSource.doRegister(any(), any(), any()) } returns true

            userRepo.doRegister("name", "email", "password")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertEquals(result.payload, true)
                    coVerify { dataSource.doRegister(any(), any(), any()) }
                }
        }
    }

    @Test
    fun `test do logout`() {
        runTest {
            every { dataSource.doLogout() } returns true

            val result = userRepo.doLogout()
            verify { dataSource.doLogout() }
            assertEquals(result, true)
        }
    }

    @Test
    fun `test is loggedIn`() {
        runTest {
            every { dataSource.isLoggedIn() } returns true

            val result = userRepo.isLoggedIn()
            verify { dataSource.isLoggedIn() }
            assertEquals(result, true)
        }
    }

    @Test
    fun `test get current user`() {
        val userViewParamMock = UserViewParam(
            fullName = "name",
            photoUrl = Uri.parse(""),
            email = "email"
        )
        runTest {
            val mockFirebaseUser = mockk<FirebaseUser>()
            every { mockFirebaseUser.displayName } returns userViewParamMock.fullName
            every { mockFirebaseUser.photoUrl } returns userViewParamMock.photoUrl
            every { mockFirebaseUser.email } returns userViewParamMock.email
            every { dataSource.getCurrentUser() } returns mockFirebaseUser

            val result = userRepo.getCurrentUser()
            verify { dataSource.getCurrentUser() }
            assertEquals(result, userViewParamMock ?: null)
            every { mockFirebaseUser.displayName }
        }
    }

    @Test
    fun `test update profile`() {
        runTest {
            coEvery { dataSource.updateProfile(any(), any()) } returns true

            userRepo.updateProfile("name", Uri.parse(""))
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertEquals(result.payload, true)
                    coVerify { dataSource.updateProfile(any(), any()) }
                }
        }
    }

    @Test
    fun `test update password`() {
        runTest {
            coEvery { dataSource.updatePassword(any()) } returns true

            userRepo.updatePassword("new pass")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertEquals(result.payload, true)
                    coVerify { dataSource.updatePassword(any()) }
                }
        }
    }

    @Test
    fun `test update email`() {
        runTest {
            coEvery { dataSource.updateEmail(any()) } returns true

            userRepo.updateEmail("new email")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertEquals(result.payload, true)
                    coVerify { dataSource.updateEmail(any()) }
                }
        }
    }

    @Test
    fun `test send change pass request by email`() {
        runTest {
            every { dataSource.sendChangePasswordRequestByEmail() } returns true

            val result = userRepo.sendChangePasswordRequestByEmail()
            verify { dataSource.sendChangePasswordRequestByEmail() }
            assertEquals(result, true)
        }
    }
}
