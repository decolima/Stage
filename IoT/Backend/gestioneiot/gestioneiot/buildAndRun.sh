#!/bin/sh
mvn clean package && docker build -t iot/gestioneiot .
docker rm -f gestioneiot || true && docker run -d -p 8080:8080 -p 4848:4848 --name gestioneiot iot/gestioneiot 
