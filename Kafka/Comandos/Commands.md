# Docker related | KAFKA

### Creamos el Contenedor de Kafka
- En la maquina de Sebastian

```powershell

docker build . -t hadoop

docker network create --driver bridge --subnet 10.0.0.0/28 littlenet

docker run -it -p 9000:9000 -p 9092:9092 -p 22:22 -v W:\Bodega\KAFKA-SPARK\cursostec\hadoopbases2\mapr:/home/hadoopuser/mapr --name hadoopserver --net littlenet --ip 10.0.0.2 hadoop

```


# Docker related | SPARK

### Creamos el Contenedor de Spark
- En la maquina de Oswaldo

```powershell

docker build . -t pyspark

docker run -it --rm -v C:\dev\tec\hadoopbases2\pyspark\examples:/src pyspark bash

docker run -it --name spark --rm -p 7077:7077 -v W:\Bodega\KAFKA-SPARK\cursostec\hadoopbases2\pyspark\examples:/src --net littlenet --ip 10.0.0.3 pyspark bash

```