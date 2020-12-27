CREATE TABLE IF NOT EXISTS
    money_given (
        token varchar(10) NOT NULL
        , user_id int NOT NULL
        , cost int NOT NULL
        , PRIMARY KEY (token, user_id)
);

CREATE TABLE IF NOT EXISTS
    money_share (
        token varchar(10) NOT NULL
        , user_id int NOT NULL
        , room_id varchar(255) NOT NULL
        , budget int NOT NULL -- 뿌릴 금액
        , amount int NOT NULL -- 뿌릴 개수
        , balance int NOT NULL -- 남은 개수
        , made_time bigint NOT NULL
        , version_balance int default 0 NOT NULL
        , PRIMARY KEY (token)
);

CREATE TABLE IF NOT EXISTS
    money_dividen (
        token varchar(10) NOT NULL
        , cost int NOT NULL -- 금액
        , amount int NOT NULL -- 개수
        , balance int NOT NULL -- 남은 개수
        , version_balance int default 0 NOT NULL
        , PRIMARY KEY (token, cost)
);



