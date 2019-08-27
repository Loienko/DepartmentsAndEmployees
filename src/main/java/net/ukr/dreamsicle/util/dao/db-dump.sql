-- SQL Manager Lite for PostgreSQL 5.9.5.52424
-- ---------------------------------------
-- Хост         : localhost
-- База данных  : department
-- Версия       : PostgreSQL 9.6.8, compiled by Visual C++ build 1800, 64-bit


SET check_function_bodies = false;
--
-- Structure for table department (OID = 32795) :
--
SET search_path = public, pg_catalog;
CREATE TABLE public.department (
  id             bigint DEFAULT nextval(('public.department_id_seq' :: text) :: regclass) NOT NULL,
  nameDepart    varchar(50)                                                              NOT NULL,
  countEmployee bigint DEFAULT 0                                                         NOT NULL
)
WITH (oids = false
);
--
-- Structure for table employee (OID = 32826) :
--
CREATE TABLE public.employee (
  id            bigint DEFAULT nextval(('public.employee_id_seq' :: text) :: regclass) NOT NULL,
  idDepartment bigint                                                                 NOT NULL,
  name          varchar(50)                                                            NOT NULL,
  surname       varchar(50)                                                            NOT NULL,
  email         varchar(120)                                                           NOT NULL,
  date          varchar(50)                                                            NOT NULL
)
WITH (oids = false
);
--
-- Definition for sequence department_id_seq (OID = 32855) :
--
CREATE SEQUENCE public.department_id_seq
  START WITH 1
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
--
-- Definition for sequence employee_id_seq (OID = 32866) :
--
CREATE SEQUENCE public.employee_id_seq
  START WITH 1
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
--
-- Data for table public.department (OID = 32795) (LIMIT 0,3)
--
INSERT INTO department (id, nameDepart, countEmployee)
VALUES (53, 'hello', 0);

INSERT INTO department (id, nameDepart, countEmployee)
VALUES (47, 'Departss', 0);

INSERT INTO department (id, nameDepart, countEmployee)
VALUES (54, 'Ann', 0);

--
-- Definition for index employee_id_dep (OID = 32837) :
--
CREATE INDEX employee_id_dep
  ON public.employee
  USING btree (idDepartment);
--
-- Definition for index department_seq (OID = 32859) :
--
CREATE INDEX department_seq
  ON public.department
  USING btree (id);
--
-- Definition for index employee_seq (OID = 32870) :
--
CREATE INDEX employee_seq
  ON public.employee
  USING btree (id);
--
-- Definition for index department_name_depart_key (OID = 32839) :
--
ALTER TABLE ONLY department
  ADD CONSTRAINT department_name_depart_key
UNIQUE (nameDepart);
--
-- Definition for index employee_email_key (OID = 32841) :
--
ALTER TABLE ONLY employee
  ADD CONSTRAINT employee_email_key
UNIQUE (email);
--
-- Definition for index department_pkey (OID = 32857) :
--
ALTER TABLE ONLY department
  ADD CONSTRAINT department_pkey
PRIMARY KEY (id);
--
-- Definition for index employee_fk (OID = 32860) :
--
ALTER TABLE ONLY employee
  ADD CONSTRAINT employee_fk
FOREIGN KEY (idDepartment) REFERENCES department (id) ON UPDATE CASCADE ON DELETE RESTRICT;
--
-- Definition for index employee_pkey (OID = 32868) :
--
ALTER TABLE ONLY employee
  ADD CONSTRAINT employee_pkey
PRIMARY KEY (id);
--
-- Data for sequence public.department_id_seq (OID = 32855)
--
SELECT pg_catalog.setval('department_id_seq', 54, true);
--
-- Data for sequence public.employee_id_seq (OID = 32866)
--
SELECT pg_catalog.setval('employee_id_seq', 85, true);
--
-- Comments
--
COMMENT ON SCHEMA public
IS 'standard public schema';
