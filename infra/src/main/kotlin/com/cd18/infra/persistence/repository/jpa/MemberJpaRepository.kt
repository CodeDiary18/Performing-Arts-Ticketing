package com.cd18.infra.persistence.repository.jpa

import com.cd18.infra.persistence.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository : JpaRepository<Member, Long>
