# back
# устанавливаем самую лёгкую версию JVM
FROM openjdk:19-jdk-alpine

# указываем ярлык. Например, разработчика образа и проч. Необязательный пункт.
LABEL maintainer="baydev2002@gmail.com"

# указываем точку монтирования для внешних данных внутри контейнера (как мы помним, это Линукс)
VOLUME /tmp

# внешний порт, по которому наше приложение будет доступно извне
EXPOSE 8080

# указываем, где в нашем приложении лежит джарник
ARG JAR_FILE=build/libs/ResMon-0.0.1-SNAPSHOT.jar

# добавляем джарник в образ под именем rebounder-chain-backend.jar
ADD ${JAR_FILE} res-mon-backend.jar

# команда запуска джарника
ENTRYPOINT ["java","-jar","/res-mon-backend.jar"]