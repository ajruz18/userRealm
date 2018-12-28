
Deployed in Tomcat 8.5 and Java 8


Data Model:

	drop all objects;
	
	create table userRealm (
	    id integer not null,
	    description varchar(255),
	    key varchar(32),
	    name varchar(255) not null,
	    primary key (id)
	);
	
	alter table userRealm 
	    add constraint UK_name unique (name);

Developed with the next points:
- In Rest API, JSON and XML format as support.
- Now REALM name table as  userRealm name table.
- id and name columns are attribute in realm tag to XML format in response.
- key value for UserRealm is auto generate (non encryption).
- Name column to UserRealm table is unique.
- Specific sequence is used "USERREALM_ID_SEQ" for userRealm table.
- Create UserRealm and get UserRelm are cached by "ehcache"
- Through a validator check if name field is present in userRealm object.
- Error object is used to wrong calls. 
- Spring JPA is used to manage data persistence.
- Available to deploy in Tomcat 8.5 (/realm context).

Versions used:
- Java 8
- Tomcat 8.5 
- Spring 5.0.8
- Spring JPA 2.1.3
- Hibernate 5.2.2
- Jackson 2.9.5
- Ehcache 2.10.6
- H2 1.4.197

