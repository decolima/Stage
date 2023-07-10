# Build
mvn clean package && docker build -t iot/gestioneiot .

# RUN

docker rm -f gestioneiot || true && docker run -d -p 8080:8080 -p 4848:4848 --name gestioneiot iot/gestioneiot 

# System Test

Switch to the "-st" module and perform:

mvn compile failsafe:integration-test