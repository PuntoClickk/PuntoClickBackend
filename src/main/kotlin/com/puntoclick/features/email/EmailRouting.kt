package com.puntoclick.features.email

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Route.emailRouting(emailService: EmailService){

    post("email") {
        val contactForm = call.receive<ContactForm>()
        emailService.sendEmail(
            to = "email-example@gmail.com",
            subject = "Nuevo formulario de contacto de ${contactForm.name}",
            body = "Nombre: ${contactForm.name}\nEmail: ${contactForm.email}\nMensaje: ${contactForm.message}"
        )
        call.respond(mapOf("status" to "Email sent successfully"))

    }
}

@Serializable
data class ContactForm(val name: String, val email: String, val message: String)
