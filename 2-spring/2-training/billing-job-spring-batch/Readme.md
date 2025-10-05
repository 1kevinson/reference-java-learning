### Commands

Build the project:
```bash
  ./mvnw package -Dmaven.test.skip=true
```

Launch the job by passing it the input file as a parameter:
```bash
  java -jar target/billing-job-0.0.1-SNAPSHOT.jar input.file=src/main/resources/billing-2023-02.csv
```