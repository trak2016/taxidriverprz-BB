ALTER TABLE public.usr ADD login CHARACTER VARYING(50) NULL;
ALTER TABLE public.usr
 ADD CONSTRAINT unique_login UNIQUE (login);

--  Remember to set column login as NOT NULL in finall version