package com.eakurnikov.autoque.ui.credentials

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eakurnikov.autoque.data.model.Credentials

/**
 * Created by eakurnikov on 2019-12-15
 */
class CredentialsAdapter : RecyclerView.Adapter<CredentialsViewHolder>() {
    var data: List<Credentials> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CredentialsViewHolder =
        CredentialsViewHolder.create(parent)

    override fun onBindViewHolder(viewHolder: CredentialsViewHolder, position: Int): Unit =
        viewHolder.bind(position, data[position])

    override fun getItemCount(): Int = data.size
}