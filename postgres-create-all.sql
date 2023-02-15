-- apply changes
create table employees (
  id                            bigint generated by default as identity not null,
  userId                        bigint,
  deloop_user_id                bigint not null,
  createdAt                     timestamp default '2020-04-26 00:00' not null,
  updatedAt                     timestamp default '2020-04-26 00:00' not null,
  employeeId                    varchar(255),
  constraint uq_employees_userId unique (userId),
  constraint pk_employees primary key (id)
);

create table items (
  id                            bigint generated by default as identity not null,
  price                         float default 0 not null,
  minStockLevel                 integer default 0 not null,
  itemCategoryId                bigint,
  createdAt                     timestamp default '2020-04-26 00:00' not null,
  updatedAt                     timestamp default '2020-04-26 00:00' not null,
  name                          varchar(255),
  imagePath                     varchar(255) default '',
  status                        varchar(8) not null,
  constraint ck_items_status check ( status in ('enabled','disabled','deleted')),
  constraint uq_items_itemcategoryid_name unique (itemcategoryid,name),
  constraint pk_items primary key (id)
);

create table item_categories (
  id                            bigint generated by default as identity not null,
  createdAt                     timestamp not null,
  updatedAt                     timestamp default '2020-04-26 00:00' not null,
  name                          varchar(255),
  status                        varchar(8) not null,
  constraint ck_item_categories_status check ( status in ('enabled','disabled','deleted')),
  constraint pk_item_categories primary key (id)
);

create table momo_users (
  id                            bigint generated by default as identity not null,
  userId                        bigint,
  deloop_user_id                bigint not null,
  createdAt                     timestamp default '2020-04-26 00:00' not null,
  updatedAt                     timestamp default '2020-04-26 00:00' not null,
  momoUserId                    varchar(255),
  constraint uq_momo_users_userId unique (userId),
  constraint pk_momo_users primary key (id)
);

create table sales (
  id                            bigint generated by default as identity not null,
  employeeId                    bigint,
  createdAt                     timestamp default '2020-04-26 00:00' not null,
  updatedAt                     timestamp default '2020-04-26 00:00' not null,
  taxes                         jsonb,
  paymentMethod                 varchar(9) not null,
  constraint ck_sales_paymentMethod check ( paymentMethod in ('CASH','MEAL_PLAN','VISA','MOMO')),
  constraint pk_sales primary key (id)
);

create table sale_details (
  id                            bigint generated by default as identity not null,
  quantity                      integer not null,
  price                         float not null,
  itemId                        bigint,
  saleId                        bigint,
  createdAt                     timestamp default '2020-04-26 00:00' not null,
  updatedAt                     timestamp default '2020-04-26 00:00' not null,
  constraint pk_sale_details primary key (id)
);

create table students (
  id                            bigint generated by default as identity not null,
  userId                        bigint,
  dailyLimit                    float not null,
  deloop_user_id                bigint not null,
  createdAt                     timestamp default '2020-04-26 00:00' not null,
  updatedAt                     timestamp default '2020-04-26 00:00' not null,
  studentId                     varchar(255),
  constraint uq_students_userId unique (userId),
  constraint pk_students primary key (id)
);

create table users (
  id                            bigint generated by default as identity not null,
  verified                      boolean default false not null,
  locked                        boolean default false not null,
  userRoleId                    bigint,
  createdAt                     timestamp default '2020-04-26 00:00' not null,
  updatedAt                     timestamp default '2020-04-26 00:00' not null,
  username                      varchar(255) default '',
  firstName                     varchar(255) default '',
  lastName                      varchar(255) default '',
  email                         varchar(255),
  password                      varchar(255),
  telephone                     varchar(255),
  status                        varchar(8) not null,
  constraint ck_users_status check ( status in ('enabled','disabled','deleted')),
  constraint uq_users_email unique (email),
  constraint pk_users primary key (id)
);

create table user_roles (
  id                            bigint generated by default as identity not null,
  createdAt                     timestamp default '2020-04-26 00:00' not null,
  updatedAt                     timestamp default '2020-04-26 00:00' not null,
  name                          varchar(255) default '',
  description                   varchar(255) default '',
  capabilities                  varchar(255) default '',
  status                        varchar(8) not null,
  constraint ck_user_roles_status check ( status in ('enabled','disabled','deleted')),
  constraint pk_user_roles primary key (id)
);

-- foreign keys and indices
alter table employees add constraint fk_employees_userId foreign key (userId) references users (id) on delete cascade on update restrict;

create index ix_items_itemCategoryId on items (itemCategoryId);
alter table items add constraint fk_items_itemCategoryId foreign key (itemCategoryId) references item_categories (id) on delete restrict on update restrict;

alter table momo_users add constraint fk_momo_users_userId foreign key (userId) references users (id) on delete cascade on update restrict;

create index ix_sales_employeeId on sales (employeeId);
alter table sales add constraint fk_sales_employeeId foreign key (employeeId) references employees (id) on delete cascade on update restrict;

create index ix_sale_details_itemId on sale_details (itemId);
alter table sale_details add constraint fk_sale_details_itemId foreign key (itemId) references items (id) on delete restrict on update restrict;

create index ix_sale_details_saleId on sale_details (saleId);
alter table sale_details add constraint fk_sale_details_saleId foreign key (saleId) references sales (id) on delete restrict on update restrict;

alter table students add constraint fk_students_userId foreign key (userId) references users (id) on delete cascade on update restrict;

create index ix_users_userRoleId on users (userRoleId);
alter table users add constraint fk_users_userRoleId foreign key (userRoleId) references user_roles (id) on delete restrict on update restrict;
