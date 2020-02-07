package com.eakurnikov.autoque.domain.csv

import android.net.Uri

interface CsvParser<Entity> {

    fun parse(uri: Uri): List<Entity>?
}