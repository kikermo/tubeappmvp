package org.kikermo.tube.persistence.di

import android.content.Context
import dagger.Module
import dagger.Provides
import org.kikermo.tube.persistence.LineDao
import org.kikermo.tube.persistence.LineStatusDatabase
import javax.inject.Singleton

@Module
class DaoModule {

    @Provides
    @Singleton
    fun providesDao(context: Context): LineDao {
        return LineStatusDatabase.getInstance(context)!!.lineDao()
    }
}
