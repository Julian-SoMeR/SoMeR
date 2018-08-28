# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table aggregator (
  aggregator_id                 bigint auto_increment not null,
  delete_status                 tinyint(1) default 0 not null,
  aggregator_name               varchar(255) not null,
  aggregator_category           TEXT default '' not null,
  aggregator_description        TEXT default '',
  creation_date                 datetime(6) not null,
  modification_date             datetime(6) not null,
  constraint pk_aggregator primary key (aggregator_id)
);

create table aggregator_content (
  aggregator_content_id         bigint auto_increment not null,
  delete_status                 tinyint(1) default 0 not null,
  aggregator_content            TEXT default '' not null,
  aggregator_aggregator_id      bigint,
  platform_platform_id          bigint,
  creation_date                 datetime(6) not null,
  modification_date             datetime(6) not null,
  constraint pk_aggregator_content primary key (aggregator_content_id)
);

create table function (
  function_id                   bigint auto_increment not null,
  delete_status                 tinyint(1) default 0 not null,
  function_name                 varchar(255) not null,
  function_category             TEXT default '' not null,
  function_description          TEXT default '',
  creation_date                 datetime(6) not null,
  modification_date             datetime(6) not null,
  constraint pk_function primary key (function_id)
);

create table function_content (
  function_content_id           bigint auto_increment not null,
  delete_status                 tinyint(1) default 0 not null,
  function_content              TEXT default '' not null,
  function_function_id          bigint,
  platform_platform_id          bigint,
  creation_date                 datetime(6) not null,
  modification_date             datetime(6) not null,
  constraint pk_function_content primary key (function_content_id)
);

create table function_content_history (
  function_content_history_id   bigint auto_increment not null,
  function_content              TEXT default '' not null,
  function_content_function_content_id bigint,
  history_creation_date         datetime(6),
  delete_status                 TINYINT default '0' not null,
  constraint pk_function_content_history primary key (function_content_history_id)
);

create table function_history (
  function_history_id           bigint auto_increment not null,
  function_function_id          bigint,
  function_name                 varchar(255) not null,
  function_category             TEXT default '' not null,
  history_creation_date         datetime(6),
  delete_status                 TINYINT default '0' not null,
  constraint pk_function_history primary key (function_history_id)
);

create table impact (
  impact_id                     bigint auto_increment not null,
  delete_status                 tinyint(1) default 0 not null,
  impact_name                   varchar(255) not null,
  impact_category               TEXT default '' not null,
  impact_description            TEXT default '',
  creation_date                 datetime(6) not null,
  modification_date             datetime(6) not null,
  constraint pk_impact primary key (impact_id)
);

create table impact_content (
  impact_content_id             bigint auto_increment not null,
  delete_status                 tinyint(1) default 0 not null,
  impact_content                TEXT default '' not null,
  impact_impact_id              bigint,
  platform_platform_id          bigint,
  creation_date                 datetime(6) not null,
  modification_date             datetime(6) not null,
  constraint pk_impact_content primary key (impact_content_id)
);

create table impact_content_history (
  impact_content_history_id     bigint auto_increment not null,
  impact_content                TEXT default '' not null,
  impact_content_impact_content_id bigint,
  history_creation_date         datetime(6),
  delete_status                 TINYINT default '0' not null,
  constraint pk_impact_content_history primary key (impact_content_history_id)
);

create table impact_history (
  impact_history_id             bigint auto_increment not null,
  impact_impact_id              bigint,
  impact_name                   varchar(255) not null,
  impact_category               TEXT default '' not null,
  history_creation_date         datetime(6),
  delete_status                 TINYINT default '0' not null,
  constraint pk_impact_history primary key (impact_history_id)
);

create table information (
  information_id                bigint auto_increment not null,
  delete_status                 tinyint(1) default 0 not null,
  information_name              varchar(255) not null,
  creation_date                 datetime(6) not null,
  modification_date             datetime(6) not null,
  constraint pk_information primary key (information_id)
);

