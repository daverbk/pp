create table if not exists sleep_log (
    id BIGSERIAL PRIMARY KEY,
    date TIMESTAMPTZ NOT NULL,
    sleep_start TIMESTAMPTZ NOT NULL,
    sleep_end TIMESTAMPTZ NOT NULL,
    total_sleep DOUBLE PRECISION NOT NULL
);

alter table sleep_log
add constraint unique_date_total unique (date, total_sleep);

create table if not exists workout_log (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    start_time TIMESTAMPTZ NOT NULL,
    end_time TIMESTAMPTZ NOT NULL,
    duration DOUBLE PRECISION NOT NULL
);
