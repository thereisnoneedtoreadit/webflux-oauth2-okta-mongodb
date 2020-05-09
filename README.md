## Organizer & notification sender

Сервис, принимающий и хранящий задачи пользователя с функциями получения/удаления/редактирования.
По наступлению даты, указанной пользователем в задаче, отсылается письмо на почту данного пользователя.

Регистрация и авторизация сделаны через recourse-сервис Okta, протокол Oauth2.

Для отправки уведомлений используется SMTP-сервер Sendgrid, а также AKKA Actors, которые создаются в момент 
получения задачи, и отправляют письмо на указанный адрес в нужный момент времени, после чего уничтожаются.

В качестве основного фреймворка используется Spring Webflux, в качестве базы данных - MongoDb.

_____________________________________________________________________________________________________________

A service that accepts and stores user tasks with the functions of receiving / deleting / editing.
Upon the date specified by the user in the task, a letter is sent to the mail of this user.

Registration and authorization are done through the Okta recourse service, Oauth2 protocol.

To send notifications, the Sendgrid SMTP server is used, as well as AKKA Actors, which are created at the moment
receiving a task, and send a letter to the specified address at the right time, after which they are destroyed.

Spring Webflux is used as the main framework, MongoDb is used as the database.
