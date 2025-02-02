CREATE TABLE performance_info
(
    id         bigint auto_increment primary key,
    perf_name  varchar(255) not null,
    perf_desc  text         not null,
    perf_venue varchar(30),
    start_date timestamp,
    end_date   timestamp,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE performance_schedule
(
    id         bigint auto_increment primary key,
    perf_id    bigint    not null,
    perf_time  timestamp not null,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE seat
(
    id           bigint auto_increment primary key,
    perf_id      bigint     not null,
    perf_schedule_id bigint     not null,
    seat_name    varchar(4) not null,
    pos_x        int unsigned not null,
    pos_y        int unsigned not null,
    status       varchar(10) not null,
    created_at   timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_at   timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE performance_price
(
    id         bigint auto_increment primary key,
    perf_id    bigint not null,
    perf_price int unsigned not null,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE performance_discount
(
    id            bigint auto_increment primary key,
    perf_id       bigint not null,
    perf_dc_price int unsigned not null,
    created_at    timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_at    timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE history_performance_discount
(
    id            bigint auto_increment primary key,
    perf_id       bigint not null,
    perf_dc_id    bigint not null,
    perf_dc_price int unsigned not null,
    created_at    timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_at    timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_action_log
(
    id            bigint auto_increment primary key,   -- 로그의 고유 id (자동 증가)
    user_id       bigint null,                         -- 사용자 id (익명 사용자의 경우 null 허용)
    action_type   varchar(50) not null,                -- 행동 유형 (예: 'VIEW_PERF', 'CREATE_COMMENT')
    target_id     bigint null,                         -- 대상 id (예: performance_id, comment_id 등)
    target_type   varchar(50) not null,                -- 대상 유형 (예: 'PERFORMANCE', 'COMMENT')
    action_detail text,                                -- 행동 상세 정보 (json 형식 등)
    created_at    datetime  DEFAULT current_timestamp, -- 로그 생성 시간 (기본값: 현재 시간)
    updated_at    timestamp DEFAULT current_timestamp  -- 로그 수정 시간 (기본값: 현재 시간)
);

CREATE TABLE performance_hourly_view_stat
(
    id         bigint auto_increment primary key,
    statistic_date  date not null,
    statistic_hour  tinyint not null check (statistic_hour BETWEEN 0 AND 23),
    perf_id    bigint not null,
    view_count int unsigned not null,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE member
(
    id         bigint auto_increment primary key,
    name       varchar(255) not null,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP
);
