package com.eakurnikov.autoque.ui.credentials

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eakurnikov.autoque.R
import com.eakurnikov.autoque.data.model.Credentials

/**
 * Created by eakurnikov on 2019-12-15
 */
class CredentialsViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(parent: ViewGroup) = CredentialsViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_credentials, parent, false)
        )
    }

    var item: LinearLayout = itemView.findViewById(R.id.item)
    var login: TextView = itemView.findViewById(R.id.login)
    var password: TextView = itemView.findViewById(R.id.password)

    fun bind(position: Int, credentials: Credentials) {
        item.setBackgroundColor(itemView.context.getColor(R.color.lightest_gray))
        login.text = credentials.login
        password.text = credentials.password
    }
}