# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table designationdata (
  designationdata_id            bigint auto_increment not null,
  designationdata_name          varchar(255),
  designationdata_category      varchar(255),
  designationdata_subcategory   varchar(255),
  designationdata_description   TEXT,
  constraint pk_designationdata primary key (designationdata_id)
);

create table platform (
  platform_id                   integer auto_increment not null,
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
  constraint pk_platform primary key (platform_id)
);

create table valuedata (
  valuedata_id                  bigint auto_increment not null,
  valuedata_content             TEXT,
  designationdata_designationdata_id bigint,
  platform_platform_id          integer,
  constraint pk_valuedata primary key (valuedata_id)
);

alter table valuedata add constraint fk_valuedata_designationdata_designationdata_id foreign key (designationdata_designationdata_id) references designationdata (designationdata_id) on delete restrict on update restrict;
create index ix_valuedata_designationdata_designationdata_id on valuedata (designationdata_designationdata_id);

alter table valuedata add constraint fk_valuedata_platform_platform_id foreign key (platform_platform_id) references platform (platform_id) on delete restrict on update restrict;
create index ix_valuedata_platform_platform_id on valuedata (platform_platform_id);


# --- !Downs

alter table valuedata drop foreign key fk_valuedata_designationdata_designationdata_id;
drop index ix_valuedata_designationdata_designationdata_id on valuedata;

alter table valuedata drop foreign key fk_valuedata_platform_platform_id;
drop index ix_valuedata_platform_platform_id on valuedata;

drop table if exists designationdata;

drop table if exists platform;

drop table if exists valuedata;

