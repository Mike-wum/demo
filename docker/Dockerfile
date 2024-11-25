# 使用官方的 OpenJDK 17 运行时作为基础镜像  
FROM openjdk:17-jdk-slim  
  
# 将当前目录下的 myapp.jar 文件复制到 Docker 容器的 /app 目录下  
COPY bookstore-0.0.1-SNAPSHOT.jar /app/bookstore.jar
  
# 设置工作目录为 /app  
WORKDIR /app  
  
# 暴露应用程序运行的端口（根据你的应用程序实际使用的端口进行调整）  
EXPOSE 8082  

# 设置环境变量来指定 Spring Boot 配置文件  
# ENV SPRING_PROFILES_ACTIVE=k8s
  
ENTRYPOINT ["java", "-Xms64m", "-Xmx128m", "-jar", "bookstore.jar"]