create table information_content (
  information_content_id        bigint auto_increment not null,
  delete_status                 tinyint(1) default 0 not null,
  information_content           TEXT default '' not null,
  information_information_id    bigint,
  platform_platform_id          bigint,
  creation_date                 datetime(6) not null,
  modification_date             datetime(6) not null,
  constraint pk_information_content primary key (information_content_id)
);

create table information_content_history (
  information_content_history_id bigint auto_increment not null,
  information_content           TEXT default '' not null,
  information_content_information_content_id bigint,
  history_creation_date         datetime(6),
  delete_status                 TINYINT default '0' not null,
  constraint pk_information_content_history primary key (information_content_history_id)
);

create table information_history (
  information_history_id        bigint auto_increment not null,
  information_information_id    bigint,
  information_name              varchar(255) not null,
  history_creation_date         datetime(6),
  delete_status                 TINYINT default '0' not null,
  constraint pk_information_history primary key (information_history_id)
);

create table platform (
  platform_id                   bigint auto_increment not null,
  delete_status                 tinyint(1) default 0 not null,
  platform_name                 varchar(255) not null,
  creation_date                 datetime(6) not null,
  modification_date             datetime(6) not null,
  constraint pk_platform primary key (platform_id)
);

create table platform_history (
  platform_history_id           bigint auto_increment not null,
  platform_platform_id          bigint,
  platform_name                 varchar(255) not null,
  history_creation_date         datetime(6),
  delete_status                 TINYINT default '0' not null,
  constraint pk_platform_history primary key (platform_history_id)
);

create index ix_aggregator_content_aggregator_aggregator_id on aggregator_content (aggregator_aggregator_id);
alter table aggregator_content add constraint fk_aggregator_content_aggregator_aggregator_id foreign key (aggregator_aggregator_id) references aggregator (aggregator_id) on delete restrict on update restrict;

create index ix_aggregator_content_platform_platform_id on aggregator_content (platform_platform_id);
alter table aggregator_content add constraint fk_aggregator_content_platform_platform_id foreign key (platform_platform_id) references platform (platform_id) on delete restrict on update restrict;

create index ix_function_content_function_function_id on function_content (function_function_id);
alter table function_content add constraint fk_function_content_function_function_id foreign key (function_function_id) references function (function_id) on delete restrict on update restrict;

create index ix_function_content_platform_platform_id on function_content (platform_platform_id);
alter table function_content add constraint fk_function_content_platform_platform_id foreign key (platform_platform_id) references platform (platform_id) on delete restrict on update restrict;

create index ix_function_content_history_function_content_function_con_1 on function_content_history (function_content_function_content_id);
alter table function_content_history add constraint fk_function_content_history_function_content_function_con_1 foreign key (function_content_function_content_id) references function_content (function_content_id) on delete restrict on update restrict;

create index ix_function_history_function_function_id on function_history (function_function_id);
alter table function_history add constraint fk_function_history_function_function_id foreign key (function_function_id) references function (function_id) on delete restrict on update restrict;

create index ix_impact_content_impact_impact_id on impact_content (impact_impact_id);
alter table impact_content add constraint fk_impact_content_impact_impact_id foreign key (impact_impact_id) references impact (impact_id) on delete restrict on update restrict;

create index ix_impact_content_platform_platform_id on impact_content (platform_platform_id);
alter table impact_content add constraint fk_impact_content_platform_platform_id foreign key (platform_platform_id) references platform (platform_id) on delete restrict on update restrict;

create index ix_impact_content_history_impact_content_impact_content_id on impact_content_history (impact_content_impact_content_id);
alter table impact_content_history add constraint fk_impact_content_history_impact_content_impact_content_id foreign key (impact_content_impact_content_id) references impact_content (impact_content_id) on delete restrict on update restrict;

