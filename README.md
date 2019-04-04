# redshift-collector-service
Api to fetch redshift table data

Steps to use this project
1- Clone it locally
2- Go to project "redshift-collector-service"
3- Run mvn clean install
4- Run the jar, java -jar redshift-collector-service.jar under /target folder
5- Hit the api to get redshift table content. GET http://localhost:8082/api/table/{tabel_name}

Note: For huge amount of data in table, pagignation need to be added in code. This api is sample to get small table content

