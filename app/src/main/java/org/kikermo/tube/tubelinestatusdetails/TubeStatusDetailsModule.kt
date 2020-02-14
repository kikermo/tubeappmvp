package org.kikermo.tube.tubelinestatusdetails

import androidx.appcompat.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import org.kikermo.tube.base.Reducer
import org.kikermo.tube.base.Transformer
import org.kikermo.tube.base.getNavigationAction
import org.kikermo.tube.navigation.TubeStatusDetailsNavigationAction

@Module
abstract class TubeStatusDetailsModule {

    @Binds
    internal abstract fun bindsReducer(reducer: TubeStatusDetailsScreenReducer): Reducer<TubeStatusDetailsScreenState>

    @Binds
    internal abstract fun bidndsTransformer(transformer: TubeStatusDetailsTransformer): Transformer<TubeStatusDetailsScreenState>

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideContext(activity: TubeStatusDetailsActivity): AppCompatActivity {
            return activity
        }

        @Provides
        @JvmStatic
        fun provideNavigationAction(activity: TubeStatusDetailsActivity): TubeStatusDetailsNavigationAction {
            return activity.intent.getNavigationAction()
                ?: throw IllegalArgumentException("You have to pass a LookDetailsNavigationAction to this activity in the bundle")
        }

    }
}
