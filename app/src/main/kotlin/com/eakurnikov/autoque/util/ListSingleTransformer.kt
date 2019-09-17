package com.eakurnikov.autoque.util

import io.reactivex.Single
import io.reactivex.SingleTransformer

/**
 * Created by eakurnikov on 2019-09-15
 */
class ListSingleTransformer<UpstreamItem, DownstreamItem>(
    private val converter: (UpstreamItem) -> DownstreamItem
) : SingleTransformer<List<UpstreamItem>, List<DownstreamItem>> {

    override fun apply(upstream: Single<List<UpstreamItem>>): Single<List<DownstreamItem>> {
        return upstream.map { upstreamList: List<UpstreamItem> ->
            upstreamList.map { upstreamItem: UpstreamItem ->
                converter.invoke(upstreamItem)
            }
        }
    }
}