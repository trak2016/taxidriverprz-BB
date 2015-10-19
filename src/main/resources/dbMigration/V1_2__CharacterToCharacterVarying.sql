ALTER TABLE public.usr ADD password CHARACTER VARYING(100) NULL;

ALTER TABLE public.car ALTER COLUMN plate_number TYPE CHARACTER VARYING(20) USING plate_number::CHARACTER VARYING(20);
ALTER TABLE public.car ALTER COLUMN brand_model TYPE CHARACTER VARYING(50) USING brand_model::CHARACTER VARYING(50);
ALTER TABLE public.car ALTER COLUMN capacity TYPE CHARACTER VARYING(20) USING capacity::CHARACTER VARYING(20);
ALTER TABLE public.company ALTER COLUMN name TYPE CHARACTER VARYING(50) USING name::CHARACTER VARYING(50);
ALTER TABLE public.company ALTER COLUMN phone TYPE CHARACTER VARYING(15) USING phone::CHARACTER VARYING(15);
ALTER TABLE public.company ALTER COLUMN address TYPE CHARACTER VARYING(50) USING address::CHARACTER VARYING(50);
ALTER TABLE public.company ALTER COLUMN logo TYPE CHARACTER VARYING(100) USING logo::CHARACTER VARYING(100);
ALTER TABLE public.usr ALTER COLUMN name TYPE CHARACTER VARYING(50) USING name::CHARACTER VARYING(50);
ALTER TABLE public.usr ALTER COLUMN lastname TYPE CHARACTER VARYING(50) USING lastname::CHARACTER VARYING(50);
ALTER TABLE public.usr ALTER COLUMN phone TYPE CHARACTER VARYING(15) USING phone::CHARACTER VARYING(15);
