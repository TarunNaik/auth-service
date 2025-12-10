-- 1. Add a new column with UUID type
ALTER TABLE users
ADD COLUMN id_uuid UUID;

-- 2. Convert existing string IDs to UUID (if they are valid UUID strings)
UPDATE users
SET id_uuid = id::uuid;

-- 3. Drop old column and rename new one
ALTER TABLE users
DROP COLUMN id;

ALTER TABLE users
RENAME COLUMN id_uuid TO id;

-- 4. (Optional) Set as primary key if needed
ALTER TABLE users
ADD PRIMARY KEY (id);
