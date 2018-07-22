# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table data_values (
  data_value_id                 bigint auto_increment not null,
  data_value                    varchar(255),
  metadata_id                   bigint,
  platform_id                   bigint,
  constraint pk_data_values primary key (data_value_id)
);

create table metadata (
  metadata_id                   bigint auto_increment not null,
  metadata_name                 varchar(255),
  metadata_category             varchar(255),
  metadata_subcategory          varchar(255),
  metadata_description          varchar(255),
  constraint pk_metadata primary key (metadata_id)
);


# --- !Downs

drop table if exists data_values;

drop table if exists metadata;

