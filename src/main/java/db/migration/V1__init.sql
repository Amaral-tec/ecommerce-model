--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.25
-- Dumped by pg_dump version 9.5.5

-- Started on 2023-11-12 11:24:41

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE ecommerce_model;
--
-- TOC entry 2335 (class 1262 OID 19040)
-- Name: ecommerce_model; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE ecommerce_model WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';


ALTER DATABASE ecommerce_model OWNER TO postgres;

\connect ecommerce_model

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2338 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 181 (class 1259 OID 19041)
-- Name: accesses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE accesses (
    id bigint NOT NULL,
    description character varying(255) NOT NULL,
    is_deleted boolean
);


ALTER TABLE accesses OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 19046)
-- Name: accounts_payable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE accounts_payable (
    id bigint NOT NULL,
    description character varying(255) NOT NULL,
    invoice_amount numeric(19,2) NOT NULL,
    invoice_due_date date NOT NULL,
    is_deleted boolean,
    payment_amount numeric(19,2),
    payment_date date,
    status character varying(255) NOT NULL,
    individual_id bigint NOT NULL,
    legal_entity_id bigint NOT NULL,
    supplier_id bigint NOT NULL
);


ALTER TABLE accounts_payable OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 19054)
-- Name: accounts_receivable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE accounts_receivable (
    id bigint NOT NULL,
    description character varying(255) NOT NULL,
    invoice_amount numeric(19,2) NOT NULL,
    invoice_due_date date NOT NULL,
    is_deleted boolean,
    payment_amount numeric(19,2) NOT NULL,
    payment_date date,
    status character varying(255) NOT NULL,
    individual_id bigint NOT NULL,
    legal_entity_id bigint NOT NULL
);


ALTER TABLE accounts_receivable OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 19062)
-- Name: addresses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE addresses (
    id bigint NOT NULL,
    city character varying(255) NOT NULL,
    complement character varying(255),
    country_id character varying(255),
    district character varying(255) NOT NULL,
    is_deleted boolean,
    number character varying(255) NOT NULL,
    postal_code character varying(255) NOT NULL,
    state character varying(255) NOT NULL,
    street_address character varying(255) NOT NULL,
    type_address character varying(255) NOT NULL,
    person_id bigint
);


ALTER TABLE addresses OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 19070)
-- Name: discount_coupons; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE discount_coupons (
    id bigint NOT NULL,
    code character varying(255) NOT NULL,
    discount_amount numeric(19,2),
    due_date date NOT NULL,
    is_deleted boolean,
    is_expired boolean,
    is_percentage boolean,
    legal_entity_id bigint NOT NULL
);


ALTER TABLE discount_coupons OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 19075)
-- Name: individual; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE individual (
    id bigint NOT NULL,
    created_at date,
    email character varying(255) NOT NULL,
    is_deleted boolean,
    name character varying(255) NOT NULL,
    phone character varying(255) NOT NULL,
    type_person character varying(255) NOT NULL,
    cpf character varying(255) NOT NULL,
    date_birth date
);


ALTER TABLE individual OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 19083)
-- Name: invoiced_products; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE invoiced_products (
    id bigint NOT NULL,
    is_deleted boolean,
    quantity double precision NOT NULL,
    legal_entity_id bigint NOT NULL,
    product_id bigint NOT NULL,
    purchase_invoice_id bigint NOT NULL
);


ALTER TABLE invoiced_products OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 19088)
-- Name: legal_entities; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE legal_entities (
    id bigint NOT NULL,
    created_at date,
    email character varying(255) NOT NULL,
    is_deleted boolean,
    name character varying(255) NOT NULL,
    phone character varying(255) NOT NULL,
    type_person character varying(255) NOT NULL,
    cnpj character varying(255) NOT NULL,
    district_tax_id character varying(255),
    state_tax_id character varying(255),
    trade_name character varying(255) NOT NULL
);


ALTER TABLE legal_entities OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 19096)
-- Name: payment_methods; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE payment_methods (
    id bigint NOT NULL,
    description character varying(255) NOT NULL,
    is_deleted boolean
);


ALTER TABLE payment_methods OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 19101)
-- Name: product_brands; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE product_brands (
    id bigint NOT NULL,
    description character varying(255) NOT NULL,
    is_deleted boolean,
    legal_entity_id bigint NOT NULL
);


