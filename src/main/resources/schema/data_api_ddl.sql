/*
Created: 12/16/2015
Modified: 2/10/2016
Model: api_customers_sybase
Database: Sybase ASE 15.7
*/

-- Create tables section -------------------------------------------------

-- Table eqbdataapi_admin_actions

CREATE TABLE [eqbdataapi_admin_actions]
(
   [id] numeric(10,0) IDENTITY NOT NULL,
   [admin_id] numeric(10,0) NOT NULL,
   [customer_id] numeric(10,0) NOT NULL,
   [ip4_address] char(15) NULL,
   [ip6_address] char(39) NULL,
   [description] text NULL,
   [timestamp] datetime DEFAULT 'CURRENT_TIMESTAMP' NOT NULL
)
go

CREATE INDEX [fk_customer_id] ON [eqbdataapi_admin_actions] ([customer_id])
go

CREATE INDEX [fk_admin_id] ON [eqbdataapi_admin_actions] ([admin_id])
go

ALTER TABLE [eqbdataapi_admin_actions] ADD CONSTRAINT [PK_admin_actions] PRIMARY KEY ([id])
go

-- Table eqbdataapi_admin_users

CREATE TABLE [eqbdataapi_admin_users]
(
   [id] numeric(10,0) IDENTITY NOT NULL,
   [login_id] char(20) NOT NULL,
   [password] binary(60) NOT NULL,
   [active] smallint DEFAULT 1 NULL,
   [access_level] smallint DEFAULT 3 NULL,
   [name] char(50) NOT NULL
)
go

CREATE UNIQUE INDEX [login_id] ON [eqbdataapi_admin_users] ([login_id])
go

ALTER TABLE [eqbdataapi_admin_users] ADD CONSTRAINT [PK_admin_users] PRIMARY KEY ([id])
go

-- Table eqbdataapi_requests

CREATE TABLE [eqbdataapi_requests]
(
   [id] numeric(10,0) IDENTITY NOT NULL,
   [cust_prod_id] numeric(10,0) NOT NULL,
   [ip4_address] char(15) NOT NULL,
   [ip6_address] char(39) NULL,
   [endpoint] varchar(254) NOT NULL,
   [response_code] int NOT NULL,
   [timestamp] datetime DEFAULT 'CURRENT_TIMESTAMP' NOT NULL,
   [api_key] char(40) NOT NULL,
   [attribute1] char(20) NULL
)
go

CREATE INDEX [fk_cust_prod_id] ON [eqbdataapi_requests] ([cust_prod_id])
go

ALTER TABLE [eqbdataapi_requests] ADD CONSTRAINT [PK_api_requests] PRIMARY KEY ([id])
go

-- Table eqbdataapi_customer_api_keys

CREATE TABLE [eqbdataapi_customer_api_keys]
(
   [id] numeric(10,0) IDENTITY NOT NULL,
   [customer_id] numeric(10,0) NOT NULL,
   [api_key] char(40) NOT NULL,
   [active] smallint DEFAULT 1 NULL,
   [timestamp] datetime DEFAULT 'CURRENT_TIMESTAMP' NOT NULL,
   [disabled_at] datetime NULL
)
go

CREATE INDEX [fk_customer_id] ON [eqbdataapi_customer_api_keys] ([customer_id])
go

ALTER TABLE [eqbdataapi_customer_api_keys] ADD CONSTRAINT [PK_customer_api_keys] PRIMARY KEY ([id])
go

-- Table eqbdataapi_customer_api_limits

CREATE TABLE [eqbdataapi_customer_api_limits]
(
   [id] numeric(10,0) IDENTITY NOT NULL,
   [customer_id] numeric(10,0) NOT NULL,
   [start_date] date NOT NULL,
   [end_date] date NOT NULL,
   [current_access_limit] bigint DEFAULT 5000 NULL,
   [current_access_count] bigint DEFAULT 0 NULL
)
go

CREATE INDEX [fk_customer_id] ON [eqbdataapi_customer_api_limits] ([customer_id])
go

ALTER TABLE [eqbdataapi_customer_api_limits] ADD CONSTRAINT [PK_customer_api_limits] PRIMARY KEY ([id])
go

-- Table eqbdataapi_customer_products

CREATE TABLE [eqbdataapi_customer_products]
(
   [id] numeric(10,0) IDENTITY NOT NULL,
   [customer_id] numeric(10,0) NOT NULL,
   [product_id] numeric(10,0) NOT NULL,
   [active] smallint DEFAULT 1 NULL,
   [all_tracks] smallint DEFAULT 1 NULL,
   [days_back] smallint DEFAULT 7 NULL
)
go

CREATE INDEX [fk_customer_id] ON [eqbdataapi_customer_products] ([customer_id])
go

CREATE INDEX [fk_product_id] ON [eqbdataapi_customer_products] ([product_id])
go

ALTER TABLE [eqbdataapi_customer_products] ADD CONSTRAINT [PK_customer_products] PRIMARY KEY ([id])
go

-- Table eqbdataapi_customers

CREATE TABLE [eqbdataapi_customers]
(
   [id] numeric(10,0) IDENTITY NOT NULL,
   [login_id] char(20) NOT NULL,
   [password] binary(60) NOT NULL,
   [email] varchar(256) NULL,
   [active] smallint DEFAULT 1 NULL,
   [base_access_limit] bigint DEFAULT 5000 NULL,
   [reset_day_of_month] smallint DEFAULT 1 NULL,
   [company_name] varchar(256) NULL
)
go

