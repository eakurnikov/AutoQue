package com.eakurnikov.autoque.autofill.impl.di.modules

import com.eakurnikov.autoque.autofill.impl.internal.ui.disclaimer.DisclaimerActivity
import com.eakurnikov.autoque.autofill.impl.internal.ui.update.UpdatePromptActivity
import com.eakurnikov.common.di.annotations.ActivityScope
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

/**
 * Created by eakurnikov on 2019-09-15
 */
@Module(includes = [AndroidInjectionModule::class])
interface ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector
    fun bindDisclaimerActivity(): DisclaimerActivity

    @ActivityScope
    @ContributesAndroidInjector
    fun bindUpdatePromptActivity(): UpdatePromptActivity

//    @ActivityScope
//    @ContributesAndroidInjector
//    fun bindShowAllActivity(): ShowAllActivity
}