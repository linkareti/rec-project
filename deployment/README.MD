$ mvn clean install

$ docker-compose -f compose/target/docker-compose-runner.yml up -s

$ ssh -vvvvv -N -L 9001:172.31.0.3:9001 elabd

$ ssh -vvvvv -N -L 8081:172.31.0.3:8080 elabd