package com.cd18.domain.port

import com.cd18.domain.port.enums.MessageTopic

interface MessagePublisher {
    fun send(
        topic: MessageTopic,
        key: String,
        message: Any,
    )
}
