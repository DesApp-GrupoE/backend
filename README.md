<h1>Comprando desde casa</h1>  
  
La aplicación esta enfocada a que comerciantes de barrio puedan acercar sus productos a sus clientes 
mediante una website a causa de la cuarentena.   
Los comerciantes podrán cargar los productos que ofrecen junto a sus precios, dando la posibilidad de entregar los 
productos mediante envio o retiro por el local.  
Los clientes podrán ver los todos los productos cargados por todos los comerciantes y podrán comprarlos, eligiendo el 
modo de entrega.  
  
<h2>Configuración del entorno</h2>    
  
<h3>Requisitos</h3>      
  
* Maven
* Java 11
* PostgreSQL   
  
  
<h3>Instalación</h3>  
  
Ingresar en consola lo siguiente, para bajar e instalar las dependencias.  
```bash
mvn install
```  
    
<h3>Deploy</h3>  
  
Para levantar la aplicación se debe ingresar en consola  
```bash  
mvn spring-boot:run
```  
Esto levantará la aplicación en el puerto `8080`.  
Y guardará los logs en `{user.home}/log/desapp`
    
---  

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c34b463b63b940c58212cf65b7662f48)](https://app.codacy.com/gh/DesApp-GrupoE/backend?utm_source=github.com&utm_medium=referral&utm_content=DesApp-GrupoE/backend&utm_campaign=Badge_Grade_Dashboard)

  
<h3>Ejemplos en Postman</h3>  
  
* Creación de Usuario
```bash
POST /api/auth/sign-up HTTP/1.1
Host: localhost:8080

{
	"name": "Ariel",
	"surname": "Ramirez",
	"email": "test@test.test",
	"password": "secret"
}
```         
  
La aplicación esta preparada para que devuelva un error personalizado en caso de se envíe un json incompleto o 
que falten campos obligatorios en el modelo  
  
---  
   
<h3>Consumir Api desde Heroku</h3>  
  
Siguiendo el ejemplo anterior de la creación de usuario, lo único que debemos hacer es cambiar el la url de la app.  
Entonces cambiamos `localhost:8080` por `https://secret-plains-01193.herokuapp.com/`   
   
---     

* [Trello](https://trello.com/b/P3cJGcJx/comprando-en-casa) 
* [Travis CI](https://travis-ci.com/github/DesApp-GrupoE/backend)
* [Codacy](https://app.codacy.com/gh/DesApp-GrupoE)
