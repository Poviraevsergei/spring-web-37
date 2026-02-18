Spring Boot:
+ application.properties / yaml
+ Spring Initializer
+ Tomcat
+ Starter
+ Logging
+ Actuator
+ Автоконфигурация

Для переноса:
1. ставим parent POM и стартеры
2. Удаляем лишние бины и создаем application.properties
3. Заполняем нужные настройки в application.properties
4. Main создаем и запускаем


Инструкция подключения Hibernate:
1. Зависимости подключить 
<dependency>
    <groupId>jakarta.persistence</groupId>
    <artifactId>jakarta.persistence-api</artifactId>
    <version>3.2.0</version>
</dependency>
<dependency>
    <groupId>org.hibernate.orm</groupId>
    <artifactId>hibernate-core</artifactId>
    <version>6.5.2.Final</version>
</dependency>

2. Поднять бины(EntityManagerFactory и EntityManager)
3. Настройка Entity
4. Добавление файла с настройками(JPA - persistence.xml)
5. Создать репозитории
6. * - удалить другие технологии для работы с БД