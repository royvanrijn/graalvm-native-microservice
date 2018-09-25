FROM ubuntu as builder
MAINTAINER Roy van Rijn

RUN apt-get update && \
    apt-get -y install gcc libc6-dev zlib1g-dev curl bash && \
    rm -rf /var/lib/apt/lists/*

# GraalVM

ENV GRAAL_VERSION 1.0.0-rc6
ENV GRAAL_FILENAME graalvm-ce-${GRAAL_VERSION}-linux-amd64.tar.gz

RUN curl -4 -L https://github.com/oracle/graal/releases/download/vm-${GRAAL_VERSION}/${GRAAL_FILENAME} -o /tmp/${GRAAL_FILENAME}

RUN tar -zxvf /tmp/${GRAAL_FILENAME} -C /tmp \
    && mv /tmp/graalvm-ce-${GRAAL_VERSION} /usr/lib/graalvm

RUN rm -rf /tmp/*

WORKDIR /projects
ADD . /projects/

ARG GRAAL_ARGUMENTS

RUN /usr/lib/graalvm/bin/native-image ${GRAAL_ARGUMENTS}

FROM ubuntu
MAINTAINER Roy van Rijn

RUN apt-get update && \
    apt-get -y install gcc libc6-dev zlib1g-dev curl bash && \
    rm -rf /var/lib/apt/lists/*

COPY --from=builder /projects/app /app
EXPOSE 4567
CMD ["/app"]
