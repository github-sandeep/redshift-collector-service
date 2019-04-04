# redshift-collector-service 
Api to fetch redshift table data
Steps to use this project
1- Clone it locally
2- Provide redshift url, user and password in RedShiftConnection.java file
3- Go to project "redshift-collector-service"
4- Run mvn clean install
5- Run the jar, java -jar redshift-collector-service.jar under /target folder
6- Hit the api to get redshift table content. GET http://localhost:8082/api/table/{tabel_name}

Note: For huge amount of data in table, pagignation need to be added in code. This api is limited to fetch small table content

