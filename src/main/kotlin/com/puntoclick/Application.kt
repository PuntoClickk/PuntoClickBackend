package com.puntoclick


import com.puntoclick.data.database.configureDatabase
import com.puntoclick.plugins.*
import com.puntoclick.security.AppEncryption
import io.ktor.server.application.*
import io.ktor.server.netty.*
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureKoin()
    configureDatabase()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureSecurity()
    configureRouting()

    val plainText = "Hello world"
    val appEncryption by inject<AppEncryption>(named(name = "AES"))
    val encrypted = appEncryption.encrypt(plainText)
    println(encrypted)
    println(appEncryption.decrypt(encrypted))
    println(appEncryption.decrypt("E0F5rdzdYhlRUqnh1hgRgiVNhpWOHTW7NVoq:kLQqTLzP5OtqfctM"))

}



