FROM openjdk:17-jdk-slim

# Set timezone and install necessary packages
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    tzdata \
    x11-apps \
    libxtst6 \
    && ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo 'Asia/Shanghai' > /etc/timezone \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Add app.jar to the image
ADD libs/Sample_Nodepad-1.0-SNAPSHOT-jar-with-dependencies.jar /app.jar

# Set DISPLAY environment variable
ENV DISPLAY=:0

# Container start command
ENTRYPOINT ["java", "-jar", "/app.jar"]

# Instructions for the user before running the container
# Execute the following command on the host machine to allow X11 connection:
# xhost +
# Then run the container:
# docker run -e DISPLAY=$DISPLAY -v /tmp/.X11-unix:/tmp/.X11-unix -d your-image-name
