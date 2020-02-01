
insert into eqbdataapi_admin_users(login_id, password,active,access_level, name)
values('default_admin','$2a$10$xA/nHOsOMQPvUpqpot4ogeX5B67n40e2aO2tGze2V/mm2hDHRCBTy',0,5,'DEFAULT ADMIN');

insert into eqbdataapi_admin_users(login_id, password,active,access_level, name)
values('admin1','$2a$10$xA/nHOsOMQPvUpqpot4ogeX5B67n40e2aO2tGze2V/mm2hDHRCBTy',1,1,'Super Admin');

insert into eqbdataapi_customers(login_id, password, active, base_access_limit,reset_day_of_month,company_name,email)
values('internal_customer','$2a$10$xA/nHOsOMQPvUpqpot4ogeX5B67n40e2aO2tGze2V/mm2hDHRCBTy',1,4000,1,'EQUIBASE', 'info@equibase.com');