create index ix_impact_history_impact_impact_id on impact_history (impact_impact_id);
alter table impact_history add constraint fk_impact_history_impact_impact_id foreign key (impact_impact_id) references impact (impact_id) on delete restrict on update restrict;

create index ix_information_content_information_information_id on information_content (information_information_id);
alter table information_content add constraint fk_information_content_information_information_id foreign key (information_information_id) references information (information_id) on delete restrict on update restrict;

create index ix_information_content_platform_platform_id on information_content (platform_platform_id);
alter table information_content add constraint fk_information_content_platform_platform_id foreign key (platform_platform_id) references platform (platform_id) on delete restrict on update restrict;

create index ix_information_content_history_information_content_inform_1 on information_content_history (information_content_information_content_id);
alter table information_content_history add constraint fk_information_content_history_information_content_inform_1 foreign key (information_content_information_content_id) references information_content (information_content_id) on delete restrict on update restrict;

create index ix_information_history_information_information_id on information_history (information_information_id);
alter table information_history add constraint fk_information_history_information_information_id foreign key (information_information_id) references information (information_id) on delete restrict on update restrict;

create index ix_platform_history_platform_platform_id on platform_history (platform_platform_id);
alter table platform_history add constraint fk_platform_history_platform_platform_id foreign key (platform_platform_id) references platform (platform_id) on delete restrict on update restrict;


# --- !Downs

alter table aggregator_content drop foreign key fk_aggregator_content_aggregator_aggregator_id;
drop index ix_aggregator_content_aggregator_aggregator_id on aggregator_content;

alter table aggregator_content drop foreign key fk_aggregator_content_platform_platform_id;
drop index ix_aggregator_content_platform_platform_id on aggregator_content;

alter table function_content drop foreign key fk_function_content_function_function_id;
drop index ix_function_content_function_function_id on function_content;

alter table function_content drop foreign key fk_function_content_platform_platform_id;
drop index ix_function_content_platform_platform_id on function_content;

alter table function_content_history drop foreign key fk_function_content_history_function_content_function_con_1;
drop index ix_function_content_history_function_content_function_con_1 on function_content_history;

alter table function_history drop foreign key fk_function_history_function_function_id;
drop index ix_function_history_function_function_id on function_history;

alter table impact_content drop foreign key fk_impact_content_impact_impact_id;
drop index ix_impact_content_impact_impact_id on impact_content;

alter table impact_content drop foreign key fk_impact_content_platform_platform_id;
drop index ix_impact_content_platform_platform_id on impact_content;

alter table impact_content_history drop foreign key fk_impact_content_history_impact_content_impact_content_id;
drop index ix_impact_content_history_impact_content_impact_content_id on impact_content_history;

alter table impact_history drop foreign key fk_impact_history_impact_impact_id;
drop index ix_impact_history_impact_impact_id on impact_history;

alter table information_content drop foreign key fk_information_content_information_information_id;
drop index ix_information_content_information_information_id on information_content;

alter table information_content drop foreign key fk_information_content_platform_platform_id;
drop index ix_information_content_platform_platform_id on information_content;

alter table information_content_history drop foreign key fk_information_content_history_information_content_inform_1;
drop index ix_information_content_history_information_content_inform_1 on information_content_history;

alter table information_history drop foreign key fk_information_history_information_information_id;
drop index ix_information_history_information_information_id on information_history;

alter table platform_history drop foreign key fk_platform_history_platform_platform_id;
drop index ix_platform_history_platform_platform_id on platform_history;

drop table if exists aggregator;

drop table if exists aggregator_content;

drop table if exists function;

drop table if exists function_content;

drop table if exists function_content_history;

drop table if exists function_history;

drop table if exists impact;

drop table if exists impact_content;

drop table if exists impact_content_history;

drop table if exists impact_history;

drop table if exists information;

drop table if exists information_content;

drop table if exists information_content_history;

drop table if exists information_history;

drop table if exists platform;

drop table if exists platform_history;

