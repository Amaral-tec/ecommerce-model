CREATE OR REPLACE FUNCTION drop_constraint_by_column()
RETURNS VOID AS $$
DECLARE
    delete_constraint TEXT;
BEGIN
    SELECT constraint_name INTO delete_constraint
    FROM information_schema.constraint_column_usage
    WHERE table_name = 'users_accesses' AND column_name = 'access_id'
    AND constraint_name <> 'unique_access_user';

    EXECUTE 'ALTER TABLE users_accesses DROP CONSTRAINT ' || delete_constraint;
END;
$$ LANGUAGE plpgsql;

