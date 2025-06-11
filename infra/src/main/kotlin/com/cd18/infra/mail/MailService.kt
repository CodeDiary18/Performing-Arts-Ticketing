package com.cd18.infra.mail

import com.cd18.infra.mail.template.MailTemplate
import java.io.File

interface MailService {
    fun send(
        to: String,
        subject: String,
        text: String,
        attachments: List<File>?,
    )

    fun sendByTemplate(
        to: String,
        template: MailTemplate,
        attachments: List<File>? = null,
    )
}
