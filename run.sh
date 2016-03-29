mvn clean package
java -javaagent:./agent/target/agent-1.0.0-SNAPSHOT-jar-with-dependencies.jar -jar app/target/app-1.0.0-SNAPSHOT-exec.jar

