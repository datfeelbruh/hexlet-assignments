FROM gradle:7.4.0-jdk17

WORKDIR /java-spring-ru/authorization

COPY /java-spring-ru/authorization .

RUN gradle installDist

CMD ./build/install/authorization/bin/authorization
