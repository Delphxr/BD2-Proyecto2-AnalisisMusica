# Docker related | KAFKA

### Creamos el Contenedor de Kafka
- En la maquina de Sebastian

```powershell

docker-compose up -d


//Server
docker-compose exec kafka kafka-topics --create --topic newsales --partitions 1 --replication-factor 1 --bootstrap-server 25.7.237.232:29093

//Producer
docker-compose exec kafka bash
kafka-console-producer --topic newsales --bootstrap-server 25.7.237.232:29093

//Consumer
kafka-console-consumer --topic newsales --from-beginning  --bootstrap-server 25.7.237.232:29093

```


# Docker related | SPARK

### Creamos el Contenedor de Spark
- En la maquina de Oswaldo

```powershell

docker build . -t pyspark

docker run -it --rm -v C:\dev\tec\hadoopbases2\pyspark\examples:/src pyspark bash

docker run -it --name spark --rm -p 7077:7077 -v W:\Bodega\KAFKA-SPARK\cursostec\hadoopbases2\pyspark\examples:/src --net littlenet --ip 10.0.0.3 pyspark bash

```