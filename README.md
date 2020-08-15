# MoneyKeeper

![GitHub](https://img.shields.io/github/license/alnat/MoneyKeeper)
![GitHub commit activity](https://img.shields.io/github/commit-activity/w/alnat/MoneyKeeper) \
[![CI](https://github.com/AlNat/MoneyKeeper/workflows/CI/badge.svg)](https://github.com/AlNat/MoneyKeeper/actions)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=MoneyKeeper&metric=alert_status)](https://sonarcloud.io/dashboard?id=MoneyKeeper) \
OpenAPI: [![Swagger API](http://validator.swagger.io/validator?url=https://raw.githubusercontent.com/AlNat/MoneyKeeper/master/docs/swagger.json)](https://alnat.github.io/MoneyKeeper/)


Небольшое приложение для учета личных финансов.


Чуть более расширенный вариант Excel файла для хранения и обработки всех трат и поступлений в разрезе нескольких кошельков.

План разработки и список запланированных\реализованных фич представлен в файле `TODO`.


### Запуск

1. Развернуть БД на сервере, выполнить инициализационные скрипты, создать пользователя и выдать ему права.

1. Получить актуальный Docker образ из [packages](https://github.com/AlNat/MoneyKeeper/packages)
    
    ```shell script
    docker login https://docker.pkg.github.com -u GITHUB_USERNAME -p TOKEN_WITH_READ_AND_WRITE_ACCESS
    docker pull docker.pkg.github.com/alnat/moneykeeper/moneykeeper:latest
    ```

1. Запустить приложение в докере, передав в контейнер параметры соединения с БД

    ```shell script
    docker run -p 3000:8090 docker.pkg.github.com/alnat/moneykeeper/moneykeeper:latest --spring.datasource.username=postgres --spring.datasource.password=postgres
    ```


## Технологии

### Стек

* Java 11
* Spring Ecosystem (Boot, Data, MVC, etc.)
* Hibernate
* OpenAPI (Swagger)
* JUnit 5, Mockito
* Postgres 12+
* Docker


### Инфраструктура

* CI процесс расположен в GitHub Actions
* Построен процесс анализа кода через SonarQube с включением его в CI
* Настроен процесс упаковки приложения в Docker с публикацией в GitHub Repository после сборки в CI



## Процесс разработка

* Разработка ведется по гибкой методологии;
* Бэклог представлен в файле `TODO`;
* Процесс разработки построен на GitHub-flow;
* Все PR в master обязательно проходят процесс CI сборки и проверку всех тестов;
* Версионирование приложения происходит по semver;
* Вся документация по API ведется через OpenAPI (Swagger). Описание REST API доступно [по ссылке](https://alnat.github.io/MoneyKeeper/).



## Документация

### HLD

High Level Design

Приложение построено по классической схеме слоев: 
* слой контроллеров
* слой бизнес-логики \ сервисов
* слой СУБД



### Модели

##### Transaction

TODO

##### Account

TODO

##### Category

TODO

##### Icon

TODO



### Интерфейс

Изначально планировался Vaadin.
Но было решено отказаться от этой идеи и реализовать на обычных html, js, css... 
Потому как  переусложнено, да и слишком много платных частей у него. Да и тянет с собой Node JS...
Скорее всего с TimeLeaf как шаблонизитором (не jsp), т.к. у последних есть ограничения в SpringBoot.
Плюс какой-нибудь общий шаблон, возможно bootstrap.
