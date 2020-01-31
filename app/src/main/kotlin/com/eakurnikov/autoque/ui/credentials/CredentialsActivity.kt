package com.eakurnikov.autoque.ui.credentials

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.eakurnikov.autoque.R
import com.eakurnikov.autoque.autofill.api.api.AutofillFeatureApi
import com.eakurnikov.autoque.data.model.Credentials
import com.eakurnikov.common.data.Resource
import com.eakurnikov.autoque.ui.base.BaseActivity
import com.eakurnikov.autoque.ui.view.popup.PopupFactory
import com.eakurnikov.autoque.viewmodel.credentials.CredentialsViewModel
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_credentials.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-12-15
 */
class CredentialsActivity : BaseActivity<CredentialsViewModel>() {

    companion object {
        fun start(context: Context): Unit =
            context.startActivity(Intent(context, CredentialsActivity::class.java))
    }

    @Inject
    lateinit var autofillApi: AutofillFeatureApi

    @Inject
    lateinit var popupFactory: PopupFactory

    override lateinit var viewModel: CredentialsViewModel

    private val adapter: CredentialsAdapter = CredentialsAdapter()

    private var viewModelDisposable: Disposable? = null

    private val promptAutofillServiceSelection: () -> Unit = {
        if (::autofillApi.isInitialized) {
            with(autofillApi) {
                if (!autofillServiceEnabler.isEnabled) {
                    autofillServiceEnabler.isEnabled = true
                }

                if (!autofillServiceSelector.isSelected) {
                    autofillServiceSelector.promptSelection(this@CredentialsActivity, 0)
                    popupFactory.createAutofillPopup().show()
                }
            }
        }
    }

    private val onCredentials = object : DisposableObserver<Resource<List<Credentials>>>() {
        override fun onComplete() {
        }

        override fun onNext(resource: Resource<List<Credentials>>) {
            when (resource) {
                is Resource.Success -> {
                    when (resource.data.size) {
                        0 -> showEmptyList()
                        else -> showPosts(resource.data)
                    }
                }
                is Resource.Loading -> showLoading(false)
                is Resource.Error -> showError(resource.message)
            }
        }

        override fun onError(error: Throwable) {
            showError(error.message ?: getString(R.string.error))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credentials)

        AndroidInjection.inject(this)

        viewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(CredentialsViewModel::class.java)

        initViews()

        Handler(mainLooper).postDelayed(
            promptAutofillServiceSelection,
            TimeUnit.SECONDS.toMillis(2)
        )
    }

    override fun onStart() {
        super.onStart()
        viewModelDisposable = viewModel.credentialsSubject
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(onCredentials)
    }

    override fun onStop() {
        super.onStop()
        viewModelDisposable?.dispose()
        viewModelDisposable = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0) {
            val isSelected: Boolean = resultCode == Activity.RESULT_OK
            autofillApi.autofillServiceSelector.onSelection(isSelected)

//            todo
//            AutofillPopup.Companion.cancelIfNecessary()

            Toast.makeText(
                this@CredentialsActivity,
                "AutoQue Autofill Service ${if (isSelected) "selected" else "selection canceled"}",
                Toast.LENGTH_LONG
            ).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initViews() {
        showLoading(false)

        layout_refresh_credentials.setOnRefreshListener {
            showLoading(true)
            viewModel.onRefresh()
        }

        list_credentials.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@CredentialsActivity)
            adapter = this@CredentialsActivity.adapter
        }
    }

    private fun showLoading(isRefresh: Boolean) {
        if (isRefresh) {
            layout_refresh_credentials.isRefreshing = true
            progress_bar_credentials.visibility = View.GONE
        } else if (!layout_refresh_credentials.isRefreshing) {
            progress_bar_credentials.visibility = View.VISIBLE
        }
    }

    private fun showPosts(posts: List<Credentials>) {
        layout_refresh_credentials.isRefreshing = false
        progress_bar_credentials.visibility = View.GONE
        list_credentials.visibility = View.VISIBLE
        tv_credentials_error.visibility = View.GONE

        adapter.apply {
            data = posts
            notifyDataSetChanged()
        }
    }

    private fun showEmptyList() {
        layout_refresh_credentials.isRefreshing = false
        progress_bar_credentials.visibility = View.GONE
        list_credentials.visibility = View.GONE
        tv_credentials_error.visibility = View.VISIBLE
        tv_credentials_error.text = getString(R.string.no_entries)
    }

    private fun showError(message: String) {
        layout_refresh_credentials.isRefreshing = false
        progress_bar_credentials.visibility = View.GONE
        list_credentials.visibility = View.GONE
        tv_credentials_error.visibility = View.VISIBLE
        tv_credentials_error.text = message
    }
}