# Reflection Task

## Требования.
### Сборщик:  Gradle
### Язык: Java
### Версия: 17
### Использовать lombok


## Задание.
* Взять за основу проект из лекции паттернов
* Придерживаться GitFlow: master -> develop -> feature/fix
* Написать CRUD для всех таблиц
* Для метода .findAll() сделать пагинацию (по умолчанию 20 элементов на странице, если pagesize не задан)
* Сделать GET метод, для генерации чека в формате pdf (если товара не существует, тогда генерируем ошибку)
* Прикрутить возможность инициализации бд и наполнения её данными с помощью параметра в application.yml файле, т.е. чтобы при подъеме приложения,  приложение создавало схему, таблицы и наполняло таблицы данными
* Фильтры
* UI НЕ нужен


## Запросы
* Get - http://localhost:8080/wagons/wagons?page=1&page-size=5
* Delete(Post) - http://localhost:8080/wagons/delete  
* {
  "id":"22222222-2222-2222-2222-222222222222"
  } 
* Create(Post) - http://localhost:8080/wagons/create
* {
  "wagonNumber": "WGN0012",
  "loadCapacity": 7000,
  "yearOfConstruction": 2023,
  "dateOfLastService": "2023-12-30"
  }
* Update(Post) - http://localhost:8080/wagons/update
* {
  "id": "11111111-1111-1111-1111-111111111111",
  "wagonNumber": "Changed",
  "loadCapacity": 9808,
  "yearOfConstruction": 2020,
  "dateOfLastService": "2022-01-01"
  }
* Get(by Id ) - http://localhost:8080/wagons/wagon?id=22222222-2222-2222-2222-222222222222
* Get (by Id pdf) - http://localhost:8080/wagons/wagon-pdf?id=22222222-2222-2222-2222-222222222222