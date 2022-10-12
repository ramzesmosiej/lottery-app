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
