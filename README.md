# FirstPlaceHealth API

Aplicação utilizando Java e Spring Boot que forneça uma API REST cadastro
de beneficiários de um plano de saúde.

Devem ser expostos endpoints para executar as seguintes operações:
- Cadastrar um beneficiário junto com seus documentos;
- Listar todos os beneficiários cadastrados;
- Listar todos os documentos de um beneficiário a partir de seu id;
- Atualizar os dados cadastrais de um beneficiário;
- Remover um beneficiário.

## Stacks
-> Java17 + Spring Boot 3.x<br />
- Spring Security
- Spring Data JPA
- Spring Doc Open API

-> Auth0<br />
- java JWT

## Configuração Base de Dados
- PostgresSql: ou utilizar outro de sua preferência.
- Criar base de dados conforme conforme arquivo application.yaml.
- Pode utilizar o mesmo nome datase ou algum de sua preferência, alterando as configurações 
- Após tudo configurado as tabelas serão criadas automaticamente durante a execução do projeto.


## Você pode executá-lo localmente, após cloná-lo e carregá-lo no Eclipse ou IDE de sua  preferência.
Este projeto foi implementado usando o IDE Eclipse.
Então você pode clonar o projeto e importá-lo facilmente para o Eclipse.

- O projeto original:
https://github.com/augustczar/FirstPlaceHealth

## Variáveis de ambiente
- Voce pode configurar suas variaveis no seu host, na sua IDE, ou em cloud.
- Facil configuração no eclipse com seu projeto selecionado:
	- Run - Run Configurations - Environment

	* AUTHORIZATION_HEADER=Authorization
	
	
	* JWT_SECRET=sua frase secreta  
	* OPEN_API_SERVER_URL=http://localhost:sua_porta/firstPlaceHealth 
	* POSTGRE_PASSWORD=senha postgres  
	* POSTGRE_USERNAME=admin  
	* POSTGRE_URL=jdbc:postgresql://localhost:5432/firstPlaceHealth  
	* SERVER_PORT=sua porta

## Documentação Swagger
 - http://localhost:port/firstPlaceHealth/swagger-ui/index.html
 - Utilizar sua porta configurada na aplicação, exemplo: 
	- port:8080  

## Authenticando no projeto
 - Utilizar sua porta disponivel exemplo: 
	- port:8080  

 - Após subir o projeto - realizar o cadastros do usuário administrador:  
 - Cadastro usuário

  
 - endpoints:
	- http://localhost:port/firstPlaceHealth/authuser/register
	  
 - massa administrador:
	- {
    "login": "seu login",
    "password": "sua senha",
    "role": "ADMIN_USER"
}  


- massa usuário:
	- {
    "login": "seu login",
    "password": "sua senha",
    "role": "COMMON_USER"
}  


 - Login: Retorna um token de configuração:
	- http://localhost:port/firstPlaceHealth/authuser/login  

 - massa login do usuário:
	- {
    "login": "seu login",
    "password": "sua senha"
}  


## Endpoints de navegação do projeto utilizando o Postman, Insomnia ou Swagger nas simulações.
 - Utilizar sua porta disponivel exemplo: 
	- port:8080  
	
- Cadastrar um beneficiário junto com seus documentos:
	- POST
	- http://localhost:port/firstPlaceHealth/beneficiary

 - massa sugerida:
	
	- {
  "name": "Isis",
  "telephone": "8787878787",
  "birthDate": "11-10-2008",
  "documents": [
    {
      "documentTypes": "CPF",
      "description": "Beneficiario do plano ouro plus"
    }
  ]
}  

 	
- Listar todos os beneficiários cadastrados:
	- GET
	- http://localhost:port/firstPlaceHealth/beneficiary  


- Listar todos os documentos de um beneficiário a partir de seu id
	- GET
	- http://localhost:port/firstPlaceHealth/document/beneficiary/id_do_beneficiario_a_ser_listado/list  


- Atualizar os dados cadastrais de um beneficiário:
	- PUT
	- http://localhost:port/firstPlaceHealth/beneficiary/id_do_beneficiario_a_ser_atualizado/update

 - massa sugerida:
	
	- {
  "name": "Isis - Grande deusa da fertilidade e da maternidade,",
  "telephone": "8787878999",
  "birthDate": "11-10-1900",
  "documents": [
    {
      "documentTypes": "CPF",
      "description": "Plano ouro plus Egipcia"
    }
  ]
}  


- Remover um beneficiário.
	- DELETE
	- http://localhost:port/firstPlaceHealth/beneficiary/id_do_usuario_a_ser_excluido/delete
