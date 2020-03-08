package com.eakurnikov.autoque.di.modules.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eakurnikov.autoque.viewmodel.credentials.CredentialsViewModel
import com.eakurnikov.common.annotations.ViewModelKey
import com.eakurnikov.common.ui.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by eakurnikov on 2019-10-07
 */
@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CredentialsViewModel::class)
    fun bindCredentialsViewModel(credentialsViewModel: CredentialsViewModel): ViewModel
}