CREATE UNIQUE INDEX [customer_id] ON [eqbdataapi_customers] ([login_id])
go

ALTER TABLE [eqbdataapi_customers] ADD CONSTRAINT [PK_customers] PRIMARY KEY ([id])
go

-- Table eqbdataapi_product_tracks

CREATE TABLE [eqbdataapi_product_tracks]
(
   [id] numeric(10,0) IDENTITY NOT NULL,
   [customer_product_id] numeric(10,0) NOT NULL,
   [track_id] char(3) NOT NULL
)
go

CREATE INDEX [fk_customer_product] ON [eqbdataapi_product_tracks] ([customer_product_id])
go

CREATE INDEX [fk_track_code] ON [eqbdataapi_product_tracks] ([track_id])
go

ALTER TABLE [eqbdataapi_product_tracks] ADD CONSTRAINT [PK_product_tracks] PRIMARY KEY ([id])
go

-- Table eqbdataapi_products

CREATE TABLE [eqbdataapi_products]
(
   [id] numeric(10,0) IDENTITY NOT NULL,
   [product_type] varchar(40) NOT NULL,
   [product_level] varchar(40) NOT NULL,
   [label] varchar(256) NOT NULL,
   [cache_time] smallint DEFAULT 0 NOT NULL
)
go

CREATE UNIQUE INDEX [product_type_level] ON [eqbdataapi_products] ([product_type],[product_level])
go

ALTER TABLE [eqbdataapi_products] ADD CONSTRAINT [PK_products] PRIMARY KEY ([id])
go

-- Table eqbdataapi_tracks

CREATE TABLE [eqbdataapi_tracks]
(
   [id] char(3) NOT NULL,
   [country] char(3) NOT NULL,
   [name] varchar(254) NOT NULL,
   [type] char(1) DEFAULT 'T' NOT NULL
)
go

ALTER TABLE [eqbdataapi_tracks] ADD CONSTRAINT [PK_tracks] PRIMARY KEY ([id])
go

-- Table eqbdataapi_data_intercepts

CREATE TABLE [eqbdataapi_data_intercepts]
(
   [id] numeric(10,0) IDENTITY NOT NULL,
   [cust_prod_id] numeric(10,0) NOT NULL,
   [track_id] char(3) NOT NULL,
   [race_date] date NOT NULL,
   [race_number] smallint NOT NULL,
   [data_to_send] text NOT NULL,
   [timestamp] datetime DEFAULT 'CURRENT_TIMESTAMP' NOT NULL
)
go

CREATE INDEX [fk_cust_prod_id] ON [eqbdataapi_data_intercepts] ([cust_prod_id])
go

CREATE INDEX [fk_track_code] ON [eqbdataapi_data_intercepts] ([track_id])
go

ALTER TABLE [eqbdataapi_data_intercepts] ADD CONSTRAINT [PK_data_intercepts] PRIMARY KEY ([id])
go

-- Create relationships section ------------------------------------------------- 

ALTER TABLE [eqbdataapi_admin_actions] ADD CONSTRAINT [fk_admin_cust_id] FOREIGN KEY ([customer_id]) REFERENCES [eqbdataapi_customers] ([id])
go

ALTER TABLE [eqbdataapi_admin_actions] ADD CONSTRAINT [fk_admin_admin_id] FOREIGN KEY ([admin_id]) REFERENCES [eqbdataapi_admin_users] ([id])
go

ALTER TABLE [eqbdataapi_requests] ADD CONSTRAINT [fk_access_cust_prod_id] FOREIGN KEY ([cust_prod_id]) REFERENCES [eqbdataapi_customer_products] ([id])
go

ALTER TABLE [eqbdataapi_customer_api_keys] ADD CONSTRAINT [fk_api_key_customer_id] FOREIGN KEY ([customer_id]) REFERENCES [eqbdataapi_customers] ([id])
go

ALTER TABLE [eqbdataapi_customer_api_limits] ADD CONSTRAINT [fk_api_cust_limits_customer_id] FOREIGN KEY ([customer_id]) REFERENCES [eqbdataapi_customers] ([id])
go

ALTER TABLE [eqbdataapi_customer_products] ADD CONSTRAINT [fk_prod_customer_id] FOREIGN KEY ([customer_id]) REFERENCES [eqbdataapi_customers] ([id])
go

ALTER TABLE [eqbdataapi_customer_products] ADD CONSTRAINT [fk_prod_product_id] FOREIGN KEY ([product_id]) REFERENCES [eqbdataapi_products] ([id])
go

ALTER TABLE [eqbdataapi_product_tracks] ADD CONSTRAINT [fk_track_cust_prod_id] FOREIGN KEY ([customer_product_id]) REFERENCES [eqbdataapi_customer_products] ([id])
go

ALTER TABLE [eqbdataapi_product_tracks] ADD CONSTRAINT [fk_track_code] FOREIGN KEY ([track_id]) REFERENCES [eqbdataapi_tracks] ([id])
go

ALTER TABLE [eqbdataapi_data_intercepts] ADD CONSTRAINT [fk_intercept_cust_prod_id] FOREIGN KEY ([cust_prod_id]) REFERENCES [eqbdataapi_customer_products] ([id])
go

ALTER TABLE [eqbdataapi_data_intercepts] ADD CONSTRAINT [fk_intercept_track_code] FOREIGN KEY ([track_id]) REFERENCES [eqbdataapi_tracks] ([id])
go



-- Grant permissions section -------------------------------------------------


