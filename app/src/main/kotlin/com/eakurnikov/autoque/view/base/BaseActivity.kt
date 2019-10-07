package com.eakurnikov.autoque.view.base

import androidx.lifecycle.ViewModelProvider
import com.eakurnikov.autoque.viewmodel.base.BaseViewModel
import dagger.android.DaggerActivity
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-10-07
 */
abstract class BaseActivity<ViewModel : BaseViewModel> : DaggerAppCompatActivity() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected abstract var viewModel: ViewModel

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
        subscribe()
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
        dispose()
    }

    protected open fun subscribe() = Unit

    protected open fun subscribe(disposable: Disposable) {
        disposables.add(disposable)
    }

    protected open fun dispose() {
        disposables.dispose()
    }
}