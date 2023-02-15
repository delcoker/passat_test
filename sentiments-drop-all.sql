-- drop all foreign keys
alter table sentiments drop foreign key fk_sentiments_reportId;
drop index ix_sentiments_reportId on sentiments;

-- drop all
drop table if exists reports;

drop table if exists sentiments;

