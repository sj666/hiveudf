drop table if exists py_udf_test;

create table if not exists py_udf_test (
id int, col1 int, col2 int, col3 int)
row format delimited fields terminated by ','
tblproperties("skip.header.line.count"="1");

load data local inpath "hive_udf_addfile.csv" into table py_udf_test;
select * from py_udf_test limit 5;

add file /apps/hive/scripts/hive_udf_addfile.py;

select
transform (id, col1, col2, col3)
using 'python hive_udf_addfile.py'
as (out1 STRING, out2 STRING, out3 STRING)
from py_udf_test;