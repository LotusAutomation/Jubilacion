#autor Duvier Martinez Alvarez
#email dmartinez@qvision.com.co

Feature: Validar Xml LOTUS
	Diego as a user wants to access the LOTUS database and validate that the migration of it has been fine.

	@ValidatesFilesLocal
	Scenario: Compare LOTUS information Local with the migrated.
	Given that I consult a Json file on a specific route
	When I compare the information with the Xml file
	Then I see successful validation messages Registered Successfully
	
	@ValidatesFilesSite
	Scenario: Compare LOTUS information with the migrated.
	Given that I consume an XML file in the lotus site
	When I compare the information with the migrated Json file
	Then I see successful validation messages Registered Successfully	
	