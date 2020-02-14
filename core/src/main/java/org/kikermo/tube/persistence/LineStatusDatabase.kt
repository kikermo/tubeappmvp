package org.kikermo.tube.persistence

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database
import org.kikermo.tube.persistence.model.LineDBModel

@Database(entities = [LineDBModel::class], version = 1, exportSchema = false)
abstract class LineStatusDatabase : RoomDatabase() {

    abstract fun lineDao(): LineDao

    companion object {

        @Volatile
        private var INSTANCE: LineStatusDatabase? = null

        fun getInstance(context: Context): LineStatusDatabase? {
            if (INSTANCE == null) {
                synchronized(LineStatusDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            LineStatusDatabase::class.java, "Line.db"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }

}
