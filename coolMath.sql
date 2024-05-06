CREATE TABLE IF NOT EXISTS public.users
(
    userid integer NOT NULL,
    username character NOT NULL,
    email character NOT NULL,
    password character varying COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (userid)
)

CREATE TABLE IF NOT EXISTS public.scores
(
    userid integer NOT NULL,
    game varchar NOT NULL,
    score character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT scores_userid_fkey FOREIGN KEY (userid)
        REFERENCES public.users (userid) MATCH SIMPLE
)