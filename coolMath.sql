CREATE TABLE IF NOT EXISTS public.users
(
    userid varchar(20) NOT NULL,
    username varchar(200) NOT NULL,
    password varchar(200) NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (userid)
)

CREATE TABLE IF NOT EXISTS public.scores
(
    userid varchar(20) NOT NULL,
    game varchar(20) NOT NULL,
    score varchar(20) NOT NULL,
    CONSTRAINT scores_userid_fkey FOREIGN KEY (userid)
        REFERENCES public.users (userid) MATCH SIMPLE
)