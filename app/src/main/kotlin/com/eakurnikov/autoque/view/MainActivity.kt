package com.eakurnikov.autoque.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eakurnikov.autoque.R
import com.eakurnikov.autoque.autofill.impl.data.Resource
import com.eakurnikov.autoque.data.entity.AccountRoomEntity
import com.eakurnikov.autoque.data.entity.LoginRoomEntity
import com.eakurnikov.autoque.view.base.BaseActivity
import com.eakurnikov.autoque.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>() {

    override lateinit var viewModel: MainViewModel

    private val adapter: AccountsAdapter = AccountsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(MainViewModel::class.java)

        initViews()
    }

    override fun subscribe() {
        subscribe(
            viewModel.accountsSubject.subscribe(
                { resource: Resource<List<AccountRoomEntity>> ->
                    when (resource) {
                        is Resource.Success -> {
                            adapter.apply {
                                data = resource.data
                                notifyDataSetChanged()
                            }
                        }
                        is Resource.Loading -> {
                            adapter.apply {
                                data = listOf()
                                notifyDataSetChanged()
                            }
                        }
                        is Resource.Error -> {
                            adapter.apply {
                                data = listOf()
                                notifyDataSetChanged()
                            }
                        }
                    }
                },
                { error: Throwable ->
                    adapter.apply {
                        data = listOf()
                        notifyDataSetChanged()
                    }
                }
            )
        )
    }

    private fun initViews() {
        list_accounts.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private class AccountViewHolder
    constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item: TextView = itemView.findViewById(R.id.item)
        var login: TextView = itemView.findViewById(R.id.login)
        var password: TextView = itemView.findViewById(R.id.password)

        fun bind(position: Int, accountEntity: AccountRoomEntity) {
            item.setBackgroundColor(if (position % 2 == 0) Color.WHITE else Color.GRAY)
//            login.text = loginEntity.login
//            password.text = loginEntity.password
        }
    }

    private class AccountsAdapter : RecyclerView.Adapter<AccountViewHolder>() {

        var data: List<AccountRoomEntity> = listOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder =
            AccountViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_account, parent, false)
            )

        override fun onBindViewHolder(viewHolder: AccountViewHolder, position: Int): Unit =
            viewHolder.bind(position, data[position])

        override fun getItemCount(): Int = data.size
    }
}