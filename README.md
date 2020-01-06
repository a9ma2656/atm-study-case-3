# ATM Study Case 3

## Requirements
See details requirement in [this wiki](https://github.com/Mitrais/java-bootcamp-working/wiki)

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
### Prerequisites
- [Java 8](https://adoptopenjdk.net/?variant=openjdk8&jvmVariant=hotspot)
- [MySQL 5.7+](https://dev.mysql.com/downloads/mysql/)
- [Maven 3.6+](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)
- Your favorite IDEA ([InteliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows), [Eclipse](https://www.eclipse.org/downloads/), [Netbeans](https://netbeans.apache.org/download/index.html), etc)

### Installing
1. Clone the application
   ~~~shell script
   $ git clone -b master git@github.com:a9ma2656/atm-study-case-3.git
   ~~~
2. Create database
   
   For application:
   ~~~shell script
   $ create database atm
   ~~~
   
   For test:
   ~~~shell script
   $ create database atm-test
   ~~~
   
3. Build and run the app using maven

   **Build Command**
   ~~~shell script
   $ mvn clean spring-boot:run ^
   -Dspring-boot.run.arguments="--atm.database.username=root,--atm.database.password=password"
   ~~~
   
   NOTE: Change database username and password as per your DB installation
   
    --`atm.database.username`
    
    --`atm.database.password`

   The app will start running at `http://localhost:8080`
   
   **Build Parameters**
   
   | Parameter | Description | Default Value |  Notes |
   | --- | --- | --- | --- |
   | atm.database.username | The database username | root | |
   | atm.database.password | The database password | password | |
   | atm.database.server | The database server | 127.0.0.1 | |
   | atm.database.port | The database port | 3306 | |
   | atm.database.dbname | The database name | atm | `test` profile will set the database name to `atm-test` |
   | atm.database.liquibase.schema | The database schema definition settings | db.changelog-master.xml | |

4. Health Check
   ~~~shell script
   $ curl -X GET localhost:8080/actuator/health
   ~~~

### Running Tests
Explain how to run the automated tests for this system

Run all tests:

1. Right click on `java` test package
2. Run `All Tests`

Run single test:
1. Right click on a test e.g. `WithdrawIntegrationTest.java`
2. Run `WithdrawIntegrationTest`

### Deployment
TBA