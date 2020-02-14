package org.kikermo.tube.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe
import org.kikermo.tube.persistence.model.LineDBModel

@Dao
interface LineDao {
    @Query("SELECT * FROM lines")
    fun getAllLines(): Maybe<List<LineDBModel>>

    @Query("SELECT * FROM lines WHERE id LIKE :id")
    fun getLine(id: String): Maybe<LineDBModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLine(line: LineDBModel): Completable
}
