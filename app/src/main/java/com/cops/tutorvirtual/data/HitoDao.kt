package com.cops.tutorvirtual.data


import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao

interface HitoDao{
    @Query("SELECT * FROM hitos ORDER BY id DESC")
    fun getAllHitos(): Flow<List<HitoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHito(hito: HitoEntity)

    @Update
    suspend fun updateHito(hito: HitoEntity)

    @Delete
    suspend fun deleteHito(hito: HitoEntity)

}


