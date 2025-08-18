#!/bin/bash

echo "Waiting for Kafka to be ready..."
sleep 30

echo "Creating Kafka topics..."

# Create transaction-events topic
kafka-topics --create \
  --bootstrap-server localhost:9092 \
  --replication-factor 1 \
  --partitions 3 \
  --topic transaction-events

# Create fraud-alerts topic
kafka-topics --create \
  --bootstrap-server localhost:9092 \
  --replication-factor 1 \
  --partitions 3 \
  --topic fraud-alerts

echo "Topics created successfully!"

# List topics to verify
kafka-topics --list --bootstrap-server localhost:9092
