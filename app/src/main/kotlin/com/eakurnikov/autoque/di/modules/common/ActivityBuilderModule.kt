package com.eakurnikov.autoque.di.modules.common

import com.eakurnikov.autoque.ui.auth.AuthActivity
import com.eakurnikov.autoque.ui.autofill.AutofillDisclaimerActivity
import com.eakurnikov.autoque.ui.autofill.AutofillUpdatePromptActivity
import com.eakurnikov.autoque.ui.credentials.CredentialsActivity
import com.eakurnikov.autoque.ui.splash.SplashActivity
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
    fun bindSplashActivity(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector
    fun bindAuthActivity(): AuthActivity

    @ActivityScope
    @ContributesAndroidInjector
    fun bindCredentialsActivity(): CredentialsActivity

    @ActivityScope
    @ContributesAndroidInjector
    fun bindAutofillDisclaimerActivity(): AutofillDisclaimerActivity

    @ActivityScope
    @ContributesAndroidInjector
    fun bindAutofillUpdatePromptActivity(): AutofillUpdatePromptActivity
}