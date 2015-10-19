ALTER TABLE course ADD COLUMN date timestamp without time zone;
ALTER TABLE course ALTER COLUMN date SET NOT NULL;
ALTER TABLE course ALTER COLUMN date SET DEFAULT ('now'::text)::date;