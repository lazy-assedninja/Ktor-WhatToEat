# Ktor-WhatToEat
Refactor the university project.


## Environment
* Ubuntu 16.04.7 LTS
* MySQL Client 14.14
* MySQL Server 5.7.3-log
* JDK 1.8.0_302


## Configurations
`EngineMain` starts a server with the selected engine and loads the application module specified in the external `PROJECT_NAME/src/main/resources/application.conf` file. 
Besides modules to load, this file can include various server parameters. 
```apacheconf
ktor {
    development = true
    deployment {
        port = 8080
        port = ${?PORT}
    }
    application {
        modules = [ me.lazy_assedninja.application.ApplicationKt.module ]
    }
    database {
        url = "jdbc:mysql://localhost:3306/TABLE_NAME?useSSL=false"
        driver = "com.mysql.jdbc.Driver"
        user = "USER"
        password = "PASSWORD"
    }
    email {
        account = "EMAIL_ADDRESS"
        password = "PASSWORD"
    }
}
```


## Run
To run the project, execute the following command in repository's root directory:
```shell
./gradlew run
```