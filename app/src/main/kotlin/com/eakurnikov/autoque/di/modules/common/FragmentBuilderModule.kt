package com.eakurnikov.autoque.di.modules.common

import com.eakurnikov.common.di.annotations.FragmentScope
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

/**
 * Created by eakurnikov on 2019-10-07
 */
@Module(includes = [AndroidInjectionModule::class])
interface FragmentBuilderModule {

//    @FragmentScope
//    @ContributesAndroidInjector
//    fun bindSomeFragment(): SomeFragment
}