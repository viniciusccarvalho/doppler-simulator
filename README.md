## Doppler simulator

Simulates events emitted by [cloudfoundry doppler](http://www.github.com/loggregator).

By default the application starts listening on port 9090, you can override any configuration using spring-boot configuration override: `--spring.config.location=file://`

## Building

The [dropsonde protocol](https://github.com/cloudfoundry/dropsonde-protocol) is changing quite a lot. This project works with a specific version, so make sure after fetching the submodule
you run `git checkout 841998ca027c0fd173cff1c752dfd46e5d14e557` from its folder.

`gradle build` (skip tests using *-x test* if you want to build it quicker, tests run some benchmarks and can slow things) 


## Configuration

The server has a default `application.yml`, if you need to override with your own values copy it to a separate folder and run it using the above command.

### How to configure

A sample application.yml should be enough to explain things:

```yaml
  server:
     port: 9090
 
  simulator:
 
    #default: 100
    events-per-second: 100000000
 
    #How many hosts you want simulate for each metric being generated. This will map to the Envelope index and ip (each resource gets an unique IP from the simulator)
    #Add as many resources as you would like to have as sources of metrics
    #Note that you need to have the resources declared in this section if you want to use them on metrics declaration bellow
    resources:
      - name: router
        size: 2
      - name: dea
        size: 8
      - name: nats
        size: 2
 
    # Yaml Polymorphic collections is not easy to grasp, this is why each metric is represented as a high level entity
    counterEvent:
     # List of resources you want to emit this type of metric.
     resources: ["nats"]
     #Weight of this metric compared to other metrics in this simulation
     weight: 2
     # A list of possible CounterEvent metrics that will be generated for each time simulation.data() is called
     metrics:
       - name: "messages.sent"
         min: 0
         max: 100
         weight: 1
 
 
    valueMetric:
     # List of resources from the resourcePool you want to generate metrics from
     resources: ["router","dea","nats"]
     weight: 3
     #List of all possible metrics that can be created for this type
     metrics:
       - name: cpu
         min: 0
         max: 100
         weight: 10
         unit: "percent"
       - name: memory
         min: 1048576
         max: 4294967296
         weight: 10
         unit: "B"
       - name: openfiles
         min: 1
         max: 1024
         weight: 1
         unit: "unit"
 
 
    containerMetric:
     resources: ["dea"]
     weight: 2
     applicationId:
       size: 5
     cpuPercentage:
       min: 0
       max: 100
     memoryBytes:
       min: 1048576
       max: 4294967296
     diskBytes:
       min: 1048576
       max: 4294967296
 
 
    logMessage:
     resources: ["router","dea"]
     weight: 1
     message: "2015-06-17 19:02:46.763  INFO 35181 --- [           main] ationConfigEmbeddedWebApplicationContext : Refreshing org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@73a1e9a9: startup date [Wed Jun 17 19:02:46 EDT 2015]; root of context hierarchy"
     messageType: ["OUT","ERR"]
     #UUID type, size means how many distinct values should be generated
     appId:
       size: 5
 
 
    httpStartStop:
      resources: ["router"]
      weight: 5
      applicationId:
        size: 10
      uri: ["/search","/create","/favicon"]
      #optional, if not provided, only 200's will be generated
      statusCode:
        - name: 200
          weight: 100
        - name: 201
          weight: 50
        - name: 404
          weight: 2
        - name: 503
          weight: 1
      #optional, if not provided, all methods will be generated with equal weights
      method:
        - name: GET
          weight: 100
        - name: POST
          weight: 50
        - name: DELETE
          weight: 10
        - name: PUT
          weight: 20
      contentLenght:
        min: 32
        max: 1024
      #optional, if not provided both types will be generated with equal weights
      peerType:
        - name: client
          weight: 100
        - name: server
          weight: 10
      #Response time in milliseconds to be set on HttpStop and HttpStartStop
      responseTime:
          min: 10
          max: 5000

```

## Testing

After running it, you can just get [noaa](https://github.com/cloudfoundry/noaa) and point to this server. 

```bash
export DOPPLER_ADDR=ws://localhost:9090 
```

After running it, you should see the messages flowing on the console.

## Performance

The data generation part is very lightweight, on a modern i7 processor with only one thread running, it generates around 3 million events per second
when calling `DopplerSimulation.data()` directly.

On a single thread the full integration tests that serialize data from the websocket endpoint to the client runs on 180k events per second. Most of 
the overhead is due the protocol buffer toByteArray() method.

Using the sample application.yml (unbounded events per second) the server can yield on a loopback interface client connected the following numbers:

| Events (sec) | Binary data size (sec) | Events (day) | Data (day) |
|--------------|------------------------|--------------|------------|
| 180k  | 20 megabytes           |  15.5 billion | 1.7 TB |

## Implemented Events 

- [x] ValueMetric
- [x] CounterEvent
- [x] HttpStart
- [x] HttpStop
- [x] HttpStartStop
- [x] ContainerMetric
- [x] LogMessage