ALTER TABLE product_brands OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 19106)
-- Name: product_categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE product_categories (
    id bigint NOT NULL,
    description character varying(255) NOT NULL,
    is_deleted boolean,
    legal_entity_id bigint NOT NULL
);


ALTER TABLE product_categories OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 19111)
-- Name: product_images; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE product_images (
    id bigint NOT NULL,
    is_deleted boolean,
    original_image text NOT NULL,
    thumbnail_image text NOT NULL,
    legal_entity_id bigint NOT NULL,
    product_id bigint NOT NULL
);


ALTER TABLE product_images OWNER TO postgres;

--
-- TOC entry 193 (class 1259 OID 19119)
-- Name: product_reviews; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE product_reviews (
    id bigint NOT NULL,
    description character varying(255) NOT NULL,
    is_deleted boolean,
    note integer NOT NULL,
    review_date date NOT NULL,
    individual_id bigint NOT NULL,
    legal_entity_id bigint NOT NULL,
    product_id bigint NOT NULL,
    CONSTRAINT product_reviews_note_check CHECK (((note >= 1) AND (note <= 10)))
);


ALTER TABLE product_reviews OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 19125)
-- Name: product_sold; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE product_sold (
    id bigint NOT NULL,
    is_deleted boolean,
    quantity double precision NOT NULL,
    legal_entity_id bigint NOT NULL,
    product_id bigint NOT NULL,
    sale_id bigint NOT NULL
);


ALTER TABLE product_sold OWNER TO postgres;

--
-- TOC entry 195 (class 1259 OID 19130)
-- Name: products; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE products (
    id bigint NOT NULL,
    description text NOT NULL,
    height double precision NOT NULL,
    is_active boolean,
    is_deleted boolean,
    is_alerted boolean,
    length double precision NOT NULL,
    low_stock_alert integer,
    name character varying(255) NOT NULL,
    number_clicks integer,
    purchase_price numeric(19,2) NOT NULL,
    quantity integer NOT NULL,
    selling_price numeric(19,2) NOT NULL,
    weight double precision NOT NULL,
    width double precision NOT NULL,
    youtube_link character varying(255),
    legal_entity_id bigint NOT NULL,
    product_brand_id bigint NOT NULL,
    product_category_id bigint NOT NULL
);


ALTER TABLE products OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 19138)
-- Name: purchase_invoices; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE purchase_invoices (
    id bigint NOT NULL,
    invoice_date date NOT NULL,
    is_deleted boolean,
    number character varying(255) NOT NULL,
    tax_amount numeric(19,2) NOT NULL,
    total_amount numeric(19,2) NOT NULL,
    account_payable_id bigint NOT NULL,
    individual_id bigint NOT NULL,
    legal_entity_id bigint NOT NULL
);


ALTER TABLE purchase_invoices OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 19143)
-- Name: sales; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE sales (
    id bigint NOT NULL,
    discount numeric(19,2),
    estimated_delivery_date date NOT NULL,
    estimated_shipping_days integer NOT NULL,
    is_deleted boolean,
    label_url character varying(255),
    sale_date date NOT NULL,
    shipping_company character varying(255),
    shipping_cost numeric(19,2),
    status character varying(255) NOT NULL,
    subtotal numeric(19,2) NOT NULL,
    total_amount numeric(19,2) NOT NULL,
    tracking_number character varying(255),
    discount_coupon_id bigint,
    individual_id bigint NOT NULL,
    legal_entity_id bigint NOT NULL,
    payment_method_id bigint NOT NULL,
    sales_invoice_id bigint
);


ALTER TABLE sales OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 19151)
-- Name: sales_invoice; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE sales_invoice (
    id bigint NOT NULL,
    is_deleted boolean,
    quantity double precision NOT NULL,
    legal_entity_id bigint NOT NULL,
    product_id bigint NOT NULL,
    sale_id bigint NOT NULL
);


