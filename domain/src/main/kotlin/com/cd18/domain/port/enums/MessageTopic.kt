package com.cd18.domain.port.enums

enum class MessageTopic(
    private val topicName: String,
) {
    TICKET_CREATE("ticket-create"),
    TICKET_CANCEL("ticket-cancel"),
    ;

    override fun toString(): String = topicName
}
