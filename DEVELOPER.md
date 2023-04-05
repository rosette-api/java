## Developer Notes

#### Deprecation Strategies
If we remove a field via deprecation, the field can still be passed in, but we will no longer deserialize it.  As an example see [PR 204](https://github.com/rosette-api/java/pull/204).

#### Building and Releasing
To be updated..


#### Internal Releasing

To perform an internal release, execute the following commands:
```
$ mvn release:clean
$ mvn release:prepare
$ mvn release:perform -Drelease-profile=internal-release
```

