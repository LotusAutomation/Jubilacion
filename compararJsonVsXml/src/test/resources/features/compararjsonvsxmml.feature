#autor Duvier Martinez Alvarez
#email dmartinez@qvision.com.co
Feature: Verificar la igualdad de la data migrada con la esperada.
  As user, I want to access to LOTUS database to validate data's migration.

  @CompareData
  Scenario Outline: Compare the Lotus information with the data's migration.
    Given that I consult a Json file on a specific route from <xml>, <anexo>, <json>, <log>, <propiedades>, <xmlHijo>.
    When I compare the information with the Xml file
    Then I should see that there is not message's errors in the log file

    Examples: 
      | xml                                         | anexo                                    | json                                     | log                                                     | propiedades                                                          | xmlHijo                                            |
      | 'H:\\XMLRECLASIFICADOS\\reclasificados_1\\' | 'H:\\RECLASIFICADOS\\reclasificados_1\\' | 'H:\\RECLASIFICADOS\\reclasificados_1\\' | 'H:/XMLRECLASIFICADOS/ResumenPruebasReclasificados.log' | 'H:\\RECLASIFICADOS\\reclasificados_1\\propiedades_lotus_anexos.txt' | 'H:\\XMLRECLASIFICADOS\\reclasificados_1\\HIJOS\\' |
