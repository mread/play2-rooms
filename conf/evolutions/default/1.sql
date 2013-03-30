# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table configuration (
  identifier                varchar(255) not null,
  constraint pk_configuration primary key (identifier))
;

create table room (
  id                        bigint not null,
  configuration_identifier  varchar(255) not null,
  room_name                 varchar(255),
  constraint pk_room primary key (id))
;

create sequence configuration_seq;

create sequence room_seq;

alter table room add constraint fk_room_configuration_1 foreign key (configuration_identifier) references configuration (identifier) on delete restrict on update restrict;
create index ix_room_configuration_1 on room (configuration_identifier);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists configuration;

drop table if exists room;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists configuration_seq;

drop sequence if exists room_seq;

