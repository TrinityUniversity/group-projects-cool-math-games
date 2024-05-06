CREATE DATABASE coolmathgames
    WITH
    OWNER = bennettmach
    ENCODING = 'UTF8'
    LC_COLLATE = 'C'
    LC_CTYPE = 'C'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE TABLE IF NOT EXISTS public.games
(
    gameid integer NOT NULL,
    route character varying COLLATE pg_catalog."default",
    CONSTRAINT games_pkey PRIMARY KEY (gameid)
)


CREATE TABLE IF NOT EXISTS public.users
(
    userid integer NOT NULL,
    username character varying COLLATE pg_catalog."default" NOT NULL,
    email character varying COLLATE pg_catalog."default" NOT NULL,
    password character varying COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (userid)
)

CREATE TABLE IF NOT EXISTS public.scores
(
    userid integer NOT NULL,
    gameid integer NOT NULL,
    score character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT scores_gameid_fkey FOREIGN KEY (gameid),
        REFERENCES public.games (gameid) MATCH SIMPLE
    CONSTRAINT scores_userid_fkey FOREIGN KEY (userid)
        REFERENCES public.users (userid) MATCH SIMPLE
)