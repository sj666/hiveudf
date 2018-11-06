# Apache Hive UDF Repository

A list of user defined functions are collected here for Hive and Spark SQL.

Note:
* UDAF class is deprecated will move to generic interface.


## For Development:

1. Build the JAR:
    ```
    mvn package
    ```

2. Start Hive CLI with the JAR in your classpath:
    ```
    export HADOOP_CLASSPATH=target/df-hiveudf-1.0-SNAPSHOT.jar ; hive
    ```

3. Execute:
    ```
    create temporary function str_lower as 'com.datafibers.hiveudf.udf.StringLower';
    ```

4. Execute:
    ```
    select str_lower("UPPER CASE LETTERS");
    ```

## For Deployment:

1. In shell commandline, create a permanent function:
    ```c
    hdfs dfs -mkdir /apps/hive/functions
    hdfs dfs -chown hive:hive /apps/hive/functions
    cp df-hiveudf-1.0-SNAPSHOT.jar /tmp
    hdfs dfs -put -f /tmp/df-hiveudf-1.0-SNAPSHOT.jar /apps/hive/functions
    ```
2. In beeline, create/register/check the function
    ```c
    DROP FUNCTION IF EXISTS str_lower;
    DROP FUNCTION IF EXISTS arraycontains;
    CREATE FUNCTION str_lower AS 'com.datafibers.hiveudf.udf.StringLower' USING JAR 'hdfs:////apps/hive/functions/df-hiveudf-1.0-SNAPSHOT.jar';
    CREATE FUNCTION arraycontains AS 'com.datafibers.hiveudf.gudf.ArrayContains' USING JAR 'hdfs:////apps/hive/functions/df-hiveudf-1.0-SNAPSHOT.jar';
    DESC FUNCTION arraycontains;
    DESC FUNCTION EXTENDED arraycontains;
    ```

3. After that, you can use the functions like hive build-in functions.
    ```c
    select str_lower(name), work_place from employee where arraycontains(work_place, 'Toronto');
    ```

4. Check query explain
    ```c
    explain select str_lower(name), work_place from employee where arraycontains(work_place, 'Toronto');
    ```

## For Streaming Function
For streaming function by python, follow steps below.
```
hdfs dfs -mkdir /apps/hive/scripts
hdfs dfs -put hive_udf_addfile.py /apps/hive/scripts
hive -f hive_udf_addfile.sql
```

# hiveudf
# hiveudf
# hiveudf
