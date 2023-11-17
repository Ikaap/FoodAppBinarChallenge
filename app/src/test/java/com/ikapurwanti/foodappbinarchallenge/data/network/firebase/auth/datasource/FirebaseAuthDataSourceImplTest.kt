package com.ikapurwanti.foodappbinarchallenge.data.network.firebase.auth.datasource

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.lang.Exception

class FirebaseAuthDataSourceImplTest {

    @MockK(relaxed = true)
    lateinit var firebaseAuth: FirebaseAuth

    private lateinit var dataSource: FirebaseAuthDataSource

    private val firebaseUserMock = mockk<FirebaseUser>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
    }

    private fun mockTaskVoid(exception: Exception? = null): Task<Void> {
        val task: Task<Void> = mockk(relaxed = true)
        every { task.isComplete } returns true
        every { task.exception } returns exception
        every { task.isCanceled } returns false
        val relaxedVoid: Void = mockk(relaxed = true)
        every { task.result } returns relaxedVoid
        return task
    }

    private fun mockTaskAuthResult(exception: Exception? = null): Task<AuthResult> {
        val task: Task<AuthResult> = mockk(relaxed = true)
        every { task.isComplete } returns true
        every { task.exception } returns exception
        every { task.isCanceled } returns false
        val relaxedResult: AuthResult = mockk(relaxed = true)
        every { task.result } returns relaxedResult
        every { task.result.user } returns mockk(relaxed = true)
        return task
    }

    @Test
    fun `test login`() {
        runTest {
            every { firebaseAuth.signInWithEmailAndPassword(any(), any()) } returns mockTaskAuthResult()

            val result = dataSource.doLogin("email", "password")
            Assert.assertEquals(result, true)
            verify { firebaseAuth.signInWithEmailAndPassword(any(), any()) }
        }
    }

    @Test
    fun `test get current user`() {
        runTest {
            every { firebaseAuth.currentUser } returns firebaseUserMock

            val result = dataSource.getCurrentUser()
            Assert.assertEquals(result, firebaseUserMock)
            verify { firebaseAuth.currentUser }
        }
    }

    @Test
    fun `test is logged in`() {
        runTest {
            every { firebaseAuth.currentUser } returns firebaseUserMock

            val result = dataSource.isLoggedIn()
            Assert.assertEquals(result, true)
            verify { firebaseAuth.currentUser }
        }
    }

    @Test
    fun `test update profile`() {
        runTest {
            coEvery { firebaseAuth.currentUser?.updateProfile(any()) } returns mockTaskVoid()

            val result = dataSource.updateProfile("name", null)
            Assert.assertEquals(result, true)
            coVerify { firebaseAuth.currentUser?.updateProfile(any()) }
        }
    }

    @Test
    fun `test update password`() {
        runTest {
            coEvery { firebaseAuth.currentUser?.updatePassword(any()) } returns mockTaskVoid()

            val result = dataSource.updatePassword("new pass")
            Assert.assertEquals(result, true)
            coVerify { firebaseAuth.currentUser?.updatePassword(any()) }
        }
    }

    @Test
    fun `test update email`() {
        runTest {
            coEvery { firebaseAuth.currentUser?.updateEmail(any()) } returns mockTaskVoid()

            val result = dataSource.updateEmail("new email")
            Assert.assertEquals(result, true)
            coVerify { firebaseAuth.currentUser?.updateEmail(any()) }
        }
    }

    @Test
    fun `test send change password by email`() {
        runTest {
            coEvery { firebaseAuth.currentUser?.email } returns "string"

            val result = dataSource.sendChangePasswordRequestByEmail()
            Assert.assertEquals(result, true)
            coVerify { firebaseAuth.currentUser?.email }
        }
    }

    @Test
    fun `test register`() {
        runTest {
            mockkConstructor(UserProfileChangeRequest.Builder::class)

            val mockAuthResult = mockTaskAuthResult()
            every { firebaseAuth.createUserWithEmailAndPassword(any(), any()) } returns mockAuthResult

            val mockUser = mockk<FirebaseUser>(relaxed = true)
            every { mockAuthResult.result.user } returns mockUser

            every { mockUser.updateProfile(any()) } returns mockTaskVoid()

            val result = dataSource.doRegister("name", "email", "password")
            Assert.assertEquals(result, true)

            verify { firebaseAuth.createUserWithEmailAndPassword(any(), any()) }
            verify { mockAuthResult.result.user }
            verify { mockUser.updateProfile(any()) }
        }
    }

    @Test
    fun `test is logout`() {
        runTest {
            mockkStatic(FirebaseAuth::class)
            every { FirebaseAuth.getInstance() } returns firebaseAuth
            every { firebaseAuth.signOut() } returns Unit
            val result = dataSource.doLogout()
            Assert.assertEquals(result, true)
        }
    }
}
