# Control 2 - TBD
## Este control fue desarrollado por el grupo N°5.
### Integrantes de grupo 5:
* Jaime Riquelme
* Fabián lizama
* Cristóbal Torres
* Fernando Solís
* Camilo Campos

# Herramientas de desarrollo

## Base de datos
* Postgres SQL versión 14
* PgAdmin versión 4

## Backend
* Springboot 3.2.5
* Gradle
* IntelliJ IDEA Ultimate / Visual Studio Code
* JDK versión 17
* Postman

## Frontend
* Vue3
* Nuxt3
* Node.js 18^
* Visual Studio Code

# Guía de Instalación y Ejecución
Para ejecutar el proyecto se deben seguir los siguientes pasos:

## 1. Clonar el repositorio

git clone https://github.com/FernandoSolisPaz/Grupo5-Control2-TBD

## 2. Instalación de IntelliJ IDEA / Visual Studio Code

1. Descarga el IDE elegido:
- [Descargar IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
- [Descargar VSCode](https://code.visualstudio.com/)

2. Instala el IDE siguiendo las instrucciones específicas para tu sistema operativo.

## 3. Instalación de PostgreSQL y pgAdmin

### 3.1 Instalación de PostgreSQL

1. Descarga PostgreSQL desde el sitio web oficial: [Descargar PostgreSQL](https://www.postgresql.org/download/).

2. Sigue las instrucciones de instalación para tu sistema operativo.

### 3.2 Instalación de pgAdmin

1. Descarga pgAdmin desde el sitio web oficial: [Descargar pgAdmin](https://www.pgadmin.org/download/).

2. Instala pgAdmin siguiendo las instrucciones para tu sistema operativo.

## 4. Crear base de datos y cargar información

### 4.1 Crear base de datos
1. Asegúrate de estar conectado a PostgreSQL con pgAdmin:
   Abre pgAdmin y conéctate a tu servidor PostgreSQL.
   Si aún no tienes una conexión configurada, puedes hacerlo siguiendo las instrucciones de pgAdmin.

2. Selecciona el servidor y la base de datos:
   En el panel izquierdo de pgAdmin, selecciona tu servidor PostgreSQL.
   Luego, selecciona la base de datos en la que deseas ejecutar el script SQL. Si no tienes una base de datos existente, puedes crear una haciendo clic derecho en "Databases" y seleccionando "Create" > "Database".

3. Ejecuta el Script SQL:
   Haz clic con el botón derecho en la base de datos seleccionada y elige "Query Tool" para abrir una ventana donde podrás ejecutar consultas SQL.

4. En la ventana del "Query Tool", abre el archivo SQL que contiene el script que deseas ejecutar. Puedes hacerlo seleccionando "File" > "Open" y navegando hasta el archivo.

5. Una vez abierto el archivo SQL, puedes ejecutarlo haciendo clic en el botón "Execute" o presionando la tecla F5.

6. Verifica la creación de la base de datos:
   Después de ejecutar el script, verifica que la base de datos y las tablas se hayan creado correctamente utilizando las funciones de pgAdmin.

### 4.2 Cargar la información

1. Abre pgAdmin y conéctate a tu servidor PostgreSQL.

2. Selecciona la base de datos en la que deseas cargar los datos.

3. Abre el "Query Tool" haciendo clic con el botón derecho en la base de datos seleccionada y elige "Query Tool".

4. Abre tu archivo SQL que contiene el script para cargar datos utilizando la opción "File" > "Open" en la ventana del "Query Tool". (Seleccionar archivo dbCreate.sql y loadData.sql)

5. Ejecuta el script SQL haciendo clic en el botón "Execute" o presionando la tecla `F5`


## 5. Ejecución del proyecto

### 5.1 Ejecución Backend

1. Abre tu IDE preferido instalado anteriormente y abre la carpeta [api](./api).

2. Asegúrate de que todas las dependencias de tu proyecto estén instaladas.

3. Adicionalmente dirigirse al apartado de configuracion de variables de entorno y agregar las siguientes variables. (USER=postgres;PORT=5432;database.password=1234)
   Nota: Credenciales deben ser cambiadas a otras si su pc posee otras en PostGresql.

4. Inicia el proyecto backend, dirigiendote al *Application* y ejecutando la api mediante el botón verde (run) de arriba a la derecha.

5. Abre tu navegador web y accede a la URL proporcionada por el servidor de desarrollo (generalmente, [http://localhost:8080/](http://localhost:8080/)) para ver tu proyecto en funcionamiento.

### 5.2 Ejecución Frontend

1. Abre una terminal en la carpeta [landing](./landing).

2. Asegúrate de haber instalado Node.js 18 o superior. Si no los tienes instalados, puedes descargarlos desde [https://nodejs.org/](https://nodejs.org/).

3. Instala las dependencias del proyecto ejecutando el siguiente comando:

   ```bash
   npm install
   ```
   
4. Ejecuta el comando
   ```bash
   npm run dev
   ```
   

## 6. Accede a la aplicacion

1. Dirigete a la URL desplegada por el front, [http://localhost:3000/](http://localhost:3000/).

2. Listo.

Ahora puedes realizar ejecuciones dentro de la aplicación mediante la vista del navegador.