ALTER TABLE sales_invoice OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 19171)
-- Name: seq_access; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_access
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_access OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 19173)
-- Name: seq_account_payable; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_account_payable
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_account_payable OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 19175)
-- Name: seq_account_receivable; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_account_receivable
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_account_receivable OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 19177)
-- Name: seq_address; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_address
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_address OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 19179)
-- Name: seq_discount_coupon; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_discount_coupon
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_discount_coupon OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 19181)
-- Name: seq_invoice_product; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_invoice_product
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_invoice_product OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 19183)
-- Name: seq_payment_method; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_payment_method
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_payment_method OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 19185)
-- Name: seq_person; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_person
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_person OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 19187)
-- Name: seq_product; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_product
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_product OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 19189)
-- Name: seq_product_brand; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_product_brand
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_product_brand OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 19191)
-- Name: seq_product_category; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_product_category
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_product_category OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 19193)
-- Name: seq_product_image; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_product_image
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_product_image OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 19195)
-- Name: seq_product_review; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_product_review
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_product_review OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 19197)
-- Name: seq_product_sold; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_product_sold
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_product_sold OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 19199)
-- Name: seq_purchase_invoice; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_purchase_invoice
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_purchase_invoice OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 19201)
-- Name: seq_sale; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_sale
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_sale OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 19203)
-- Name: seq_sales_invoice; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_sales_invoice
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_sales_invoice OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 19205)
-- Name: seq_user; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_user
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_user OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 19156)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE users (
    id bigint NOT NULL,
    is_deleted boolean,
    login character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    password_created_at date NOT NULL,
    individual_id bigint NOT NULL
);


ALTER TABLE users OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 19164)
-- Name: users_accesses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE users_accesses (
    user_id bigint NOT NULL,
    access_id bigint NOT NULL
);


ALTER TABLE users_accesses OWNER TO postgres;

--
-- TOC entry 2293 (class 0 OID 19041)
-- Dependencies: 181
-- Data for Name: accesses; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO accesses (id, description, is_deleted) VALUES (1, 'ROLE_USER', NULL);


--
-- TOC entry 2294 (class 0 OID 19046)
-- Dependencies: 182
-- Data for Name: accounts_payable; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2295 (class 0 OID 19054)
-- Dependencies: 183
-- Data for Name: accounts_receivable; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2296 (class 0 OID 19062)
-- Dependencies: 184
-- Data for Name: addresses; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO addresses (id, city, complement, country_id, district, is_deleted, number, postal_code, state, street_address, type_address, person_id) VALUES (1, 'Natal', 'Apto 307 Bl 3', '55', 'Planalto', NULL, '820', '59073070', 'RN', 'Rua Engenheiro João Helio Alves Rocha', 'BILLING', 1);
INSERT INTO addresses (id, city, complement, country_id, district, is_deleted, number, postal_code, state, street_address, type_address, person_id) VALUES (2, 'Natal', 'Apto 307 Bl 3', '55', 'Planalto', NULL, '820', '59073070', 'RN', 'Rua Engenheiro João Hélio Alves Rocha', 'SHIPPING', 1);
INSERT INTO addresses (id, city, complement, country_id, district, is_deleted, number, postal_code, state, street_address, type_address, person_id) VALUES (3, 'Natal', NULL, '55', 'Tirol', NULL, '776', '59020300', 'RN', 'Av Campos Sales', 'BILLING', 2);
INSERT INTO addresses (id, city, complement, country_id, district, is_deleted, number, postal_code, state, street_address, type_address, person_id) VALUES (4, 'Natal', NULL, '55', 'Tirol', NULL, '776', '59020300', 'RN', 'Av Campos Sales', 'SHIPPING', 2);


--
-- TOC entry 2297 (class 0 OID 19070)
-- Dependencies: 185
-- Data for Name: discount_coupons; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2298 (class 0 OID 19075)
-- Dependencies: 186
-- Data for Name: individual; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO individual (id, created_at, email, is_deleted, name, phone, type_person, cpf, date_birth) VALUES (1, '2023-11-12', 'amaral_adm@hotmail.com', NULL, 'Leandro Amaral', '84999990000', 'INDIVIDUAL', '25292264075', '1990-10-10');


--
-- TOC entry 2299 (class 0 OID 19083)
-- Dependencies: 187
-- Data for Name: invoiced_products; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2300 (class 0 OID 19088)
-- Dependencies: 188
-- Data for Name: legal_entities; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO legal_entities (id, created_at, email, is_deleted, name, phone, type_person, cnpj, district_tax_id, state_tax_id, trade_name) VALUES (2, '2023-11-12', 'company@emial.com', NULL, 'Model Ltda', '8430301010', 'LEGAL', '33379009000104', '897858', '305987', 'Model Ltda');


