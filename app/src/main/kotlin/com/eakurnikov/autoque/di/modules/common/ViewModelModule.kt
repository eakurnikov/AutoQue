package com.eakurnikov.autoque.di.modules.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eakurnikov.autoque.viewmodel.credentials.CredentialsViewModel
import com.eakurnikov.autoque.viewmodel.base.ViewModelFactory
import com.eakurnikov.common.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by eakurnikov on 2019-10-07
 */
@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CredentialsViewModel::class)
    fun bindCredentialsViewModel(credentialsViewModel: CredentialsViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}