<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kafka Integration with Spring Boot</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1 { color: #333; }
        h2 { color: #555; }
        code { background: #f4f4f4; padding: 2px 4px; border-radius: 4px; }
        pre { background: #f4f4f4; padding: 10px; border-radius: 4px; overflow-x: auto; }
    </style>
</head>
<body>

<h1>Kafka Integration with Spring Boot</h1>

<p>This project demonstrates how to integrate Apache Kafka with a Spring Boot application, enabling the sending and receiving of messages through Kafka topics.</p>

<h2>Features</h2>
<ul>
    <li><strong>Kafka Producer</strong>: Sends messages to a specified Kafka topic.</li>
    <li><strong>Kafka Consumer</strong>: Listens for messages from a Kafka topic.</li>
    <li><strong>Dynamic Topic Creation</strong>: Allows creating Kafka topics with specified partitions and replication factors.</li>
</ul>

<h2>Prerequisites</h2>
<ul>
    <li>Java 11 or later</li>
    <li>Apache Kafka (running locally or accessible via specified bootstrap servers)</li>
    <li>Spring Boot 3.x</li>
</ul>

<h2>Getting Started</h2>

<h3>1. Clone the Repository</h3>
<pre><code>git clone &lt;repository-url&gt;
cd kafkapp</code></pre>

<h3>2. Setup Kafka</h3>
<p>Ensure that you have Kafka and Zookeeper running. You can use Docker to set them up:</p>
<pre><code>version: '2'

services:
  zookeeper:
    image: wurstmeister/zookeeper:latest
    ports:
      - "2181:2181"

  kafka:
    image: wurstmeister/kafka:latest
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_LISTENERS: INSIDE://kafka:9093,OUTSIDE://localhost:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: INSIDE:PLAINTEXT,OUTSIDE:PLAINTEXT
      KAFKA_LISTENERS: INSIDE://0.0.0.0:9093,OUTSIDE://0.0.0.0:9092
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181</code></pre>

<h3>3. Configuration</h3>
<p>Update <code>src/main/resources/application.yaml</code> with the following configurations:</p>
<pre><code>spring:
  application:
    name: kafkapp
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: my-group-id</code></pre>

<h3>4. Build and Run the Application</h3>
<pre><code>mvn spring-boot:run</code></pre>

<h3>5. Sending Messages</h3>
<p>To send messages to the Kafka topic, use the following HTTP POST request:</p>
<pre><code>POST http://localhost:8080/api/kafka/send
Content-Type: application/json

{
    "name": "John Doe",
    "email": "john.doe@example.com"
}</code></pre>

<h3>6. Creating Topics</h3>
<p>You can create a topic by sending a POST request to <code>/create-topic</code> endpoint:</p>
<pre><code>POST http://localhost:8080/create-topic?name=my-topic&partitions=3</code></pre>

<h3>7. Consuming Messages</h3>
<p>The application will automatically consume messages from the specified topic, and you can observe the logs to see the consumed messages.</p>

<h2>Note</h2>
<ul>
    <li>Ensure that the Kafka server is up and running before starting the Spring Boot application.</li>
    <li>Adjust the topic name and partition count as per your requirements.</li>
</ul>

<h2>License</h2>
<p>This project is licensed under the MIT License.</p>

</body>
</html>
