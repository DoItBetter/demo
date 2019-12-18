#FROM
#
## 指定时区
#RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
#
#ENV JAR_NAME qt-data-web-0.0.1-SNAPSHOT.jar
#ENV WORK_DIR /usr/local/services/app
#
#COPY qt-data-web/target/$JAR_NAME $WORK_DIR
#
## 设置目录(需要通过基础镜像获取)
#ENV CATALINA_HOME /home/admin/taobao-tomcat
#
## 设置 EDAS-Container / Pandora 应用容器版本
#ENV EDAS_CONTAINER_VERSION V3.5.4
#LABEL pandora V3.5.4
#
## 将启动命令写入启动脚本 start.sh
#RUN echo 'java -jar $CATALINA_OPTS $PRIVATE_OPTS -Dcatalina.logs=$CATALINA_HOME/logs -Duser.timezone=Asia/Shanghai -Dfile.encoding=UTF-8 "$WORK_DIR/$JAR_NAME" --server.context-path=/ --server.port=8080 --server.tomcat.uri-encoding=ISO-8859-1 --server.tomcat.max-threads=400' >> /home/admin/start.sh && chmod +x /home/admin/start.sh
#
#WORKDIR $CATALINA_HOME
#
#CMD ["/bin/bash", "/home/admin/start.sh"]
