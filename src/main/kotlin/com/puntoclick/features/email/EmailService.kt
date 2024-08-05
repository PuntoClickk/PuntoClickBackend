package com.puntoclick.features.email

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Properties
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class EmailService(private val host: String, private val port: String, private val username: String, private val password: String) {

    private val session: Session

    init {
        println(host)
        println(port)
        println(username)
        println(password)
        val props = Properties().apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.host", host)
            put("mail.smtp.port", port)
            put("mail.smtp.ssl.trust", host)
            put("mail.debug", "true")
        }

        session = Session.getInstance(props,
            object : javax.mail.Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    println(username)
                    println(password)
                    return PasswordAuthentication(username, password)
                }
            })
    }

    suspend fun sendEmail(to: String, subject: String, body: String) {

        try {

            withContext(Dispatchers.IO){
                val message = MimeMessage(session).apply {
                    setFrom(InternetAddress(username))
                    setRecipients(Message.RecipientType.TO, InternetAddress.parse(to))
                    this.subject = subject
                    setContent(buildHtmlContent(), "text/html")
                }
                println("Attempting to send email to $to")
                Transport.send(message)
                println("Email sent successfully to $to")
            }

        } catch (e: Exception) {
            println("Failed to send email: ${e.message}")
            e.printStackTrace()
        }
    }
}

fun buildHtmlContent(): String {
    return """
        <!DOCTYPE html>
        <html>
        <head>
            <style>
                body {
                    font-family: Arial, sans-serif;
                }
                .container {
                    padding: 20px;
                    background-color: #f9f9f9;
                    border: 1px solid #ddd;
                }
                .header {
                    background-color: #4CAF50;
                    color: white;
                    padding: 10px;
                    text-align: center;
                }
                .content {
                    margin: 20px;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <div class="header">
                    <h1>ID Innovations</h1>
                </div>
                <div class="content">
                    <p>Test, first message from ID Innovations</p>
                </div>
            </div>
        </body>
        </html>
    """
}