# Messages server

This service will distinguish database users from others and allow
 users to record messages.  Users can also view 10 last messages.

## Endpoints

* `/auth/login` send here POST wis JSON  `{"user": "admin", "password": "password"}` to login and get token. 
Answer be `{"token": "SOM TOKEN"}`.
* `/messages` send here POST wis HEADER `Authorization: Raerer_SOME TOKEN` and
JSON `{"name": "admin", "message": "som message"}` to write this message or JSON 
`{"name": "admin", "message": "history 10"}` to get last 10 messages.


## You can try in on demo server
* To login type
`curl -X POST http://185.164.172.63:49001/auth/login -H 'Content-Type: application/json' 
-d '{"name":"admin","password":"password"}'
`

* Get token and put it here
`curl -X POST http://185.164.172.63:49001/messages -H 'Authorization: Bearer_TOKEN' -H 'Content-Type: application/json'
 -d '{"name":"admin","message":"test"}'
`  
* Put token her to see last 10 messages
`curl -X POST http://185.164.172.63:49001/messages -H 'Authorization: Bearer_TOKEN' -H 'Content-Type: application/json'
 -d '{"name":"admin","message":"history 10"}'
`  


## You can start it 
### Docker-compose
1. Clone this repository `git clone https://github.com/QmBo/message-server.git`  
2. Get in directory `cd ./message-server`
3. Run `docker-compose up`

##### Server started at port `49001`

### One mor way
0. Create a database in Postgres `create database messageserver`
1. Clone this repository `git clone https://github.com/QmBo/message-server.git`  
2. Get in directory `cd ./message-server`
3. Run `mvnw spring-boot:run`

##### Server started at port `80`

## links

[Docker HUB](https://hub.docker.com/repository/docker/qmbo/message-server)