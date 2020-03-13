package com.eakurnikov.autoque

import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataDto

object FillDataExtTestHelper {

    /*
    facebook     com.facebook.katana              https://www.facebook.com/
    vk           com.vkontakte.android            https://vk.com/
    evernote     com.evernote                     https://www.evernote.com/
    avito        com.avito.android                https://www.avito.ru/
    ozon         ru.ozon.app.android              https://www.ozon.ru/
    instagram    com.instagram.android            https://www.instagram.com/
    yula         com.allgoritm.youla              https://youla.ru/
    deliveryclub com.deliveryclub                 https://www.delivery-club.ru/
    yandex music ru.yandex.music                  https://music.yandex.ru/
    yandex       ru.yandex.searchplugin           https://yandex.ru/
    skype        com.skype.raider                 https://www.skype.com/
    gmail        com.google.android.gm            https://mail.google.com/
    beru         ru.beru.android                  https://beru.ru/
    chopchop     ru.trinitydigital.chopchop       https://chopchop.me/
    airbnb       com.airbnb.android               https://www.airbnb.ru/
    goldapple    goldapple.ru.goldapple.customers https://goldapple.ru/
    joom         com.joom                         https://www.joom.com/
    lamoda       com.lamoda.lite                  https://www.lamoda.ru/
    medium       com.medium.reader                https://medium.com/
    sberfood     ru.platius.mobile                https://plazius.ru/
    hh           ru.hh.android                    https://hh.ru/
    citymobil    com.citymobil                    https://city-mobil.ru/
    ghostly      space.kgnk.ghostly               -
     */

    val fbAppAcc1 =
            createFillData(1, "My facebook", "com.facebook.katana")

    val fbAppAcc2 =
            FillDataDto(
                    AccountEntityImpl(1, "My facebook", "com.facebook.katana"),
                    LoginEntityImpl(24, "", "fbstub@gmail.com", "54321")
            )

//    val fbWebAcc1 =
//            createWebFillData(1, "My facebook", "https://www.facebook.com/")
//
//    val fbWebAcc2 =
//            FillDataEntity.Web(
//                    WebAccountEntityImpl(1, "My facebook", "https://www.facebook.com/"),
//                    LoginEntityImpl(24, "", "fbstub@gmail.com", "54321")
//            )

    val avitoAppAcc =
            createFillData(4, "Avito", "com.avito.android")

//    val avitoWebAccc =
//            createWebFillData(4, "Avito", "https://www.avito.ru/")

    val APP_ACCOUNTS_LIST: List<FillDataDto> = arrayListOf(
            fbAppAcc1,
            fbAppAcc2,
            createFillData(2, "VK", "com.vkontakte.android"),
            createFillData(3, "Evernote", "com.evernote"),
            avitoAppAcc,
            createFillData(5, "Ozon", "ru.ozon.app.android"),
            createFillData(6, "Instagram", "com.instagram.android"),
            createFillData(7, "Yula", "com.allgoritm.youla"),
            createFillData(8, "Delivery club", "com.deliveryclub"),
            createFillData(9, "Yandex music", "ru.yandex.music"),
            createFillData(10, "Yandex", "ru.yandex.searchplugin"),
            createFillData(11, "Skype", "com.skype.raider"),
            createFillData(12, "Gmail", "com.google.android.gm"),
            createFillData(13, "Beru", "ru.beru.android"),
            createFillData(14, "Chop-chop", "ru.trinitydigital.chopchop"),
            createFillData(15, "Airbnb", "com.airbnb.android"),
            createFillData(16, "Gold apple", "goldapple.ru.goldapple.customers"),
            createFillData(17, "Joom", "com.joom"),
            createFillData(18, "Lamoda", "com.lamoda.lite"),
            createFillData(19, "Medium", "com.medium.reader"),
            createFillData(20, "Sber food", "ru.platius.mobile"),
            createFillData(21, "Head hunter", "ru.hh.android"),
            createFillData(22, "Citymobil", "com.citymobil"),
            createFillData(23, "Ghostly", "space.kgnk.ghostly"),
            FillDataDto(
                    AccountEntityImpl(1, "Vkontakte", "com.vkontakte.android"),
                    LoginEntityImpl(25, "", "vkstub@gmail.com", "54321")
            )
    )

//    val WEB_ACCOUNTS_LIST: List<FillDataEntity.Web> = arrayListOf(
//            fbWebAcc1,
//            fbWebAcc2,
//            createWebFillData(2, "VK", "https://vk.com/"),
//            createWebFillData(3, "Evernote", "https://www.evernote.com/"),
//            avitoWebAcc,
//            createWebFillData(5, "Ozon", "https://www.ozon.ru/"),
//            createWebFillData(6, "Instagram", "https://www.instagram.com/"),
//            createWebFillData(7, "Yula", "https://youla.ru/"),
//            createWebFillData(8, "Delivery club", "https://www.delivery-club.ru/"),
//            createWebFillData(9, "Yandex music", "https://music.yandex.ru/"),
//            createWebFillData(10, "Yandex", "https://yandex.ru/"),
//            createWebFillData(11, "Skype", "https://www.skype.com/"),
//            createWebFillData(12, "Gmail", "https://mail.google.com/"),
//            createWebFillData(13, "Beru", "https://beru.ru/"),
//            createWebFillData(14, "Chop-chop", "https://chopchop.me/"),
//            createWebFillData(15, "Airbnb", "https://www.airbnb.ru/"),
//            createWebFillData(16, "Gold apple", "https://goldapple.ru/"),
//            createWebFillData(17, "Joom", "https://www.joom.com/"),
//            createWebFillData(18, "Lamoda", "https://www.lamoda.ru/"),
//            createWebFillData(19, "Medium", "https://medium.com/"),
//            createWebFillData(20, "Sber food", "https://plazius.ru/"),
//            createWebFillData(21, "Head hunter", "https://hh.ru/"),
//            createWebFillData(22, "Citymobil", "https://city-mobil.ru/"),
//            createWebFillData(23, "Ghostly", ""),
//            FillDataEntity.Web(
//                    WebAccountEntityImpl(1, "Vkontakte", "https://vk.com/"),
//                    LoginEntityImpl(25, "", "vkstub@gmail.com", "54321")
//            )
//    )

//    val ALL_ACCOUNTS_LIST: List<FillDataEntity> =
//            APP_ACCOUNTS_LIST.union(WEB_ACCOUNTS_LIST).toList()
}