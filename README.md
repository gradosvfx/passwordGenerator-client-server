- Generador de Contraseñas - Cliente/Servidor en Java (Eclipse)

Proyecto en Java que implementa una comunicación cliente-servidor mediante sockets TCP.  
El sistema permite generar contraseñas seguras a partir de los requisitos indicados por el usuario.

- Descripción:

	1. El servidor escucha en el puerto 4321 y gestiona las peticiones de los clientes.  
	2. El cliente se conecta al servidor y, a través de la consola, introduce los parámetros necesarios para generar una contraseña personalizada.
	3. El servidor valida los datos recibidos y, si son correctos, genera la contraseña utilizando `SecureRandom`.

- Funcionalidades:

	- Conexión cliente-servidor mediante sockets.
	- Validación de entrada de datos.
	- Generación de contraseñas con:
  		- Mayúsculas
  		- Minúsculas
  		- Dígitos
  		- Caracteres especiales
	- Manejo básico de errores de conexión y validación.
	- Registro de eventos en el servidor (logs por consola).

- Estructura del proyecto:

	- src/
		- cliente/
  			- cliente.java
			- MainCliente.java
	
		- servidor/
			- MainServidor.java
			- RequisitosPass.java
			- ServicioPass.java
			- Servidor.java 

- Cómo ejecutar:

	1. Ejecutar primero 'MainServidor' (⚠️ El servidor debe estar iniciado antes de lanzar el cliente)
	2. Ejecutar después 'MainCliente'



- Tecnologías utilizadas

	- Java
	- Sockets TCP
	- Programación orientada a objetos
	- SecureRandom para generación segura de contraseñas

- Objetivo del proyecto


	Practicar la arquitectura cliente-servidor, la comunicación por red y la organización del código separando responsabilidades entre clases.
