# MEMSQL

## Creamos el contenedor

```powershell

docker network create --driver bridge --subnet 10.0.0.0/28 smallnet

docker run -i --init --name memsql-ciab --net littlenet --ip 10.0.0.9 -e LICENSE_KEY="BGEwMzY0ZmY4NTZjMTQ3NDlhYWM0ZTEzNDJmNTBmOGVhAAAAAAAAAAAEAAAAAAAAAAwwNQIZAI8rJuPNrCezTuczCU4w+27CWUByR58ysgIYTxl4qrgflFhcFesGFdF6lBZLaVhet25EAA==" -e ROOT_PASSWORD="jlL()QUEBP@sGgTNEETj" -p 3306:3306 -p 8080:8080 memsql/cluster-in-a-box


docker start memsql-ciab


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
    len INT
);

insert into lyrics (id,artist_name, trackname, release_date, genre, lyrics, len) values (1,'pepe', 'la cancion de pepe', 2004, 'electro cumbia', 'hola soy pepe, hola soy pepe, hola soy pepe, adios pepe', 54);

select * from lyrics;

CREATE PIPELINE kafka_consumer AS
    LOAD DATA KAFKA "hadoopserver:9092/proyecto"
    SKIP ALL ERRORS
    INTO TABLE lyrics
    FIELDS TERMINATED BY ',' 
    OPTIONALLY ENCLOSED BY '"'
    IGNORE 1 LINES
    (id, artist_name, trackname, release_date, genre, lyrics, len);



TEST PIPELINE kafka_consumer LIMIT 1;

START PIPELINE kafka_consumer FOREGROUND LIMIT 1 BATCHES; --para probar y recibir solo un bache y probar

select * from lyrics where release_date = 2006


START PIPELINE kafka_consumer;

SELECT * FROM information_schema.PIPELINES_BATCHES_SUMMARY; //ver informacion de batches```