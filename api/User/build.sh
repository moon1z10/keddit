sudo docker stop keddis-user
sudo docker rm keddis-user
gradle build -x test
sudo docker build -t kedditapiuser:latest .
sudo docker run -d -p 8081:8081 --name keddis-user kedditapiuser:latest