# secret-filter

A service used to populate placeholders in property files when deploying applications.

*Problem*: Property files are used to manage envoirnmental properties and sometimes contain private information. Given that my source-code is public, how to I manage keeping these private values private?

*Solution*: Use placeholders in the property files and replace them when deploying your applications. This service can be used in your automatic deployment process to replace these placeholders representing private information.
