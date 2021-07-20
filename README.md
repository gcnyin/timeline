# Timeline

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

A web application.

- scala
- playframework
- slick
- flyway
- postgresql
- bootstrap

## Build

### Docker

```
sbt playUpdateSecret docker:publishLocal
```

## Run

```
docker-compose up -d
```

Service is listening on port `9000`.

## Screenshots

![001.jpeg](./docs/screenshots/001.jpeg)
