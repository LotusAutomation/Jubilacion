#Autor Duvier Martinez Alvarez - Daniel López
#Email dmartinez@qvision.com.co - dalopez@qvisiom.com.co
Feature: To verify Json file data is equal to Xml data.
  As user, I want to access to LOTUS database to validate data's migration.

  @CompareData
  Scenario Outline: Compare the Lotus information with the data's migration.
    Given that I compare a Json file on a specific route from <xml>, <anexo>, <json>, <log>, <propiedades>, <xmlHijo> with the Xml file
    Then I should see that there is not message's errors in the log file

    Examples: 
      | xml                                         | anexo                                    | json                                     | log                                                    | propiedades                                                          | xmlHijo                                            |
#      | 'F:\\XMLCOMEX\\comex_2\\'                   | 'H:\\RECLASIFICADOS\\reclasificados_1\\' | 'F:\\COMEX\\comex_2\\'                   | 'F:/COMEX/comex_2/ResumenPruebasComex.log'             | 'F:\\COMEX\\comex_2\\propiedades_lotus_anexos.txt'                   | 'H:\\XMLRECLASIFICADOS\\reclasificados_1\\HIJOS\\' |
      | 'G:\\XMLRECLASIFICADOS\\reclasificados_1\\' | 'G:\\RECLASIFICADOS\\reclasificados_1\\' | 'G:\\RECLASIFICADOS\\reclasificados_1\\' | 'G:/XMLRECLASIFICADOS/ResumenPruebasReclasificados.log | 'G:\\RECLASIFICADOS\\reclasificados_1\\propiedades_lotus_anexos.txt' | 'G:\\XMLRECLASIFICADOS\\reclasificados_1\\HIJOS\\' |
