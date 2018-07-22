# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table data_values (
  data_value_id                 bigint auto_increment not null,
  data_value                    TEXT,
  metadata_id                   bigint,
  platform_id                   bigint,
  constraint pk_data_values primary key (data_value_id)
);

create table metadata (
  metadata_id                   bigint auto_increment not null,
  metadata_name                 varchar(255),
  metadata_category             varchar(255),
  metadata_subcategory          varchar(255),
  metadata_description          TEXT,
  constraint pk_metadata primary key (metadata_id)
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


# --- !Downs

drop table if exists data_values;

drop table if exists metadata;

drop table if exists platform;

