# Sistema de Gestión de catering
>**Luis Menendez Ramos**  
  
Este proyecto implementa un sistema de gestión de catering  
utilizando JPA (Hibertante) para realizar el acceso a los datos.  
  
Se han modelado las entidades principales del dominio (Cliente, Compra, Menu y Bonocliente) y sus relaciones,  
siguiendo el esquema proporcionado.  
  
---  
## Estructura del proyecto
- model.catering: Entidades JPA
- dao.catering: Acceso a datos DAO
- service.catering: logica de negocio
- test: pruebas con TestClient  

---  
  
# Tecnología utilizada
- Java
- JPA (Hibernate)
- JDBC (para las pruebas)
- Eclipse
  
---  
## Problemas encontrados
**En la interfaz Service:**    
- se elimina un import incorrecto awt.Menu y se sustituye por es.ubu.lsi.model.catering.Menu
  
**Uso de TypedQuery frente a Query**  
En la implementacion de las consultas JPA he optado finalmente por utilizar TypedQuery en lugar de Query  
ya que elimina se evita cast innecesarios y warnings, mejorando la claridad del código  
  
Referencia:
https://docs.oracle.com/javaee/7/api/javax/persistence/TypedQuery.html  
https://stackoverflow.com/questions/17306655/using-the-jpa-criteria-api-can-you-do-a-fetch-join-that-results-in-only-one-joi

