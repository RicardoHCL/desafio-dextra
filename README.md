# Desafio Dextra - API 

Api desenvolvida em Java 11 e com Spring Boot 2.4.5

## Iniciando a Api sem Docker

1. Importar o projeto no eclipse ou intelli j;
2. Configurar o arquivo `src/main/resources/application.properties` com as informações de conexão de um banco de dados `PostgreSQL`;
3. Executar a classe `Application.java`;

## Iniciando a Api com Docker

1. Entrar na raiz do projeto, onde está o arquivo `docker-compose.yml`;
2. Rodar o comando `docker-compose up -d --build` e esperar subir a aplicação;

## Usando a Api

`base url local: http://localhost:8080` </br>
`base url heroku: https://dextra-challenge.herokuapp.com`

1. Via postman:
    Exemplos de Requests:

    CRIAR PERSONAGEM:
```
    url: http://localhost:8080/api/characters  
    metodo: POST 
    body (Json):  
                  { 
                    "name": "Harry Potter", 
                    "role": "student", 
                    "school": "Hogwarts School of Witchcraft and Wizardry", 
                    "house": "1760529f-6d51-4cb1-bcb1-25087fce5bde", 
                    "patronus": "stag" 
                  } 

    Exemplo de Response: 

    { 
      "id": 1, 
      "name": "Harry Potter", 
      "role": "student", 
      "school": "Hogwarts School of Witchcraft and Wizardry", 
      "house": "1760529f-6d51-4cb1-bcb1-25087fce5bde", 
      "patronus": "stag" 
    }       
```

    CONSULTAR PERSONAGEM: 

  ```  
    url: http://localhost:8080/api/characters/1 
    metodo: GET

    Exemplo de Response: 

    {
      "id": 1,
      "name": "Harry Potter",
      "role": "student",
      "school": "Hogwarts School of Witchcraft and Wizardry",
      "house": "1760529f-6d51-4cb1-bcb1-25087fce5bde",
      "patronus": "stag"
    } 
```

    ATUALIZAR PERSONAGEM:

```
    url:http://localhost:8080/api/characters/1 
    metodo: PUT 
    body (Json): 
                  {
                    "name": "Harry Potter",
                    "role": "student",
                    "school": "Hogwarts School of Witchcraft and Wizardry",
                    "house": "1760529f-6d51-4cb1-bcb1-25087fce5bde",
                    "patronus": "stag"
                  }

    Exemplo de Response:

    {
      "id": 1,
      "name": "Harry Potter",
      "role": "student",
      "school": "Hogwarts School of Witchcraft and Wizardry",
      "house": "1760529f-6d51-4cb1-bcb1-25087fce5bde",
      "patronus": "stag"
    }  
```
    DELETAR PERSONAGEM:
    
```
    url: http://localhost:8080/api/characters/1 
    metodo: DELETE

    Exemplo de Response:

    {

    }
```

    CONSULTAR TODOS PERSONAGENS:

 ```   
    url: http://localhost:8080/api/characters 
    metodo: GET

    Exemplo de Response:

    [
      {
        "id": 1,
        "name": "Harry Potter",
        "role": "student",
        "school": "Hogwarts School of Witchcraft and Wizardry",
        "house": "1760529f-6d51-4cb1-bcb1-25087fce5bde",
        "patronus": "stag"
      },
      {
        "id": 2,
        "name": "Harry Potter 2",
        "role": "student",
        "school": "Hogwarts School of Witchcraft and Wizardry",
        "house": "1760529f-6d51-4cb1-bcb1-25087fce5bde",
        "patronus": "stag"
      }
    ] 
```

    CONSULTAR PERSONAGENS POR CASA:

```    
    url: http://localhost:8080/api/characters?house=1760529f-6d51-4cb1-bcb1-25087fce5bde 
    metodo: GET

    Exemplo de Response:

    [
      {
        "id": 1,
        "name": "Harry Potter",
        "role": "student",
        "school": "Hogwarts School of Witchcraft and Wizardry",
        "house": "1760529f-6d51-4cb1-bcb1-25087fce5bde",
        "patronus": "stag"
      },
      {
        "id": 2,
        "name": "Harry Potter 2",
        "role": "student",
        "school": "Hogwarts School of Witchcraft and Wizardry",
        "house": "1760529f-6d51-4cb1-bcb1-25087fce5bde",
        "patronus": "stag"
      }
    ] 
```

2. Via Swegger:

    url: http://localhost:8080/swagger-ui.html

## Testando a Api

Executar as classes de testes `CharacterServiceTest.java` e `CharacterControllerTest.java` com o JUnit Test

## Links de Hospedagem

HEROKU: https://dextra-challenge.herokuapp.com </br>
GITHUB: https://github.com/RicardoHCL/desafio-dextra </br>

## Contatos

[LinkedIn](https://www.linkedin.com/in/ricardohcl/)
[GitHub](https://github.com/RicardoHCL)
[Email](ricardolima.dev@gmail.com)
