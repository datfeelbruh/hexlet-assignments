FROM gradle:7.4.0-jdk17

WORKDIR /java-spring-ru/authentication

COPY /java-spring-ru/authentication .

RUN gradle installDist

CMD ./build/install/intro-to-spring/bin/authentication
