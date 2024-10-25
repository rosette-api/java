## Endpoint Examples
Each example file demonstrates one of the capabilities of the Analytics Platform.

Here are some methods for running the examples. Each example will also accept an optional `-Drosette.api.altUrl`
parameter for overriding the default URL.

A note on prerequisites. Analytics API only supports TLS 1.2 so ensure your toolchain also supports it.

#### Running from Local Source

```
git clone git@github.com:rosette-api/java.git
cd java
mvn install
cd examples
mvn exec:java -Dexec.mainClass="com.basistech.rosette.examples.PingExample" -Drosette.api.key=$API_KEY
```
