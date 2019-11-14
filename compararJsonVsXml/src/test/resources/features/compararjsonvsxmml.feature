#Autor Duvier Martinez Alvarez - Daniel López
#Email dmartinez@qvision.com.co - dalopez@qvisiom.com.co
Feature: To verify Json file data is equal to Xml data.
  As user, I want to access to LOTUS database to validate data's migration.

  @CompareData
  Scenario Outline: Compare the Lotus information with the data's migration.
    Given that I compare a Json file on a specific route from <xml>, <anexo>, <json>, <log>, <propiedades>, <xmlHijo> with the Xml file
    Then I should see that there is not message's errors in the log file

    Examples:  
    | xml                    	  | anexo          		   | json     		  	    | log                                 			   | propiedades      								    | xmlHijo|
    | 'D:\\COMEX\\comex_14\\XML\\'| 'D:\\COMEX\\comex_14\\'| 'D:\\COMEX\\comex_14\\'| 'D:/COMEX/comex_14/ResumenPruebasSolicitudes.log'| 'D:\\COMEX\\comex_14\\propiedades_lotus_anexos.txt'| ''|
   # | 'D:\\XMLCOMEX\\comex_3\\' | 'Y:\comex_3\\' | 'D:\\COMEX\\3\\' | 'D:/COMEX/3/ResumenPruebasComex.log' | 'D:\\COMEX\\3\\' | 'H:\\' |
