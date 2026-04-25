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
En la interfaz Service:  
- se elimina un import incorrecto awt.Menu y se sustituye por es.ubu.lsi.model.catering.Menu