package com.ikapurwanti.foodappbinarchallenge.data.local.datastore

import com.ikapurwanti.foodappbinarchallenge.utils.PreferenceDataStoreHelper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class AppPreferenceDataSourceImplTest {

    @MockK
    private lateinit var dataHelper : PreferenceDataStoreHelper

    private lateinit var appDataSource : AppPreferenceDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        appDataSource = AppPreferenceDataSourceImpl(dataHelper)
    }

//    @Test
//    fun `get app layout flow`(){
//        runTest {
//            every { dataHelper.getPreference(any(), any()) } returns
//
//        }
//    }


}