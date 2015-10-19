ALTER TABLE public.course ADD customer_phone_number INT NULL;

ALTER TABLE public.course ALTER COLUMN cost SET DEFAULT 0;

--  Remember to set column login as NOT NULL in finall version