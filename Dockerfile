FROM maven:3.8.6-eclipse-temurin-17-focal AS build
COPY . /source
WORKDIR /source
RUN apt-get update && apt-get -y --no-install-recommends install unzip
RUN mvn clean install
RUN mvn jpro:release
RUN unzip target/Inventory-jpro.zip -d target/Inventory-jpro

FROM azul/zulu-openjdk:17-latest AS release
WORKDIR /app
ENV DEBIAN_FRONTEND=noninteractive
RUN echo "APT::Get::force-yes \"true\";" > /etc/apt/apt.conf.d/90forceyes \
&& echo 'debconf debconf/frontend select Noninteractive' | debconf-set-selections
RUN apt-get update && apt-get -y --no-install-recommends install xorg gtk2-engines libasound2 libgtk2.0-0
COPY --from=build /source/target/Inventory-jpro .
CMD ["/bin/bash", "./Inventory-jpro/bin/start.sh"]

# Convert to alpine or distroless