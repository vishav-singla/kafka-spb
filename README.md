# Kafka Application

## Table of Contents
- [Introduction](#introduction)
- [Overview](#overview)
- [Setup](#setup)
- [How to Run](#how-to-run)
- [Components](#components)
    - [KafkaConfig](#kafkaconfig)
    - [MessageConsumer](#messageconsumer)
    - [KafkaController](#kafkacontroller)
    - [MessageProducer](#messageproducer)
    - [User Model](#user-model)
- [Testing](#testing)

## Introduction

Apache Kafka is a distributed event streaming platform capable of handling trillions of events a day. Initially conceived as a messaging queue, Kafka is based on an abstraction of a distributed commit log. It provides low-latency, high-throughput, fault-tolerant publish and subscribe pipelines and is able to process streams of events.

Key concepts in Kafka include:
- **Topics**: Categories or feed names to which records are published.
- **Partitions**: Divisions of topics for scalability.
- **Producers**: Clients that publish messages to Kafka topics.
- **Consumers**: Clients that subscribe to topics and process the published messages.
- **Consumer Groups**: Groups of consumers that work together to consume messages from topics.

## Overview

This application demonstrates a Spring Boot implementation of Kafka with multiple topics and consumer groups. It showcases:
- Configuration of multiple Kafka topics
- Production of messages to specific topics
- Consumption of messages with different consumer groups
- Integration of Kafka with Spring Boot using Spring Kafka

The application uses three topics (`kafka-demo-1`, `kafka-demo-2`, `kafka-demo-3`) and three consumer groups to illustrate how different groups can subscribe to different sets of topics, allowing for flexible message routing and processing.

## Setup

This project requires setting up Kafka and Zookeeper using Docker.

## How to Run

1. Ensure Docker and Docker Compose are installed on your system.
2. Navigate to the project directory containing the `docker-compose.yml` file.
3. Run the following command to start Kafka and Zookeeper:
    ```sh
    docker-compose up -d
    ```
4. Once the containers are up, you can start your Spring Boot application.

## Components

### KafkaConfig

- **Description**: Configures Kafka topics, producer factory, and Kafka template. It sets up three topics with different partition counts.
- **Topics**:
    - `kafka-demo-1` with 3 partitions
    - `kafka-demo-2` with 2 partitions
    - `kafka-demo-3` with 4 partitions

### MessageConsumer

- **Description**: Contains Kafka listeners for different consumer groups. Demonstrates how different groups can subscribe to different sets of topics.
- **Consumer Groups**:
    - `group-1`: Subscribes to `kafka-demo-1`, `kafka-demo-2`, `kafka-demo-3`
    - `group-2`: Subscribes to `kafka-demo-1`, `kafka-demo-2`
    - `group-3`: Subscribes to `kafka-demo-3`

### KafkaController

- **Description**: Provides REST endpoints for sending messages to different Kafka topics. Acts as the entry point for producing messages.
- **Endpoints**:
    - `POST /api/kafka/send/topic1`: Sends messages to `kafka-demo-1`
    - `POST /api/kafka/send/topic2`: Sends messages to `kafka-demo-2`
    - `POST /api/kafka/send/topic3`: Sends messages to `kafka-demo-3`

### MessageProducer

- **Description**: Handles the actual sending of messages to Kafka topics using `KafkaTemplate`.
- **Methods**:
    - `sendMessage(String topic, User user)`: Sends a `User` message to the specified topic.

### User Model

- **Description**: Represents the structure of messages being sent and received.
- **Fields**:
    - `name` (String): The name of the user.
    - `age` (int): The age of the user.

## Testing

To test the application, you can use cURL commands to send POST requests to the endpoints defined in `KafkaController`. These requests will produce messages to different Kafka topics, which will then be consumed by the appropriate consumer groups as defined in `MessageConsumer`.

Example cURL commands for testing:

```sh
# Send to topic1 (kafka-demo-1)
curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"John Doe\",\"age\":30}" http://localhost:8080/api/kafka/send/topic1

# Send to topic2 (kafka-demo-2)
curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"Jane Doe\",\"age\":25}" http://localhost:8080/api/kafka/send/topic2

# Send to topic3 (kafka-demo-3)
curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"Bob Smith\",\"age\":40}" http://localhost:8080/api/kafka/send/topic3
