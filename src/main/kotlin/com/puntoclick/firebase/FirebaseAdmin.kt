package com.puntoclick.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import java.io.InputStream
import com.google.firebase.FirebaseOptions
import io.ktor.server.application.*

object FirebaseAdmin {
    private val serviceAccount: InputStream? =
        this::class.java.classLoader.getResourceAsStream("ktor-firebase-auth-adminsdk.json")

    private val options: FirebaseOptions = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build()

    fun Application.configureFirebase(): FirebaseApp = FirebaseApp.initializeApp(options)
}