CREATE TABLE IF NOT EXISTS public.licenses
(
    license_id      text COLLATE pg_catalog."default" NOT NULL,
    organization_id text COLLATE pg_catalog."default" NOT NULL,
    description     text COLLATE pg_catalog."default",
    product_name    text COLLATE pg_catalog."default" NOT NULL,
    license_type    text COLLATE pg_catalog."default" NOT NULL,
    comment         text COLLATE pg_catalog."default",
    CONSTRAINT licenses_pkey PRIMARY KEY (license_id)
)
    TABLESPACE pg_default;

ALTER TABLE public.licenses
    OWNER to postgres;