<h1>Comprando desde casa</h1>  
  
La aplicación esta enfocada a que comerciantes de barrio puedan acercar sus productos a sus clientes 
mediante una website a causa de la cuarentena.   
Los comerciantes podrán cargar los productos que ofrecen junto a sus precios, dando la posibilidad de entregar los 
productos mediante envio o retiro por el local.  
Los clientes podrán ver los todos los productos cargados por todos los comerciantes y podrán comprarlos, eligiendo el 
modo de entrega.  
  
<h2>Configuración del entorno</h2>    
  
<h3>Requisitos</h3>      
* Java 11
* Path `/var/log/desapp` con permisos de usuario para que se pueda loguear la app   
  
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
  
```bash
GET /api/test HTTP/1.1
Host: localhost:8080
```         
  
```bash
POST /api/test HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
	"id" : 1,
	"email": "test@test.test",
	"password": "secret"
}
```  