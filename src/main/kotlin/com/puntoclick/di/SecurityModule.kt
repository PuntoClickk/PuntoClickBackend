package com.puntoclick.di

import com.puntoclick.security.AppEncryption
import com.puntoclick.security.AppEncryptionAES
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.named
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val securityModule = module {
    singleOf(::AppEncryptionAES){
        named(name = "AES")
        bind<AppEncryption>()
    }
}