CREATE TABLE IF NOT EXISTS users (
    username varchar(200) NOT NULL,
    password varchar(200) NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (username)
)

CREATE TABLE IF NOT EXISTS scores (
    username varchar(200) NOT NULL,
    game varchar NOT NULL,
    score character NOT NULL,
    CONSTRAINT scores_userid_fkey FOREIGN KEY (username)
        REFERENCES public.users (username) MATCH SIMPLE
)