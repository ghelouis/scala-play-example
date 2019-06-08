# --- Add users table

# --- !Ups
create table "users" (
    "id" UUID NOT NULL PRIMARY KEY,
    "name" TEXT,
    "age" INTEGER,
    "creation_date" TIMESTAMP
);

# --- !Downs
DROP table "users";