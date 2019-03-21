drop table template if exists;

create table template (
  id int,
  content varchar,
  createTime timestamp
);

insert into template (id, content,createTime) values ('1','{ "title": "标题", "content": "内容"}','2018-08-19 00:00:00');
insert into template (id, content,createTime) values ('2','{ "title": "标题", "content": "内容"}','2018-08-20 00:00:00');
insert into template (id, content,createTime) values ('3','{ "title": "标题", "content": "内容"}','2018-08-21 00:00:00');
