ALTER TABLE public.company ADD vat_id CHARACTER (15);
ALTER TABLE public.company ADD country CHARACTER (15);
ALTER TABLE public.company ADD city CHARACTER (50);
ALTER TABLE public.company ADD zip CHARACTER (10);

UPDATE public.company set vat_id = '454FERD5', country = 'Poland', city = 'Rzeszów', zip = '35-118', address = 'Pi³sudskiego 22'where id = '1';
UPDATE public.company set vat_id = '4SDS3', country = 'Poland', city = 'Rzeszów', zip = '35-118', address = 'D¹browskiego 22'where id = '2';
UPDATE public.company set vat_id = '232323AS', country = 'Poland', city = 'Rzeszów', zip = '35-118', address = 'Wyspiañskiego 22'where id = '3';
UPDATE public.company set vat_id = 'SDSS3', country = 'Poland', city = 'Rzeszów', zip = '35-118', address = 'Maczka 6'where id = '4';
UPDATE public.company set vat_id = 'DFDFDFD3', country = 'Poland', city = 'Rzeszów', zip = '35-118', address = 'Hetmañska 22'where id = '5';
UPDATE public.company set vat_id = 'SDSSSD1', country = 'Poland', city = 'Rzeszów', zip = '35-118', address = 'Solarza 8'where id = '6';
ALTER TABLE public.company ALTER COLUMN vat_id SET NOT NULL;
ALTER TABLE public.company ALTER COLUMN country SET NOT NULL;
ALTER TABLE public.company ALTER COLUMN city SET NOT NULL;
ALTER TABLE public.company ALTER COLUMN zip SET NOT NULL;
