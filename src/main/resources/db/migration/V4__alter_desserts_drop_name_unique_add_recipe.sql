ALTER TABLE desserts DROP CONSTRAINT IF EXISTS desserts_name_key;
ALTER TABLE desserts ADD COLUMN recipe TEXT;
