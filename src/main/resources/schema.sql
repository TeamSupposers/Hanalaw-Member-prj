DROP TABLE IF EXISTS TB_HANA_MBR;
DROP TABLE IF EXISTS TB_HANA_AUTHORITIES;

CREATE TABLE TB_HANA_MBR (
	USERNAME VARCHAR(50) not null primary key,
	PASSWORD VARCHAR(50) not null,
	ENABLED boolean not null
);

CREATE TABLE TB_HANA_AUTHORITIES (
    USERNAME VARCHAR(50) not null,
    AUTHORITY VARCHAR(50) not null,
    CONSTRAINT FK_AUTHIRITIES_USERS FOREIGN KEY(USERNAME) REFERENCES TB_HANA_MBR(USERNAME)
);

CREATE UNIQUE INDEX IX_AUTH_USERNAME ON TB_HANA_AUTHORITIES (USERNAME,AUTHORITY);