--
-- TOC entry 2301 (class 0 OID 19096)
-- Dependencies: 189
-- Data for Name: payment_methods; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2302 (class 0 OID 19101)
-- Dependencies: 190
-- Data for Name: product_brands; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2303 (class 0 OID 19106)
-- Dependencies: 191
-- Data for Name: product_categories; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2304 (class 0 OID 19111)
-- Dependencies: 192
-- Data for Name: product_images; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2305 (class 0 OID 19119)
-- Dependencies: 193
-- Data for Name: product_reviews; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2306 (class 0 OID 19125)
-- Dependencies: 194
-- Data for Name: product_sold; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2307 (class 0 OID 19130)
-- Dependencies: 195
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2308 (class 0 OID 19138)
-- Dependencies: 196
-- Data for Name: purchase_invoices; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2309 (class 0 OID 19143)
-- Dependencies: 197
-- Data for Name: sales; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2310 (class 0 OID 19151)
-- Dependencies: 198
-- Data for Name: sales_invoice; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2339 (class 0 OID 0)
-- Dependencies: 201
-- Name: seq_access; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_access', 1, false);


--
-- TOC entry 2340 (class 0 OID 0)
-- Dependencies: 202
-- Name: seq_account_payable; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_account_payable', 1, false);


--
-- TOC entry 2341 (class 0 OID 0)
-- Dependencies: 203
-- Name: seq_account_receivable; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_account_receivable', 1, false);


--
-- TOC entry 2342 (class 0 OID 0)
-- Dependencies: 204
-- Name: seq_address; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_address', 1, false);


--
-- TOC entry 2343 (class 0 OID 0)
-- Dependencies: 205
-- Name: seq_discount_coupon; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_discount_coupon', 1, false);


--
-- TOC entry 2344 (class 0 OID 0)
-- Dependencies: 206
-- Name: seq_invoice_product; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_invoice_product', 1, false);


--
-- TOC entry 2345 (class 0 OID 0)
-- Dependencies: 207
-- Name: seq_payment_method; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_payment_method', 1, false);


--
-- TOC entry 2346 (class 0 OID 0)
-- Dependencies: 208
-- Name: seq_person; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_person', 1, false);


--
-- TOC entry 2347 (class 0 OID 0)
-- Dependencies: 209
-- Name: seq_product; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_product', 1, false);


--
-- TOC entry 2348 (class 0 OID 0)
-- Dependencies: 210
-- Name: seq_product_brand; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_product_brand', 1, false);


--
-- TOC entry 2349 (class 0 OID 0)
-- Dependencies: 211
-- Name: seq_product_category; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_product_category', 1, false);


--
-- TOC entry 2350 (class 0 OID 0)
-- Dependencies: 212
-- Name: seq_product_image; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_product_image', 1, false);


--
-- TOC entry 2351 (class 0 OID 0)
-- Dependencies: 213
-- Name: seq_product_review; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_product_review', 1, false);


--
-- TOC entry 2352 (class 0 OID 0)
-- Dependencies: 214
-- Name: seq_product_sold; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_product_sold', 1, false);


--
-- TOC entry 2353 (class 0 OID 0)
-- Dependencies: 215
-- Name: seq_purchase_invoice; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_purchase_invoice', 1, false);


--
-- TOC entry 2354 (class 0 OID 0)
-- Dependencies: 216
-- Name: seq_sale; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_sale', 1, false);


--
-- TOC entry 2355 (class 0 OID 0)
-- Dependencies: 217
-- Name: seq_sales_invoice; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_sales_invoice', 1, false);


--
-- TOC entry 2356 (class 0 OID 0)
-- Dependencies: 218
-- Name: seq_user; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_user', 1, false);


--
-- TOC entry 2311 (class 0 OID 19156)
-- Dependencies: 199
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO users (id, is_deleted, login, password, password_created_at, individual_id) VALUES (1, NULL, 'amaral_adm@hotmail.com', '$2a$10$bX6JMn6LUgyUpSrg3/.s2.S9gVeV5fVmrjDYRU6vBJvLVPTcfGt.2', '2023-11-12', 1);


--
-- TOC entry 2312 (class 0 OID 19164)
-- Dependencies: 200
-- Data for Name: users_accesses; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO users_accesses (user_id, access_id) VALUES (1, 1);


--
-- TOC entry 2337 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2023-11-12 11:24:42

--
-- PostgreSQL database dump complete
--

