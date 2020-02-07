package com.eakurnikov.autoque.di.modules.common

import android.content.Context
import com.eakurnikov.autoque.domain.popup.PopupNotificationManager
import com.eakurnikov.autoque.domain.popup.PopupNotificationManagerImpl
import com.eakurnikov.autoque.ui.view.popup.PopupFactory
import com.eakurnikov.common.annotations.AppContext
import com.eakurnikov.common.di.annotations.AppScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [PopupModule.Declarations::class])
class PopupModule {

    @Provides
    @AppScope
    fun providePopupFactory(
        @AppContext context: Context,
        popupNotificationManager: PopupNotificationManager
    ): PopupFactory = PopupFactory(context, popupNotificationManager)

    @Module
    interface Declarations {

        @Binds
        @AppScope
        fun bindPopupNotificationManager(
            impl: PopupNotificationManagerImpl
        ): PopupNotificationManager
    }
}