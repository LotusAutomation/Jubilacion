# Automatización Jubilación Lotus

### Control de versiones

| Versión | Descripción  | Fecha de implementación del proyecto en los demás IDE |
| :------------:|:---------------:| :-----:|
| 1  | Validación para los .ZIP vacíos.  |02/08/2019 |
| 2  | Validación de los hijos y ejecuciones simultáneas.  |13/08/2019 |

- **Versión 1**
	- Se agregó el ajuste para que cuando los .ZIP estén vacíos, no se comparen con los archivos Json.
	- Se documentaron algunas clases del proyecto.

- **Versión 2**
	- Se agregó el ajuste para comprar también los hijos del XML (cuando sea el caso).
	- Se cambió la forma en que se leen e ingresan los datos (en este caso las rutas donde se encuentran los archivos y carpetas a comparar), con el objetivo de tener ejecuciones simultáneas, es decir, que se ejecuten pruebas inmediatamente después de que termine la anterior.  Los datos ya se ingresan desde el feature.
	- Se documentaron algunas clases del proyecto.
