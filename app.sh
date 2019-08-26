docker network create dev
cd api-gdd
docker build --tag api-gdd --rmi --network dev .
docker pull erlang
docker pull rabbitmq:3.7.17
docker pull mysql:8.0
cd ..
cd mq
docker build --tag mq --rmi --network dev .
cd ..
cd api-gdc
docker build --tag api-gdc --rmi --network dev .
docker run -d -p 3307:33060 --network dev --rm --name mysql-gdc -e MYSQL_ROOT_PASSWORD=19+Enero+1998 -e MYSQL_DATABASE=gdc -e MYSQL_USER=api -e MYSQL_PASSWORD=api..gdc++19  mysql:8.0
sleep 2m
docker run -d -p 3000:3000 --network dev --rm --name apigdd api-gdd
sleep 20
docker run -d -p 5672:5672 --network dev --rm --name mqservice rabbitmq:3.7.17
sleep 30
docker run -d -p 5000:5050 --network dev --rm --name api-gdc api-gdc
sleep 20
docker run --network dev --rm --name mq mq