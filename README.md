# Ktor-WhatToEat

需自行建立`application.conf`配置文件，檔案路徑為`/src/main/resources/application.conf`，隱私因素不便附上。
```
ktor {
    deployment {
        port = 8080
        port = ${?PORT}
    }
    application {
        modules = [ me.lazy_assedninja.application.ApplicationKt.module ]
    }
    database {
        user = "lazy-assedninja"
        password = "000000"
    }
}
```