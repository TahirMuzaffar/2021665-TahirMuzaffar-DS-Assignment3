# Use OpenJDK 11 as the base image
FROM openjdk:11-jdk-slim

# Set environment variables
ENV SPARK_VERSION=3.5.3 \
    HADOOP_VERSION=3 \
    SCALA_VERSION=2.12.17 \
    SBT_VERSION=1.5.5 \
    SPARK_HOME=/opt/spark \
    SCALA_HOME=/opt/scala \
    PATH=$PATH:/opt/spark/bin:/opt/scala/bin

# Install necessary utilities and dependencies
RUN apt-get update && apt-get install -y --no-install-recommends \
    curl \
    wget \
    tar \
    gzip \
    gnupg2 \
    ca-certificates \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

# Install Apache Spark
RUN curl -fsSL https://downloads.apache.org/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz | tar -xz -C /opt \
    && mv /opt/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION} /opt/spark

# Install Scala
RUN curl -fsSL https://downloads.lightbend.com/scala/${SCALA_VERSION}/scala-${SCALA_VERSION}.tgz | tar -xz -C /opt \
    && mv /opt/scala-${SCALA_VERSION} /opt/scala

# Install SBT
RUN curl -fsSL https://github.com/sbt/sbt/releases/download/v${SBT_VERSION}/sbt-${SBT_VERSION}.tgz | tar -xz -C /opt \
    && ln -s /opt/sbt/bin/sbt /usr/bin/sbt

# Create the 'results' directory for saving output
RUN mkdir -p /home/project/results

# Set working directory
WORKDIR /home/project

# Verify installations
RUN java -version && \
    /opt/spark/bin/spark-shell --version && \
    scala -version && \
    sbt sbtVersion

# Default command to keep the container running
CMD ["/bin/bash"]