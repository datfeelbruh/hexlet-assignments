FROM gradle:7.4.0-jdk17

WORKDIR /intro-to-spring

COPY /intro-to-spring .

RUN gradle installDist

CMD ./build/install/intro-to-spring/bin/intro-to-spring