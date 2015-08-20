#!/bin/bash -e

mvn clean install
cd target/github-publish/examples
mvn
begin='src/main/java/com/basistech/rosette/examples/'
end='.java'
for f in src/main/java/com/basistech/rosette/examples/*.java
do
    f=${f#$begin}
    f=${f%$end}
    # ExampleBase is not an example, but all others should be
    if [ $f != "ExampleBase" ]
    then
        mvn exec:java -Dexec.mainClass="com.basistech.rosette.examples.$f" -Drosette.api.key="7eb3562318e5242b5a89ad80011f1e22"
    fi
done
