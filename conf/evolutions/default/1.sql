# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table aggregator (
  aggregator_id                 bigint auto_increment not null,
  aggregator_name               varchar(255),
  aggregator_category           varchar(255),
  aggregator_description        TEXT,
  creation_date                 datetime(6) not null,
  modification_date             datetime(6) not null,
  version_number                bigint not null,
  constraint pk_aggregator primary key (aggregator_id)
);

create table aggregator_content (
  aggregator_content_id         bigint auto_increment not null,
  aggregator_content            TEXT,
  aggregator_aggregator_id      bigint,
  platformdata_platformdata_id  bigint,
  creation_date                 datetime(6) not null,
  modification_date             datetime(6) not null,
  version_number                bigint not null,
  constraint pk_aggregator_content primary key (aggregator_content_id)
);

create table designationdata (
  designationdata_id            bigint auto_increment not null,
  designation_name              varchar(255),
  designation_category          varchar(255),
  designation_subcategory       varchar(255),
  designation_description       TEXT,
  creation_date                 datetime(6) not null,
  modification_date             datetime(6) not null,
  version_number                bigint not null,
  constraint pk_designationdata primary key (designationdata_id)
);

create table function (
  function_id                   bigint auto_increment not null,
  function_name                 varchar(255),
  function_category             varchar(255),
  function_description          TEXT,
  creation_date                 datetime(6) not null,
  modification_date             datetime(6) not null,
  version_number                bigint not null,
  constraint pk_function primary key (function_id)
);

create table function_content (
  function_content_id           bigint auto_increment not null,
  function_content              TEXT,
  function_function_id          bigint,
  platformdata_platformdata_id  bigint,
  creation_date                 datetime(6) not null,
  modification_date             datetime(6) not null,
  version_number                bigint not null,
  constraint pk_function_content primary key (function_content_id)
);

create table platformdata (
  platformdata_id               bigint auto_increment not null,
  platform_name                 varchar(255),
  platform_or_service_type      varchar(255),
  target_group                  varchar(255),
  commercial_or_noncommercial_service varchar(255),
  free_or_subscription_source   TEXT,
  primary_purpose               TEXT,
  geographical_reach            TEXT,
  number_active_or_registered_users TEXT,
  content_coverage              TEXT,
  website_url                   varchar(255),
  usage_ranking                 TEXT,
  description                   TEXT,
  creation_date                 datetime(6) not null,
  modification_date             datetime(6) not null,
  version_number                bigint not null,
  constraint pk_platformdata primary key (platformdata_id)
);

create table valuedata (
  valuedata_id                  bigint auto_increment not null,
  valuedata_content             TEXT,
  designationdata_designationdata_id bigint,
  platformdata_platformdata_id  bigint,
  creation_date                 datetime(6) not null,
  modification_date             datetime(6) not null,
  version_number                bigint not null,
  constraint pk_valuedata primary key (valuedata_id)
);

create index ix_aggregator_content_aggregator_aggregator_id on aggregator_content (aggregator_aggregator_id);
alter table aggregator_content add constraint fk_aggregator_content_aggregator_aggregator_id foreign key (aggregator_aggregator_id) references aggregator (aggregator_id) on delete restrict on update restrict;

create index ix_aggregator_content_platformdata_platformdata_id on aggregator_content (platformdata_platformdata_id);
alter table aggregator_content add constraint fk_aggregator_content_platformdata_platformdata_id foreign key (platformdata_platformdata_id) references platformdata (platformdata_id) on delete restrict on update restrict;

create index ix_function_content_function_function_id on function_content (function_function_id);
alter table function_content add constraint fk_function_content_function_function_id foreign key (function_function_id) references function (function_id) on delete restrict on update restrict;

create index ix_function_content_platformdata_platformdata_id on function_content (platformdata_platformdata_id);
alter table function_content add constraint fk_function_content_platformdata_platformdata_id foreign key (platformdata_platformdata_id) references platformdata (platformdata_id) on delete restrict on update restrict;

create index ix_valuedata_designationdata_designationdata_id on valuedata (designationdata_designationdata_id);
alter table valuedata add constraint fk_valuedata_designationdata_designationdata_id foreign key (designationdata_designationdata_id) references designationdata (designationdata_id) on delete restrict on update restrict;

create index ix_valuedata_platformdata_platformdata_id on valuedata (platformdata_platformdata_id);
alter table valuedata add constraint fk_valuedata_platformdata_platformdata_id foreign key (platformdata_platformdata_id) references platformdata (platformdata_id) on delete restrict on update restrict;


# --- !Downs

alter table aggregator_content drop foreign key fk_aggregator_content_aggregator_aggregator_id;
drop index ix_aggregator_content_aggregator_aggregator_id on aggregator_content;

alter table aggregator_content drop foreign key fk_aggregator_content_platformdata_platformdata_id;
drop index ix_aggregator_content_platformdata_platformdata_id on aggregator_content;

alter table function_content drop foreign key fk_function_content_function_function_id;
drop index ix_function_content_function_function_id on function_content;

alter table function_content drop foreign key fk_function_content_platformdata_platformdata_id;
drop index ix_function_content_platformdata_platformdata_id on function_content;

alter table valuedata drop foreign key fk_valuedata_designationdata_designationdata_id;
drop index ix_valuedata_designationdata_designationdata_id on valuedata;

alter table valuedata drop foreign key fk_valuedata_platformdata_platformdata_id;
drop index ix_valuedata_platformdata_platformdata_id on valuedata;

drop table if exists aggregator;

drop table if exists aggregator_content;

drop table if exists designationdata;

drop table if exists function;

drop table if exists function_content;

drop table if exists platformdata;

drop table if exists valuedata;

