package com.cd18.infra.mail

import com.cd18.infra.mail.template.MailTemplate
import jakarta.mail.internet.MimeMessage
import org.springframework.mail.MailAuthenticationException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.io.File

@Service
class MailServiceImpl(
    private val mailSender: JavaMailSender,
) : MailService {
    companion object {
        const val FROM = "noreply@cd18.com"
    }

    override fun send(
        to: String,
        subject: String,
        text: String,
        attachments: List<File>?,
    ) {
        val mimeMessage: MimeMessage = mailSender.createMimeMessage()
        try {
            val helper = MimeMessageHelper(mimeMessage, true, "UTF-8")

            helper.setFrom(FROM)
            helper.setTo(to)
            helper.setSubject(subject)
            helper.setText(text, true)

            if (!attachments.isNullOrEmpty()) {
                attachments.forEach { file ->
                    helper.addAttachment(file.name, file)
                }
            }
        } catch (e: MailAuthenticationException) {
            throw IllegalArgumentException("Mail Authentication Error: ${e.message}", e)
        } catch (e: Exception) {
            throw RuntimeException("Failed to send email: ${e.message}", e)
        }
        mailSender.send(mimeMessage)
    }

    override fun sendByTemplate(
        to: String,
        template: MailTemplate,
        attachments: List<File>?,
    ) {
        if (attachments != null) {
            this.send(
                to = to,
                subject = template.getSubject(),
                text = template.getBody(),
                attachments = attachments,
            )
        }
    }
}
