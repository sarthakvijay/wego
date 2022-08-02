# Getting Started

## Reference Documentation

#### @GetMapping("/carparks/location")
    for Getting the Location (x_coord and y_coord) date for particular parking.
    ** Location info - Location table.

#### @GetMapping("/carparks/get")
    for getting the availabilty info for partcular car parking info.
    ** Availability - Info table.

#### @GetMapping("/carparks/nearest")
    for searching the nearest parkings and slots availabilty.s

##### In main class using @PostContruct I can load all the location and availability data.
##### So directly the nearest calls can be made.


## if need to use any exteranl independent database 
## Configure Spring Datasource, JPA, App properties
Open `src/main/resources/application.properties`
- For PostgreSQL:
```
spring.datasource.url= jdbc:postgresql://localhost:5432/testdb
spring.datasource.username= postgres
spring.datasource.password= 123

```
- For MySQL
```
spring.datasource.url= jdbc:mysql://localhost:3306/testdb?useSSL=false
spring.datasource.username= mysql
spring.datasource.password= 123
```

# Complete Process 

#### Step1 - Delete file persisting data (testdb.mv.db)
#### Step2 - start process, from main class or using command line
#### Step3 - launch api, (http://localhost:8080/carparks/location)
#### Step4 - launch api, (http://localhost:8080/carparks/availability)
#### Step5 - Now you can search for nearest carparking locations using the API (http://localhost:8080/carparks/nearest?latitude=1.39326&longitude=103.946&page=1&per_page=3)

##### Junit test cases are not complete as a crunch of time.
