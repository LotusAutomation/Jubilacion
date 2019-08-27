# Automatización Jubilación Lotus

### Control de versiones

| Versión | Descripción  | Fecha de implementación del proyecto en los demás IDE |
| :------------:|:---------------:| :-----:|
| 1  | Validación para los .ZIP vacíos.  |02/08/2019 |
| 2  | Validación de los hijos y ejecuciones simultáneas.  |13/08/2019 |
| 3  | Mejoras al feature y segregación de la construcción del reporte en un task aparte.  |27/08/2019 |

- **Versión 1**
	- Se agregó el ajuste para que cuando los .ZIP estén vacíos, no se comparen con los archivos Json.
	- Se documentaron algunas clases del proyecto.

- **Versión 2**
	- Se agregó el ajuste para comparar también los hijos del XML (cuando sea el caso).
	- Se cambió la forma en que se leen e ingresan los datos (en este caso las rutas donde se encuentran los archivos y carpetas a comparar), con el objetivo de tener ejecuciones simultáneas, es decir, que se ejecuten pruebas inmediatamente después de que termine la anterior.  Los datos ya se ingresan desde el feature.
	- Se documentaron algunas clases del proyecto.
	
- **Versión 3**
	- Se eliminaron algunos @Given @When y @Then del step definitions, debido a que no se estaban usando.
	- Se eliminaron del archivo feature los escenarios que no se estaban utilizando.
	- Se modificó la prosa del escenario principal de la prueba, con el objetivo de tener una mejor lectura.
	- Se creó una nueva task donde se encuentra la construcción del reporte de la prueba, con el objetivo de que en los task se realice una única responsabilidad.
