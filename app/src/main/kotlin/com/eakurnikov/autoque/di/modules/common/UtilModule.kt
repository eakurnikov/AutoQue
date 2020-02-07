package com.eakurnikov.autoque.di.modules.common

import dagger.Module

@Module(
    includes = [
        PopupModule::class,
        CsvParserModule::class
    ]
)
class UtilModule