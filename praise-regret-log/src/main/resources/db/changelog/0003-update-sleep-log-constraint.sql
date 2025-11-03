alter table sleep_log
drop constraint unique_date_total;

alter table sleep_log
add constraint unique_date unique (date);