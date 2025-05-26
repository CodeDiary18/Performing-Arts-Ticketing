package com.cd18.infra.mail.template

interface MailTemplate {
    fun getSubject(): String

    fun getBody(): String
}
