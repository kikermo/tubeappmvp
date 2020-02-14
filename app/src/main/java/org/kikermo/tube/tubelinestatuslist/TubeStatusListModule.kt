package org.kikermo.tube.tubelinestatuslist

import androidx.appcompat.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import org.kikermo.tube.base.Reducer
import org.kikermo.tube.base.Transformer

@Module
abstract class TubeStatusListModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideContext(activity: TubeStateListActivity): AppCompatActivity {
            return activity
        }
    }
}
