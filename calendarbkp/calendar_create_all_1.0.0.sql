SET SCHEMA 'schema';

create table access_group (
  id                            uuid not null,
  version                       bigint,
  group_name                    varchar(255),
  user_id                       uuid,
  all_records                   boolean,
  user_and_superiors            uuid[],
  group_type                    varchar(255),
  entity_ids                    uuid[],
  created_at                    timestamptz not null,
  updated_at                    timestamptz not null,
  constraint pk_access_group primary key (id)
);

create table access_rule (
  id                            uuid not null,
  version                       bigint,
  rule_name                     varchar(255),
  active                        boolean,
  default_rule                  boolean,
  all_users                     boolean,
  entity_name                   varchar(255),
  reference_property            varchar(255),
  access_group_name             varchar(255),
  created_at                    timestamptz not null,
  updated_at                    timestamptz not null,
  constraint pk_access_rule primary key (id)
);

create table permission_changes (
  id                            uuid not null,
  version                       bigint,
  group_name                    varchar(255),
  user_id                       uuid,
  user_and_superiors            uuid[],
  granted_entity_ids            uuid[],
  revoked_entity_ids            uuid[],
  created_at                    timestamptz not null,
  updated_at                    timestamptz not null,
  constraint pk_permission_changes primary key (id)
);

create index ix_access_group_group_name on access_group (group_name);
create index ix_permission_changes_group_name on permission_changes (group_name);


--INDEX--

        -- Postgres support for GIN indexes for UUID type
        DO $$
        BEGIN

        CREATE OPERATOR CLASS _uuid_ops DEFAULT FOR TYPE _uuid USING gin AS
        OPERATOR 1 &&(anyarray, anyarray),
        OPERATOR 2 @>(anyarray, anyarray),
        OPERATOR 3 <@(anyarray, anyarray),
        OPERATOR 4 =(anyarray, anyarray),
        FUNCTION 1 uuid_cmp(uuid, uuid),
        FUNCTION 2 ginarrayextract(anyarray, internal, internal),
        FUNCTION 3 ginqueryarrayextract(anyarray, internal, smallint, internal, internal, internal, internal),
        FUNCTION 4 ginarrayconsistent(internal, smallint, anyarray, integer, internal, internal, internal, internal),
        STORAGE uuid;

        EXCEPTION
        WHEN duplicate_object THEN
        RAISE NOTICE 'warning: %', SQLERRM;
        END;
        $$;

        -- postgres ARRAY specific indexes
        CREATE INDEX idx_access_group_user_and_superiors ON access_group USING GIN(user_and_superiors);
        CREATE INDEX idx_access_group_entity_ids ON access_group USING GIN(entity_ids);


create table calendar_type (
    id                      uuid not null,
    version                 bigint,
    external_id             varchar(255),
    description             varchar(80),
    color                   varchar(30),
    active                  boolean not null,
    standard                boolean not null default false,
    created_at              timestamptz not null,
    updated_at              timestamptz not null,
    deleted                 boolean not null default false,
    constraint pk_calendar_type primary key(id)
);