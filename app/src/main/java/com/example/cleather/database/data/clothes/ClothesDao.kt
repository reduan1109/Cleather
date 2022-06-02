package com.example.cleather.database.data.clothes

import androidx.room.*

/* interface which is used for the DAOs (Data Access Objects) that used to access your application's
 persisted data */
@Dao
interface ClothesDao {
    // custom query which is used to get all the information from the database
    @Query("SELECT * FROM clothes_table ORDER BY itemId ASC")
    fun getAllData(): List<Clothes>

    // unused, would be used if we made a way for the user to add clothes
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addClothes(clothes: Clothes)

    // unused, would be used if we made a way for the user to delete clothes
    @Delete
    suspend fun deleteClothes(clothes: Clothes)
}