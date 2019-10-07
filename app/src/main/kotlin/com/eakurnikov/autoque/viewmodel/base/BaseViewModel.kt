package com.eakurnikov.autoque.viewmodel.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by eakurnikov on 2019-10-05
 */
abstract class BaseViewModel : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    open fun onStart() {
        subscribe()
    }

    open fun onStop() {
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