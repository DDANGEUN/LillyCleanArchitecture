package lilly.cleanarchitecture.data.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import lilly.cleanarchitecture.data.model.TextEntity

@Dao
interface TextDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTexts(texts: TextEntity): Long

    @Query("SELECT * FROM text ORDER BY id DESC")
    fun getAllTexts(): Flow<List<TextEntity>>

    @Query("SELECT * FROM text WHERE content LIKE '%' || :content || '%' ORDER BY id DESC")
    fun getTextsByContent(content: String): Flow<List<TextEntity>>

    @Delete
    fun delete(texts: TextEntity)

    @Query("DELETE FROM text")
    fun deleteAllTexts()
}