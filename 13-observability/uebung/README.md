# tle-fetcher Project

The TLE fetcher retrieves TLE (two-line element set) data for calculating satellites trajectories from a NASA related API.
You can learn more information on the TLE format [here](https://en.wikipedia.org/wiki/Two-line_element_set), on orbital
mechanics [here](https://en.wikipedia.org/wiki/Orbital_mechanics) and while playing some rounds of Kerbal Space Program.

This project uses Quarkus. If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Prerequisites

To build and run this application, you will need the following dependencies on your system:

| Name           | Version |
|----------------|---------|
| Docker         | *       |
| Docker-Compose | 1.13.0+ |
| Java           | 11      |


## Java services

### Building the application

You can build the application using Gradle:

```shell
$ ./gradlew build
```

This will build the Java application and a docker image. The docker image will be registered as `qaware/tle-fetcher:1.0.0` within your docker daemon:

```shell
$ docker images

REPOSITORY           TAG     IMAGE ID       CREATED         SIZE
qaware/tle-fetcher   1.0.0   55bd6d637c77   7 seconds ago   371MB
```

### Configuration

All relevant configuration can be found in each service `src/main/resources/application.properties`.

## The Grafana stack

The Grafana stack is configured in the directories `grafana`, `loki`, `promtail`, `prometheus` and `tempo`.

**NOTE**: It is important to change the permissions on the configuration files for the Grafana stack. Run the following command:

```shell
$ chmod -R o+rX grafana loki prometheus promtail tempo
```

### Grafana

Grafana is the visualization engine of the Grafana stack.

The main configuration is done in `grafana.ini`.

You can provision several other things automatically, like dashboards and datasources. All of this is stored in `grafana/provisioning` and deployed automatically on startup.

### Loki

Loki is the log storage engine of the Grafana stack. In this repository, there is only the `loki/loki.yaml` with basic storage configuration.

### Promtail

Promtail is the logshipper of the Grafana stack. It periodically scrapes logfiles and sends them to Loki.

The configuration `promtail/promtail.yaml` scrapes the application logs from the services and pre-parses their JSON.

### Prometheus

Prometheus is a time series storage that can be connected to Grafana. It periodically scrapes metrics from known endpoints.

The configuration file `prometheus/prometheus-yml` scrapes metrics from Prometheus itself and the Quarkus services.

### Tempo

Tempo stores APM and tracing data from services. It can also be connected to Grafana.

## Running the services

Run the applications with `docker-compose`:

```shell
$ docker-compose up
```

## Related Guides

- SmallRye OpenTracing ([guide](https://quarkus.io/guides/opentracing)): Trace your services with SmallRye OpenTracing
- Micrometer Registry Prometheus ([guide](https://quarkus.io/guides/micrometer)): Enable Prometheus support for Micrometer
- REST Client Classic ([guide](https://quarkus.io/guides/rest-client)): Call REST services
- RESTEasy Classic JSON-B ([guide](https://quarkus.io/guides/rest-json)): JSON-B serialization support for RESTEasy Classic
- RESTEasy Classic ([guide](https://quarkus.io/guides/resteasy)): REST endpoint framework implementing JAX-RS and more
- Logging JSON ([guide](https://quarkus.io/guides/logging#json-logging)): Add JSON formatter for console logging
- SmallRye Health ([guide](https://quarkus.io/guides/microprofile-health)): Monitor service health
- Micrometer metrics ([guide](https://quarkus.io/guides/micrometer)): Instrument the runtime and your application with dimensional metrics using Micrometer.
