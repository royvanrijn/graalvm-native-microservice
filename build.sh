mvn clean install

docker build \
   --build-arg GRAAL_ARGUMENTS="--no-server --verbose -cp target/*:target/lib/* \
      com.royvanrijn.graal.Main \
      -H:IncludeResources=.*.properties|.*META-INF/persistence.xml|.*.xsd \
      -H:+ReportUnsupportedElementsAtRuntime \
      -H:IncludeResourceBundles=com.sun.org.apache.xerces.internal.impl.xpath.regex.message \
      -H:ReflectionConfigurationFiles=src/main/resources/reflection_config.json \
      -H:JNIConfigurationFiles=src/main/resources/jni_config.json \
      -H:+AllowVMInspection \
      -H:EnableURLProtocols=http \
      --delay-class-initialization-to-runtime=oracle.jdbc.driver.OracleDriver,java.sql.DriverManager,oracle.jdbc.driver.OracleTimeoutThreadPerVM,oracle.sql.LnxLibServer,oracle.sql.LoadCorejava
      -H:Name=app" \
   -t royvanrijn/example-native-image:latest .
