package com.eakurnikov.autoque.domain.usecase

import android.net.Uri
import com.eakurnikov.autoque.data.model.Credentials
import com.eakurnikov.autoque.data.repository.CredentialsRepo
import com.eakurnikov.autoque.domain.csv.CsvParser
import io.reactivex.Maybe
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Named

class ImportPasswordsUseCase @Inject constructor(
    private val repo: CredentialsRepo,
    @Named("BufferedCredentialsCsvParser") private val csvParser: CsvParser<Credentials>
) {
    operator fun invoke(uri: Uri): Maybe<List<Credentials>> {
        return csvParser.parse(uri)?.let { credentialsList: List<Credentials> ->
            if (credentialsList.isNotEmpty()) {
//            repo.addCredentials(credentialsList)
                Maybe.just(credentialsList)
            } else {
                Maybe.empty()
            }
        } ?: Maybe.error(RuntimeException("Could not parse CSV-file"))
    }
}