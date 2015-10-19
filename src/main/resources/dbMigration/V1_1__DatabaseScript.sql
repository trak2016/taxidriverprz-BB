--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.4
-- Dumped by pg_dump version 9.4.4
-- Started on 2015-08-28 10:01:07

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 186 (class 3079 OID 11855)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2072 (class 0 OID 0)
-- Dependencies: 186
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;



--
-- TOC entry 179 (class 1259 OID 16528)
-- Name: car_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE car_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE car_id_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 177 (class 1259 OID 16486)
-- Name: car; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE car (
    id bigint DEFAULT nextval('car_id_seq'::regclass) NOT NULL,
    plate_number character(20) NOT NULL,
    brand_model character(50) NOT NULL,
    year_of_prod date NOT NULL,
    status boolean NOT NULL,
    capacity character(20) NOT NULL,
    number_of_seats smallint NOT NULL,
    company_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE car OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 16541)
-- Name: company_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE company_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE company_id_seq OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 16478)
-- Name: company; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE company (
    id bigint DEFAULT nextval('company_id_seq'::regclass) NOT NULL,
    name character(50) NOT NULL,
    phone character(15) NOT NULL,
    address character(50) NOT NULL,
    logo text
);


ALTER TABLE company OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 16543)
-- Name: course_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE course_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE course_id_seq OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 16442)
-- Name: course; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE course (
    id bigint DEFAULT nextval('course_id_seq'::regclass) NOT NULL,
    cost real NOT NULL,
    distance real NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE course OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 16538)
-- Name: role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE role_id_seq OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 16399)
-- Name: role; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE role (
    id bigint DEFAULT nextval('role_id_seq'::regclass) NOT NULL,
    name character varying(30) NOT NULL,
    priority smallint NOT NULL
);


ALTER TABLE role OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 16545)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE usr_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE usr_id_seq OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 16394)
-- Name: usr; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE "usr" (
    id bigint DEFAULT nextval('usr_id_seq'::regclass) NOT NULL,
    name character(50) NOT NULL,
    lastname character(50) NOT NULL,
    dob date NOT NULL,
    phone character(15)
);


ALTER TABLE "usr" OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 16547)
-- Name: user_company_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_company_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_company_id_seq OWNER TO postgres;

--
-- TOC entry 178 (class 1259 OID 16503)
-- Name: user_company; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE user_company (
    id bigint DEFAULT nextval('user_company_id_seq'::regclass) NOT NULL,
    user_id bigint NOT NULL,
    company_id bigint NOT NULL
);


ALTER TABLE user_company OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 16549)
-- Name: user_role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_role_id_seq OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 16407)
-- Name: user_role; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE user_role (
    id bigint DEFAULT nextval('user_role_id_seq'::regclass) NOT NULL,
    user_id integer NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE user_role OWNER TO postgres;

--
-- TOC entry 1942 (class 2606 OID 16490)
-- Name: car_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY car
    ADD CONSTRAINT car_pkey PRIMARY KEY (id);


--
-- TOC entry 1940 (class 2606 OID 16485)
-- Name: company_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY company
    ADD CONSTRAINT company_pkey PRIMARY KEY (id);


--
-- TOC entry 1937 (class 2606 OID 16467)
-- Name: course_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY course
    ADD CONSTRAINT course_pkey PRIMARY KEY (id);


--
-- TOC entry 1931 (class 2606 OID 16448)
-- Name: role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- TOC entry 1948 (class 2606 OID 16507)
-- Name: user_company_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY user_company
    ADD CONSTRAINT user_company_pkey PRIMARY KEY (id);


--
-- TOC entry 1929 (class 2606 OID 16398)
-- Name: user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY "usr"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- TOC entry 1935 (class 2606 OID 16459)
-- Name: user_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY user_role
    ADD CONSTRAINT user_role_pkey PRIMARY KEY (id);


--
-- TOC entry 1943 (class 1259 OID 16502)
-- Name: fki_company_car; Type: INDEX; Schema: public; Owner: postgres; Tablespace:
--

CREATE INDEX fki_company_car ON car USING btree (company_id);


--
-- TOC entry 1945 (class 1259 OID 16519)
-- Name: fki_company_user_company; Type: INDEX; Schema: public; Owner: postgres; Tablespace:
--

CREATE INDEX fki_company_user_company ON user_company USING btree (company_id);


--
-- TOC entry 1932 (class 1259 OID 16423)
-- Name: fki_role_user_role; Type: INDEX; Schema: public; Owner: postgres; Tablespace:
--

CREATE INDEX fki_role_user_role ON user_role USING btree (role_id);


--
-- TOC entry 1944 (class 1259 OID 16496)
-- Name: fki_user_car; Type: INDEX; Schema: public; Owner: postgres; Tablespace:
--

CREATE INDEX fki_user_car ON car USING btree (user_id);


--
-- TOC entry 1938 (class 1259 OID 16477)
-- Name: fki_user_course; Type: INDEX; Schema: public; Owner: postgres; Tablespace:
--

CREATE INDEX fki_user_course ON course USING btree (user_id);


--
-- TOC entry 1946 (class 1259 OID 16513)
-- Name: fki_user_user_company; Type: INDEX; Schema: public; Owner: postgres; Tablespace:
--

CREATE INDEX fki_user_user_company ON user_company USING btree (user_id);


--
-- TOC entry 1933 (class 1259 OID 16417)
-- Name: fki_user_user_role; Type: INDEX; Schema: public; Owner: postgres; Tablespace:
--

CREATE INDEX fki_user_user_role ON user_role USING btree (user_id);


--
-- TOC entry 1953 (class 2606 OID 16497)
-- Name: fk_company_car; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY car
    ADD CONSTRAINT fk_company_car FOREIGN KEY (company_id) REFERENCES company(id);


--
-- TOC entry 1955 (class 2606 OID 16514)
-- Name: fk_company_user_company; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_company
    ADD CONSTRAINT fk_company_user_company FOREIGN KEY (company_id) REFERENCES company(id);


--
-- TOC entry 1949 (class 2606 OID 16449)
-- Name: fk_role_user_role; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_role
    ADD CONSTRAINT fk_role_user_role FOREIGN KEY (role_id) REFERENCES role(id);


--
-- TOC entry 1952 (class 2606 OID 16491)
-- Name: fk_user_car; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY car
    ADD CONSTRAINT fk_user_car FOREIGN KEY (user_id) REFERENCES "usr"(id);


--
-- TOC entry 1951 (class 2606 OID 16472)
-- Name: fk_user_course; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY course
    ADD CONSTRAINT fk_user_course FOREIGN KEY (user_id) REFERENCES "usr"(id);


--
-- TOC entry 1954 (class 2606 OID 16508)
-- Name: fk_user_user_company; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_company
    ADD CONSTRAINT fk_user_user_company FOREIGN KEY (user_id) REFERENCES "usr"(id);


--
-- TOC entry 1950 (class 2606 OID 16437)
-- Name: fk_user_user_role; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_role
    ADD CONSTRAINT fk_user_user_role FOREIGN KEY (user_id) REFERENCES "usr"(id);


--
-- TOC entry 2071 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-08-28 10:01:07

--
-- PostgreSQL database dump complete
--

