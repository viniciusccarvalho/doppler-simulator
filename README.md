## Doppler simulator

Simulates events emitted by [cloudfoundry doppler](http://www.github.com/loggregator).

By default the application starts listening on port 9090, you can override any configuration using spring-boot configuration override: `--spring.config.file=file://`

## Configuration

The server has a default `application.yml`, if you need to override with your own values copy it to a separate folder and run it using the above command.

### How to configure

```

```

## Performance

The data generation part is very lightweight, on a modern i7 processor with only one thread running, it generates around 3 million events per second
when calling `DopplerSimulation.data()` directly.

On a single thread the full integration tests that serialize data from the websocket endpoint to the client runs on 30k events per second. Most of 
the overhead is due the protocol buffer toByteArray() method.
