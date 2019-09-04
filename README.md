# Automatización Jubilación Lotus

### Control de versiones

| Versión | Descripción  | Fecha de implementación del proyecto en los demás IDE |
| :------------:|:---------------:| :-----:|
| 1  | Validación para los .ZIP vacíos.  |02/08/2019 |
| 2  | Validación de los hijos y ejecuciones simultáneas.  |13/08/2019 |
| 3  | Mejoras al feature y segregación de la construcción del reporte en un task aparte inmerso en la clase compararJson.  |27/08/2019 |
| 4  | Replicación de los cambios hechos en compararJson (Hijos y en su reporte), en las demás clases de comparación.  |29/08/2019 |
| 5  | Segregación de la construcción del reporte (ya está en el @ Then) que estaba inmerso en las clases de comparación. |30/08/2019 |
| 6  | Se agregó validación de tag "datatimepar" para fechas en "datetimelist", además se implementó validación obligatoria de "universalId.PDF" para base de datos de COMEX.|03/09/2019|

- **Versión 1**
	- Se agregó el ajuste para que cuando los .ZIP estén vacíos, no se comparen con los archivos Json.
	- Se documentaron algunas clases del proyecto.

- **Versión 2**
	- Se agregó el ajuste para comparar también los hijos del XML (cuando sea el caso).
	- Se cambió la forma en que se leen e ingresan los datos (en este caso las rutas donde se encuentran los archivos y carpetas a comparar), con el objetivo de tener ejecuciones simultáneas, es decir, que se ejecuten pruebas inmediatamente después de que termine la anterior.  Los datos ya se ingresan desde el feature.
	- Se documentaron algunas clases del proyecto.
	
- **Versión 3**
	- Se eliminaron algunos @ Given @ When y @ Then del step definitions, debido a que no se estaban usando.
	- Se eliminaron del archivo feature los escenarios que no se estaban utilizando.
	- Se modificó la prosa del escenario principal de la prueba, con el objetivo de tener una mejor lectura.
	- Se creó una nueva task donde se encuentra la construcción del reporte de la prueba, con el objetivo de que en los task se realice una única responsabilidad.

- **Versión 4**
	- Se replicaron los cambios hechos en la clase compararJson, en las demás clases de comparación.
	- Se hizo uso de la clase construirElReporte en las clases de comparación que no lo tenían implementado.
	
- **Versión 5**
	- Se creó una nueva clase Guardar, que almacena los valores a utilizar en la construcción del reporte.
	- En las clases de comparación, se llama a la clase Guardar para enviar.
	- Se agregó la task de construirElReporte en la eqtiqueta @ Then, que recibe como parámetro los valores almacenados en la clase Guardar.
	- Se eliminó el @ When y se modificó el feature para efectos de lectura.
	
- **Versión 6**
	- Se realizó modificación deL método "readField"(para arrayList) perteneciente a la clase ElementosJson y se agregó validación de etiqueta "datetimepair" incluidas en "datimelist" de bases de datos COMEX.
	- Se realizó modificación del método "comparateAttachments" perteneciente a la clase ArchivosComprimidos, en dónde se agregó la validación que busca el archivo "UniversalID.PDF" en el campo attachment del Json dónde se debe encontrar obligatoriamente para las bases de datos de COMEX.
	