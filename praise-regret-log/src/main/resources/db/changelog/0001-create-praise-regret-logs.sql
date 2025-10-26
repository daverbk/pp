create table if not exists praise_regret_logs (
    id bigserial primary key,
    type varchar(16) not null check (type in ('PRAISE', 'REGRET')),
    created_at timestamptz not null default now()
);
