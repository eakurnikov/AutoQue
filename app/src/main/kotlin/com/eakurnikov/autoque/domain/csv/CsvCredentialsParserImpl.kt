package com.eakurnikov.autoque.domain.csv

import android.content.Context
import android.net.Uri
import android.os.ParcelFileDescriptor
import com.eakurnikov.autoque.data.model.Credentials
import com.eakurnikov.common.annotations.AppContext
import java.io.InputStreamReader
import javax.inject.Inject

class CsvCredentialsParserImpl @Inject constructor(
    @AppContext private val context: Context
) : CsvParser<Credentials> {

    override fun parse(uri: Uri): List<Credentials>? {
        val entities: MutableList<Credentials> = arrayListOf()

        val fileDescriptor: ParcelFileDescriptor =
            context.contentResolver.openFileDescriptor(uri, "r") ?: return null

        val reader = InputStreamReader(ParcelFileDescriptor.AutoCloseInputStream(fileDescriptor))

        var data: Int = reader.read()
        while (data.toChar() != '\n' && data != -1) {
            data = reader.read()
        }

        if (data == -1) {
            return null
        }

        data = reader.read()
        while (data != -1) {

            var name = ""
            while (data.toChar() != ',') {
                name += data.toChar()
                data = reader.read()
            }

            var url = ""
            data = reader.read()
            while (data.toChar() != ',') {
                url += data.toChar()
                data = reader.read()
            }

            var login = ""
            data = reader.read()
            while (data.toChar() != ',') {
                login += data.toChar()
                data = reader.read()
            }

            var password = ""
            data = reader.read()
            while (data.toChar() != '\n' && data != -1) {
                password += data.toChar()
                data = reader.read()
            }

            if (data != -1) {
                data = reader.read()
            }

            if (name != "" && url != "" && login != "" && password != "") {
                entities.add(Credentials(name, url, login, password))
            }
        }

        reader.close()

        return entities
    }
}