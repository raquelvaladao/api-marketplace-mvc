## Projeto API - JAVA - [Crypto Market]

### 👉 FEATURES
#### Docs - swagger2
+ http://localhost:8080/swagger-ui.html

#### Security
  + JWT + OAuth 2
#### Deploy Heroku


### 👉 POSTMAN JSON PARA TESTES
  + [Ver arquivo neste repositório](API_Estudo.postman_collection.json)
### 👉 DADOS DE LOGIN:
+ Para o admin:
  + Email: **admin@gft.com**
  + Senha: **123456**
+ Para o scrum:
  + Email: **scrum@gft.com**
  + Senha: **123456**

### 👉 POPULAÇÃO DO BANCO
+ Ocorre com Flyway, então o banco é populado ao iniciar a aplicação.
+ **OBS:** No HeidiSQL, bem como no application.properties, 
as config de conexão são
  + login : **root**
  + senha : vazia
  + porta : **3306**.

### 👉 BANCO E IDE
+ MySQL + Flyway p/ migração
+ Intellij Community
+ Java 11 -  module: Coretto 11
