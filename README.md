# FaceRec

java -jar target/demo-0.0.1-SNAPSHOT.jar

mvn clean package

python3 -m http.server

docker run: docker run -p 8080:80 backend

tag docker image: docker tag  backend containerxisheng.azurecr.io/backend:v1

login in docker container: docker login containerxisheng.azurecr.io

push file to docker: docker push containerxisheng.azurecr.io/backend:v1
