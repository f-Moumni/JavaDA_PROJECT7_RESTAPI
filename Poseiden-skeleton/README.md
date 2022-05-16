#![poseidon](img/logo.png)

_Poseidon_ is a web application that helps institutions increase their number of transactions.

---------
## Technical:
1. Framework: Spring Boot v2.6.4
2. Java 11
3. Thymeleaf
4. Bootstrap v.4.3.1

---------

## Getting Started

### Running App

To run the app, go to folder `Poseiden`

1. To set up the tables and data in the data base.

   1. Create database with name **_poseiden_** as configuration in application.properties 
   2. run the sql script present in  `doc/data.sql`.

2. Compile and generate the final jar by running command: `mvn package`

3. Run the APP with: `mvn spring-boot:run -Dspring-boot.run.arguments="--spring.datasource.username=`*database username* `--spring.datasource.password=`*database password*`"

### Testing

To run the tests execute the command: `mvn verify`  
To generate the project's reports site, please run :`mvn site`
---------