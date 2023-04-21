# Тестовое задание

## Для запуска приложения нужно:
# 1)Выкачать проект
# 2)Добавить в проект файл .env c пропертями, например:

`DATASOURSE_URL=jdbc:postgresql://postgres:5432/postgres`<br>
`DATASOURSE_USERNAME=postgres`<br>
`DATASOURSE_PASSWORD=postgres`<br>
`DATASOURSE_DB_NAME=postgres`<br>

*Обратите внимание на DATASOURSE_URL, хост указан <strong>postgres</strong>.<br> 
Это важно, так как при разворачивании бд с помощью докера,<br> приложение сможет достучаться до бд только по этому хосту. 


# 2) С помощью мавена нужно сделать clean package, чтобы создать jar файл с которго будет создаваться docker image
*Если всё сделано правильно, то должна появиться папка target в которой будет TestTaskPing-0.0.1-SNAPSHOT.jar

# 3) Открываем консоль и пишем:
`docker-compose up`

# 4) Чтобы проверить всё ли запустилось, пишем в консоли:
`docker ps`

*Там должно быть два сервиса ping и postgresForTestTask

## Функционал приложения:

1) http://localhost:8080/all - эндпойнт возвращает список всех сохраненных пингов

2) http://localhost:8080/search - поиск по параметрам

3) http://localhost:8080/{id} - поиск по id