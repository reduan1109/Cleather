package com.example.cleather.database.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cleather.database.data.clothes.Clothes
import com.example.cleather.database.data.clothes.ClothesDao
import com.example.cleather.enumClasses.ItemBodyPart
import com.example.cleather.enumClasses.ItemSpecialFeature
import com.example.cleather.enumClasses.ItemType
import com.example.cleather.enumClasses.ItemWarmness
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.hasItems
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CleatherDatabaseTest : TestCase() {
    private lateinit var database: CleatherDatabase
    private lateinit var clothesDao: ClothesDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()


        // makes a temporary database for testing
        database = Room.inMemoryDatabaseBuilder(context, CleatherDatabase::class.java).build()
        clothesDao = database.clothesDao()

    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun writeAndReadClothes() = runBlocking {
        val clothes = Clothes(
            1,
            false,
            "t-shorte",
            "tshirt1",
            ItemType.CLOTHING_UNDERWEAR,
            ItemWarmness.COLD,
            ItemBodyPart.UPPER_BODY,
            listOf(ItemSpecialFeature.NA)
        )
        clothesDao.addClothes(clothes)
        val info = clothesDao.getAllData()

        // checks if info has the item clothes which was added
        assertThat(info, hasItems(clothes))

        // checks the length of the list
        assertThat(info.size, equalTo(1))
    }
}