drop table student if exists;

CREATE TABLE student (
  id bigint  auto_increment,
  name varchar(255) DEFAULT NULL,
  class_name varchar(255) DEFAULT NULL,
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_user varchar(255) DEFAULT NULL,
  update_user varchar(255) DEFAULT NULL,
  remark varchar(255) DEFAULT NULL,
   PRIMARY KEY (id)
);

