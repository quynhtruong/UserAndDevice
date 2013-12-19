CREATE FUNCTION script()
  RETURNS VOID
AS $$
BEGIN

  CREATE SEQUENCE hibernate_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  ALTER TABLE hibernate_sequence
  OWNER TO postgres;

  CREATE TABLE users
    (
      web_id  BIGINT  NOT NULL DEFAULT nextval('hibernate_sequence'),
      name CHARACTER VARYING,
      password CHARACTER VARYING,
      last_updated  timestamp without time zone DEFAULT now() NOT NULL,
      CONSTRAINT user_pkey PRIMARY KEY (web_id)
    )
    WITH (
      OIDS = FALSE
    );

  CREATE INDEX user_web_id_idx
    ON users
    USING BTREE
    (web_id);

  CREATE TABLE to_do_list
    (
      web_id  BIGINT  NOT NULL DEFAULT nextval('hibernate_sequence'),
      description CHARACTER VARYING,
      is_deleted BOOLEAN,
      last_updated  timestamp without time zone DEFAULT now() NOT NULL,
      user_id BIGINT,
      CONSTRAINT to_do_list_pkey PRIMARY KEY (web_id),
      CONSTRAINT user_fk FOREIGN KEY (user_id)
      REFERENCES users (web_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
    )
    WITH (
      OIDS = FALSE
    );

  CREATE INDEX to_do_list_web_id_idx
    ON to_do_list
    USING BTREE
    (web_id);

  CREATE TABLE device
  (
    web_id  BIGINT  NOT NULL DEFAULT nextval('hibernate_sequence'),
    name CHARACTER VARYING,
    last_updated  timestamp without time zone DEFAULT now() NOT NULL,
    user_id BIGINT,
    CONSTRAINT device_pkey PRIMARY KEY (web_id),
    CONSTRAINT user_fk FOREIGN KEY (user_id)
    REFERENCES users (web_id) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION
  )
  WITH (
    OIDS = FALSE
  );

  CREATE INDEX device_web_id_idx
  ON device
  USING BTREE
  (web_id);

END;
$$ LANGUAGE plpgsql;

SELECT
  script();
DROP FUNCTION script();
