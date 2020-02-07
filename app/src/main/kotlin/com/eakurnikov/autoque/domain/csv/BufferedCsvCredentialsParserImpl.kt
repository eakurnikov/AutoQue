package com.eakurnikov.autoque.domain.csv

import android.content.Context
import android.net.Uri
import android.os.ParcelFileDescriptor
import com.eakurnikov.autoque.data.model.Credentials
import com.eakurnikov.common.annotations.AppContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject

class BufferedCsvCredentialsParserImpl @Inject constructor(
    @AppContext private val context: Context
) : CsvParser<Credentials> {

    override fun parse(uri: Uri): List<Credentials>? {
        val entities: MutableList<Credentials> = arrayListOf()

        val fileDescriptor: ParcelFileDescriptor =
            context.contentResolver.openFileDescriptor(uri, "r") ?: return null

        val reader = BufferedReader(
            InputStreamReader(ParcelFileDescriptor.AutoCloseInputStream(fileDescriptor))
        )

        try {
            var line: String? = reader.readLine()
            if (line != null) line = reader.readLine() //skip first line
            while (line != null) {
                line.parseLine()?.let { entities.add(it) }
                line = reader.readLine()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            reader.close()
        }

        return entities
    }

    private fun String.parseLine(): Credentials? {
        val (name, url, login, password) = split(',', ignoreCase = false, limit = 4)

        if (name == "") return null
        if (url == "") return null
        if (login == "") return null
        if (password == "") return null

        return Credentials(name, url, login, password)
    }
}