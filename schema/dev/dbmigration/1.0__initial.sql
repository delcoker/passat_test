-- apply changes
create table reports (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  details                       varchar(255),
  createdAt                     datetime(6) default '2020-04-26 00:00' not null,
  updatedAt                     datetime(6) default '2020-04-26 00:00' not null,
  constraint pk_reports primary key (id)
);

create table sentiments (
  id                            bigint auto_increment not null,
  sentence                      varchar(255),
  sentiment                     varchar(10),
  score                         double not null,
  reportId                      bigint,
  createdAt                     datetime(6) default '2020-04-26 00:00' not null,
  updatedAt                     datetime(6) default '2020-04-26 00:00' not null,
  constraint pk_sentiments primary key (id)
);

-- foreign keys and indices
create index ix_sentiments_reportId on sentiments (reportId);
alter table sentiments add constraint fk_sentiments_reportId foreign key (reportId) references reports (id) on delete restrict on update restrict;

