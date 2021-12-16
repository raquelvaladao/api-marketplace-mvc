## Projeto API - JAVA - [Compra/venda de produtos]

###  ENDPOINT BASE:
```
https://api-estudo-mvc.herokuapp.com/v1/
```

###  Docs - swagger2
+ https://api-estudo-mvc.herokuapp.com/swagger-ui.html

###  CIRCUNSTÂNCIAS
#### + Security (JWT)
#### + Deploy Heroku
#### + Mapstruct


###  POSTMAN JSON PARA TESTES
  + [Ver arquivo neste repositório](API Estudo.postman_collection.json)
###  DADOS DE LOGIN:
+ Para o admin:
  + Email: **admin@gft.com**
  + Senha: **123456**
+ Para o scrum:
  + Email: **scrum@gft.com**
  + Senha: **123456**

###  POPULAÇÃO DO BANCO
+ Ocorre com Flyway, então o banco é populado ao iniciar a aplicação com alguns usuários, carteiras e produtos.

###  BANCO E IDE
+ H2 + Flyway p/ migração
+ Intellij Community
+ Java 11 -  module: Coretto 11
