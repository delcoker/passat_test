-- drop all foreign keys
alter table if exists employees drop constraint if exists fk_employees_userId;

alter table if exists items drop constraint if exists fk_items_itemCategoryId;
drop index if exists ix_items_itemCategoryId;

alter table if exists momo_users drop constraint if exists fk_momo_users_userId;

alter table if exists sales drop constraint if exists fk_sales_employeeId;
drop index if exists ix_sales_employeeId;

alter table if exists sale_details drop constraint if exists fk_sale_details_itemId;
drop index if exists ix_sale_details_itemId;

alter table if exists sale_details drop constraint if exists fk_sale_details_saleId;
drop index if exists ix_sale_details_saleId;

alter table if exists students drop constraint if exists fk_students_userId;

alter table if exists users drop constraint if exists fk_users_userRoleId;
drop index if exists ix_users_userRoleId;

-- drop all
drop table if exists employees cascade;

drop table if exists items cascade;

drop table if exists item_categories cascade;

drop table if exists momo_users cascade;

drop table if exists sales cascade;

drop table if exists sale_details cascade;

drop table if exists students cascade;

drop table if exists users cascade;

drop table if exists user_roles cascade;

