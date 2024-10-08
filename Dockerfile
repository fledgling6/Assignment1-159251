# 使用 OpenJDK 17 的官方基础镜像
FROM openjdk:17-jdk-slim

# 设置时区并安装必要的包
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    tzdata \
    x11-apps \
    libxtst6 \
    && ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo 'Asia/Shanghai' > /etc/timezone \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# 把 app.jar 包添加到镜像中
ADD target/Sample_Nodepad.jar /app.jar

# 设置 DISPLAY 环境变量
ENV DISPLAY=:0

# 容器启动命令
ENTRYPOINT ["java", "-jar", "/app.jar"]

# 提示用户在运行容器之前执行的命令
# 在宿主机上运行以下命令以允许 X11 连接：
# xhost +
# 然后运行容器：
# docker run -e DISPLAY=$DISPLAY -v /tmp/.X11-unix:/tmp/.X11-unix -d your-image-name