# MEMSQL

## Creamos el contenedor

```powershell

docker network create --driver bridge --subnet 10.0.0.0/28 smallnet

docker run -i --init --name memsql-ciab --net littlenet --ip 10.0.0.9 -e LICENSE_KEY="BGEwMzY0ZmY4NTZjMTQ3NDlhYWM0ZTEzNDJmNTBmOGVhAAAAAAAAAAAEAAAAAAAAAAwwNQIZAI8rJuPNrCezTuczCU4w+27CWUByR58ysgIYTxl4qrgflFhcFesGFdF6lBZLaVhet25EAA==" -e ROOT_PASSWORD="jlL()QUEBP@sGgTNEETj" -p 3306:3306 -p 8080:8080 memsql/cluster-in-a-box


docker start memsql-ciab

docker exec -it memsql-ciab memsql -p"jlL()QUEBP@sGgTNEETj"


```

## CREAMOS EL PIPELINE

```sql

CREATE DATABASE proyecto;

USE proyecto;

CREATE TABLE lyrics (
    id INT,
    artist_name VARCHAR(50),
    trackname VARCHAR(50),
    release_date INT,
    genre VARCHAR(24),
    lyrics VARCHAR(1000),
    len INT,
    points FLOAT,
    theme VARCHAR(50)
);


CREATE PIPELINE kafka_consumer AS
    LOAD DATA KAFKA "hadoopserver:9092/memsql"
    SKIP ALL ERRORS
    INTO TABLE lyrics
    FIELDS TERMINATED BY ',' 
    OPTIONALLY ENCLOSED BY '"'
    IGNORE 1 LINES
    (id, artist_name, trackname, release_date, genre, lyrics, len, points, theme);




TEST PIPELINE kafka_consumer LIMIT 10;


select count(id) from lyrics

delete from lyrics


START PIPELINE kafka_consumer;


SELECT * FROM information_schema.PIPELINES_BATCHES_SUMMARY; //ver informacion de batches


```