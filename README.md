# Mandarin
A library management system.

## Environment Requirement
| Item | Requirement |
|:-:|:-:|
| Memory | 512 MB+ |
| OS | Windows 7+ / Linux 3.10.0+ |

## Deployment Guide
We `embedded` Apache Tomcat 7 in Mandarin, and `generate` database schema automatically.  
So the deployment is easy.

### On Linux
As a demonstration, we chose two representative popular Linux distributions Ubuntu and Archlinux, while the other distributions can follow the following deployment process.  
Also, if you have deployment experience, you can choose to deploy `MySQL` or the entire Mandarin project using a virtual environment such as `docker`.  

1. Install Java Runtime Environment 1.8
```bash
# Ubuntu
apt install openjdk-8-jre
# Archlinux
pacman -S jre8-openjdk
```

2. Install MySQL Server 8.0+ and MySQL Client

MySQL Server
```bash
# Ubuntu
apt install mysql-server
# Archlinux
pacman -S mysql
```

MySQL Client (Optional)
```bash
# ubuntu 
apt install mycli
# Archlinux
pacman -S mycli
```

3. Create Mandarin database on MySQL
```bash
mycli -u root
> create database mandarin;
```

4. Deploy Mandarin
Copy `mandarin-runner.jar` to any path you want.  
E.g. you can put `mandarin-runner.jar` to `/home/root/`.  

5. Write Mandarin configuration
you should put this file as a default configuration file on the same path as `mandarin-runner.jar`.  
```bash
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/mandarin
spring.datasource.username=root
spring.datasource.password=YOUR_ROOT_PASSWORD

spring.jpa.hibernate.ddl-auto=create
logging.level.org.a3.mandarin=debug

spring.datasource.data=classpath:mandarin-data.sql
spring.datasource.initialization-mode=always

spring.main.allow-bean-definition-overriding=true
spring.jpa.show-sql=true

spring.mail.host=YOUR_EMAIL_HOST
spring.mail.username=YOUR_EMAIL_USERNAME
spring.mail.password=YOUR_EMAIL_PASSWORD
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtps.required=true.starttl

server.port=80
```

Now your directory should be like this:
```bash
.
├── application.properties
└── mandarin-runner.jar

0 directories, 2 files
```


6. (Optional) Import other data into Mandarin database
You can change your default configuration file:
```bash
spring.datasource.data=file:YOUR_SQL_FILE_PATH
```
Or, use a MySQL Client:
```bash
$ mycli -u root
> use mandarin;
> source ./other.sql;
```

7. Run Mandarin
```bash
java -jar mandarin-runner.jar
```
visit `http://HOST:PORT/` to enter reader index page.
And `http://HOST:PORT/admin/`is the administrator's entry

