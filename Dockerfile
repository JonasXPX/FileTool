FROM gradle:8-jdk17-alpine as stage-build

WORKDIR /build

COPY . /build/

RUN gradle -x test build

FROM amazoncorretto:17-alpine

WORKDIR /app

COPY --from=stage-build /build/build/libs/ /app

ENTRYPOINT [ "java", "-jar", "file-tool-1.0-SNAPSHOT.jar" ]