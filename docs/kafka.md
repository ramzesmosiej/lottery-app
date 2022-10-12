# Using Kafka to improve event- driven architecture performance
### Learning Kafka was an important step when creating this project. Kafka is a distributed event streaming platform used for streaming events and asynchronous communication 
### The first step was to add Kafka and Zookeeper to docker-compose file
```
  zookeeper:
    image: bitnami/zookeeper:latest
    container_name: zookeeper
    networks:
      - spring
    ports:
      - "2181:2181"
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes
      - ZOOKEEPER_CLIENT_PORT=2181

  kafka:
    image: bitnami/kafka:latest
    container_name: kafka
    networks:
      - spring
    ports:
      - "9092:9092"
      - "29092:29092"
    environment:
      - KAFKA_BROKER_ID=1
      - KAFKA_LISTENERS=LOCAL_HOST://:9092, CLIENT://:29092
      - KAFKA_ADVERTISED_LISTENERS=LOCAL_HOST://localhost:9092, CLIENT://kafka:29092
      - KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=LOCAL_HOST:PLAINTEXT,CLIENT:PLAINTEXT
      - KAFKA_INTER_BROKER_LISTENER_NAME=LOCAL_HOST
      - KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181
      - ALLOW_PLAINTEXT_LISTENER=yes
    depends_on:
      - zookeeper
```
In my case both producer and consumer and also kafka broker run in docker network on my host machine
so kafka broker name is kafka (inherited from container name), therefore in advertised listener that should be included
Another listener(localhost) is for clients that will run locally and connect to kafka that runs in docker.
We create a new listener called LOCAL_HOST using port 29092 and the new advertised.listener is on localhost. Because it’s on a different port, we change the ports mapping (exposing 29092 instead of 9092).

The existing listener (PLAINTEXT) remains unchanged. We also need to specify KAFKA_LISTENER_SECURITY_PROTOCOL_MAP. This previously used a default value for the single listener, but now that we’ve added another, we need to configure it explicitly.

The next step is to configure lottery module which will produce messages and notification module which will consume messages and send email.
useful links: https://www.confluent.io/blog/kafka-client-cannot-connect-to-broker-on-aws-on-docker-etc/
https://rmoff.net/2018/08/02/kafka-listeners-explained/

