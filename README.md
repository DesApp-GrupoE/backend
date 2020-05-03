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
* Path `/var/log/desapp` con permisos de usuario para que se pueda loguear la aplicación
* Mysql   
  
 
<h4>Creación de usuario en MySql</h4>  
  
Para poder persistir los datos de la aplicación deberemos crear el siguiente usuario y darle permisos.    
```sql
CREATE USER 'desapp'@'localhost' IDENTIFIED BY 'desapp';
GRANT ALL PRIVILEGES ON * . * TO 'desapp'@'localhost';
FLUSH PRIVILEGES;
```   
  
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
    
---  
  
<h3>Ejemplos en Postman</h3>  
  
* Creación de Customer
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

* [Trello](https://trello.com/b/P3cJGcJx/comprando-en-casa) 
* [Travis CI](https://travis-ci.com/github/DesApp-GrupoE/backend)
* [Codacy](https://app.codacy.com/gh/DesApp-GrupoE)
