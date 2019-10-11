package com.eakurnikov.autoque.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eakurnikov.autoque.R
import com.eakurnikov.autoque.autofill.api.api.AutofillFeatureApi
import com.eakurnikov.autoque.autofill.api.api.selector.AutofillServiceSelector
import com.eakurnikov.autoque.autofill.impl.data.Resource
import com.eakurnikov.autoque.data.entity.LoginRoomEntity
import com.eakurnikov.autoque.view.base.BaseActivity
import com.eakurnikov.autoque.viewmodel.MainViewModel
import dagger.android.AndroidInjection
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewModel>() {

    @Inject
    lateinit var autofillApi: AutofillFeatureApi

    override lateinit var viewModel: MainViewModel

    private val adapter: AccountsAdapter = AccountsAdapter()

    private var viewModelDisposable: Disposable? = null
    private var autofillServiceSelectorDisposable: Disposable? = null

    private val promptAutofillServiceSelection: () -> Unit = {
        if (::autofillApi.isInitialized) {
            with(autofillApi) {
                if (!autofillServiceRegistrar.isRegistered) {
                    autofillServiceRegistrar.isRegistered = true
                }

                if (!autofillServiceSelector.isSelected) {
                    autofillServiceSelector.promptSelection(this@MainActivity)
                }
            }
        }
    }

    private val onAccounts = object : DisposableObserver<Resource<List<LoginRoomEntity>>>() {
        override fun onComplete() {
        }

        override fun onNext(resource: Resource<List<LoginRoomEntity>>) {
            when (resource) {
                is Resource.Success -> {
                    label_loading.visibility = View.GONE
                    label_error.visibility = View.GONE

                    when (resource.data.size) {
                        0 -> {
                            label_empty.visibility = View.VISIBLE
                            list_accounts.visibility = View.GONE
                        }
                        else -> {
                            label_empty.visibility = View.GONE
                            list_accounts.visibility = View.VISIBLE

                            adapter.apply {
                                data = resource.data
                                notifyDataSetChanged()
                            }
                        }
                    }
                }
                is Resource.Loading -> {
                    label_loading.visibility = View.VISIBLE
                    label_error.visibility = View.GONE
                    label_empty.visibility = View.GONE
                    list_accounts.visibility = View.GONE
                }
                is Resource.Error -> {
                    label_loading.visibility = View.GONE
                    label_error.visibility = View.VISIBLE
                    label_empty.visibility = View.GONE
                    list_accounts.visibility = View.GONE

                    label_loading.text = resource.message
                }
            }
        }

        override fun onError(error: Throwable) {
            label_loading.visibility = View.GONE
            label_error.visibility = View.VISIBLE
            label_empty.visibility = View.GONE
            list_accounts.visibility = View.GONE

            label_loading.text = error.message ?: getString(R.string.error)
        }
    }

    private val onAutofillServiceSelection = object : DisposableObserver<AutofillServiceSelector.SelectionStatus>() {
        override fun onComplete() {
        }

        override fun onNext(status: AutofillServiceSelector.SelectionStatus) {
            Toast.makeText(
                this@MainActivity,
                "AutoQue Autofill Service ${if (status.isSelected) "selected" else "selection canceled"}",
                Toast.LENGTH_LONG
            ).show()
            disposeAutofillServiceSelector()
        }

        override fun onError(error: Throwable) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)

        viewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(MainViewModel::class.java)

        initViews()

        Handler(mainLooper).postDelayed(promptAutofillServiceSelection, TimeUnit.SECONDS.toMillis(2))
    }

    override fun onStart() {
        super.onStart()
        viewModelDisposable = viewModel.accountsSubject.subscribeWith(onAccounts)
    }

    override fun onStop() {
        super.onStop()
        viewModelDisposable?.dispose()
        viewModelDisposable = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        autofillServiceSelectorDisposable = autofillApi
            .autofillServiceSelector
            .onSelection(requestCode, resultCode, data)
            ?.subscribeWith(onAutofillServiceSelection)

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initViews() {
        list_accounts.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun disposeAutofillServiceSelector() {
        autofillServiceSelectorDisposable?.dispose()
        autofillServiceSelectorDisposable = null
    }

    private class AccountViewHolder
    constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item: LinearLayout = itemView.findViewById(R.id.item)
        var login: TextView = itemView.findViewById(R.id.login)
        var password: TextView = itemView.findViewById(R.id.password)

        fun bind(position: Int, loginEntity: LoginRoomEntity) {
            item.setBackgroundColor(if (position % 2 == 0) Color.WHITE else Color.GRAY)
            login.text = loginEntity.login
            password.text = loginEntity.password
        }
    }

    private class AccountsAdapter : RecyclerView.Adapter<AccountViewHolder>() {
        var data: List<LoginRoomEntity> = listOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder =
            AccountViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_account, parent, false)
            )

        override fun onBindViewHolder(viewHolder: AccountViewHolder, position: Int): Unit =
            viewHolder.bind(position, data[position])

        override fun getItemCount(): Int = data.size
    }
}