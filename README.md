# HTTP API for Meowies App

Meowies API предназначен для связи базы данных приложения Meowies с клиентским приложением. Приложение Meowies было разработано в рамках лабораторной работы в университете и представлено в двух вариантах:
* [Десктопное приложение](https://github.com/larevies/Meowies)
* [Мобильное приложение](https://github.com/larevies/Meowies-for-Android)

## Структура
* [DBProperties.kt](src/main/kotlin/com/meowies/database/connection/DBProperties.kt) - реквизиты для подключения к базе данных.
* [queries](src/main/kotlin/com/meowies/database/queries) - запросы к базе данных.
* [modules](src/main/kotlin/com/example/modules)  - папка с объектами приложения.
* [plugins](src/main/kotlin/com/example/plugins) - плагины, необходимые для корректной работы.
* [routes](src/main/kotlin/com/meowies/routes) - папка с маршрутами. 
  * [BookmarkRoutes.kt](src/main/kotlin/com/meowies/routes/BookmarkRoutes.kt) - маршруты для работы с пользовательскими закладками.
  * [UserRoutes.kt](src/main/kotlin/com/meowies/routes/UserRoutes.kt) - маршруты для работы с пользователями, их данными.
* [Application.kt](src/main/kotlin/com/meowies/Application.kt) - запуск сервера. 

Примеры запросов находятся в папке [test](src/test/kotlin).

## Примеры запросов с ответами:
### Создание нового пользователя
```
POST http://127.0.0.1:8080/user
Content-Type: application/json
{
  "Name": "юзер1",
  "Email": "юююююю@гугл.ком",
  "Password": "зеер",
  "Birthday": "yesterday",
  "ProfilePicture": "2"
}

Ответ (успех): Success, status = 201
Ответ (неудача): Fail, status = 409
```
### Получение информации о пользователе по адресу почты
```
GET http://192.168.0.10:8080/user/юююююю@гугл.ком

Ответ (успех): 
{
  "Id":1,
  "Name":"юзер1",
  "Email":"юююююю@гугл.ком",
  "Password":"зеер",
  "Birthday":"2024-02-20",
  "ProfilePicture":"2"
}

Ответ (неудача):
User with email "юююююю@гугл.ком" is not in the base, status = 404
```
### Изменение почты
```
POST http://192.168.0.10:8080/change/email
Content-Type: application/json
{
  "Email": "Email",
  "NewEmail": "NewEmail"
}
Ответ (успех): Success, status = 202
Ответ (неудача): Fail, status = 409
```
### Удаление закладки
```
DELETE http://192.168.0.10:8080/bookmark/1

Ответ (успех): Success, status = 202
Ответ (неудача): Fail, status = 409
```
