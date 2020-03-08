package com.eakurnikov.autoque.autofill.impl.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eakurnikov.autoque.autofill.impl.internal.viewmodel.disclaimer.DisclaimerViewModel
import com.eakurnikov.autoque.autofill.impl.internal.viewmodel.update.UpdatePromptViewModel
import com.eakurnikov.common.annotations.ViewModelKey
import com.eakurnikov.common.ui.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by eakurnikov on 2019-09-14
 */
@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UpdatePromptViewModel::class)
    fun bindAutofillUpdatePromptViewModel(viewModel: UpdatePromptViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DisclaimerViewModel::class)
    fun bindAutofillDisclaimerViewModel(viewModel: DisclaimerViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(ShowAllViewModel::class)
//    fun bindAutofillShowAllViewModel(viewModel: ShowAllViewModel): ViewModel
}