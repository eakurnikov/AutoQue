package com.eakurnikov.autoque.di.modules.common

import com.eakurnikov.autoque.data.model.Credentials
import com.eakurnikov.autoque.domain.csv.BufferedCsvCredentialsParserImpl
import com.eakurnikov.autoque.domain.csv.CsvCredentialsParserImpl
import com.eakurnikov.autoque.domain.csv.CsvParser
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module(includes = [CsvParserModule.Declarations::class])
class CsvParserModule {

    @Module
    interface Declarations {

        @Binds
        @Named("CredentialsCsvParser")
        fun bindCredentialsCsvParser(
            impl: CsvCredentialsParserImpl
        ): CsvParser<Credentials>

        @Binds
        @Named("BufferedCredentialsCsvParser")
        fun bindBufferedCredentialsCsvParser(
            impl: BufferedCsvCredentialsParserImpl
        ): CsvParser<Credentials>
    }
}