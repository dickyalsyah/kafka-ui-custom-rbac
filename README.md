#### UI for Apache Kafka is a free, open-source web UI to monitor and manage Apache Kafka clusters.

UI for Apache Kafka is a simple tool that makes your data flows observable, helps find and troubleshoot issues faster and deliver optimal performance. Its lightweight dashboard makes it easy to track key metrics of your Kafka clusters - Brokers, Topics, Partitions, Production, and Consumption.

# Getting Started

To run UI for Apache Kafka, you can use either a pre-built Docker image or build it (or a jar file) yourself.

## Quick start (Demo run)

```
docker run -it -p 8080:8080 -e DYNAMIC_CONFIG_ENABLED=true provectuslabs/kafka-ui
```

Then access the web UI at [http://localhost:8080](http://localhost:8080)

The command is sufficient to try things out. When you're done trying things out, you can proceed with a [persistent installation](https://docs.kafka-ui.provectus.io/quick-start/persistent-start)

## Persistent installation

```
services:
  kafka-ui:
    container_name: kafka-ui
    image: provectuslabs/kafka-ui:latest
    ports:
      - 8080:8080
    environment:
      DYNAMIC_CONFIG_ENABLED: 'true'
    volumes:
      - ~/kui/config.yml:/etc/kafkaui/dynamic_config.yaml
```

Please refer to our [configuration](https://docs.kafka-ui.provectus.io/configuration/quick-start) page to proceed with further app configuration.

## Some useful configuration related links

[Web UI Cluster Configuration Wizard](https://docs.kafka-ui.provectus.io/configuration/configuration-wizard)

[Configuration file explanation](https://docs.kafka-ui.provectus.io/configuration/configuration-file)

[Docker Compose examples](https://docs.kafka-ui.provectus.io/configuration/compose-examples)

[Misc configuration properties](https://docs.kafka-ui.provectus.io/configuration/misc-configuration-properties)

## Helm charts

[Quick start](https://docs.kafka-ui.provectus.io/configuration/helm-charts/quick-start)

## Building from sources

[Quick start](https://docs.kafka-ui.provectus.io/development/building/prerequisites) with building

## Liveliness and readiness probes
Liveliness and readiness endpoint is at `/actuator/health`.<br/>
Info endpoint (build info) is located at `/actuator/info`.

# Configuration options

All of the environment variables/config properties could be found [here](https://docs.kafka-ui.provectus.io/configuration/misc-configuration-properties).

# Contributing

Please refer to [contributing guide](https://docs.kafka-ui.provectus.io/development/contributing), we'll guide you from there.
