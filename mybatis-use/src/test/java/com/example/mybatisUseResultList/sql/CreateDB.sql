drop table student if exists;

drop table subject if exists;

drop table class_mate if exists;

create table student (
  id int,
  name varchar(20),
  class_mate_id int
);


create table subject (
  id int,
  student_id int,
  name varchar(20),
  score int
);

create table class_mate(
  id int,
  name varchar(20)
);

insert into student (id,name,class_mate_id) values ('1','张三','1');

insert into subject (id,student_id,name,score) values ('1','1','语文','80');

insert into class_mate (id,name) values ('1','三年一班');