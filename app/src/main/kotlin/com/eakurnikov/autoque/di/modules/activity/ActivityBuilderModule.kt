package com.eakurnikov.autoque.di.modules.activity

import com.eakurnikov.autoque.view.AuthActivity
import com.eakurnikov.autoque.view.SplashActivity
import com.eakurnikov.common.di.annotations.ScreenScope
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by eakurnikov on 2019-09-15
 */
@Module(includes = [AndroidSupportInjectionModule::class])
interface ActivityBuilderModule {

    @ScreenScope
    @ContributesAndroidInjector
    fun bindSplashActivity(): SplashActivity

    @ScreenScope
    @ContributesAndroidInjector
    fun bindAuthActivity(): AuthActivity
}