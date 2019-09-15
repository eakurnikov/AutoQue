package com.eakurnikov.autoque.autofill.impl.util

import android.service.autofill.FillResponse
import com.eakurnikov.autoque.autofill.impl.data.Resource
import com.eakurnikov.autoque.autofill.impl.data.model.FillDataEntity

/**
 * Created by eakurnikov on 2019-09-15
 */

typealias FillDataResource = Resource<List<FillDataEntity>>
typealias FillResponseResource = Resource<FillResponse>
typealias SaveResource = Resource<Unit>