FROM java:8
WORKDIR /root
RUN mkdir -p /root/web/logs
EXPOSE 9090
ARG JAR_FILE
ADD target/${JAR_FILE} /root/tool.jar
ENTRYPOINT ["java", "-jar","/root/tool.jar"]
