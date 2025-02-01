package com.cd18.infra.persistence.model

import com.cd18.domain.metrics.enums.ActionType
import com.cd18.domain.metrics.enums.TargetType
import com.cd18.infra.persistence.config.model.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "user_action_log")
class UserActionLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "user_id")
    val userId: Long? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "action_type")
    val actionType: ActionType,
    @Column(name = "target_id")
    val targetId: Long? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "target_type")
    val targetType: TargetType,
    @Column(name = "action_detail")
    val actionDetail: String? = null,
) : BaseTimeEntity()
