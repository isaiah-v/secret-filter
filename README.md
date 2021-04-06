# secret-filter

A service used to populate placeholders in property files when deploying applications.

*Problem*: Property files are used to manage environmental properties and sometimes contain private information. Given that my source-code is public, how do I manage these private values?

*Solution*: Use placeholder variables in the property files and replace them when deploying my applications. This service is used in in my automatic deployment process to replace these placeholders, keeping my private information off the internet.

## Data Isolation
The properties kept in this system are separated by project and environment. Within the system, multiple projects can be created. Each project can have multiple envoirnments. And each envoirnment has its own set of properties.

## Security
To make requests, users need an Access Token that can verified with an access manager, and the client must have the scope, "secret_filter"

### Limited Access
A user with limited access can read some values. They can read details about the projects and environments. And depending on how the environment was setup, they may or may not have read access to the property values. If, for example, DEV is setup to allow reads, limited users can read the property values. If PROD, however, is setup to not allow reads, the property values will be masked for limited users.
 
 | Unmasked | Masked |
 |----------|--------|
 |`{"Property.Name": "Property Value"}`| `{"Property.Name": "******"}` |
 
### Administrative Access
As an administrator, you have read and write access to the entire system. Administrator access requires the role, "properties_admin"

Unfortunately there doesn't seem to be a standard for managing roles in OpenID Connect. Currently this project only supports authorization through [Keycloak](https://www.keycloak.org/)'s implementation.
