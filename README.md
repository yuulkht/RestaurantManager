# Домашнее задание №2 КПО
## __Кухтина Юлия Егоровна, БПИ224__
### Система управления заказами в ресторане
#### Чтобы запустить приложение, необходимо склонировать репозиторий, открыть проект в `IntelliJ IDEA` и запустить `Main.kt`, распологающийся в __src\main\kotlin\ru\hse\software\construction__  с помощью `run`.

### Файлы для работы приложения

#### Для использования приложения необходимо указать `json` файлы для сохранения состояния программы:
* Файл для сохранения информации о состоянии ресторана
* Файл для сохранения информации о пользователях приложения

#### Готовые файлы для работы: `restaurant.json` и `userStorage.json`

#### Особенности работы программы с файлами: при возникновении проблем с чтением из файлов и сохранении информации в них пользователю приложения выводятся соответствующие сообщения об ошибках. При использовании некорректных путей к файлам программа завершается, при использовании файлов с некорректным содержанием продолжает работать, создавая "с нуля" объект ресторана и хранилище пользователей.

#### P.S. Еще, почему-то, иногда при использовании файла с некорректным содержимым для ресторана программа не завершается корректно после нажатия `q`, я дебажила, но источник этого странного поведения так и не нашла, скорее всего проблема связана с объектом ресторана, в котором почему-то не завершаются корректно потоки в таком случае, хотя должны.


### Аутентификация

#### Для использования приложения необходим вход в систему. Вы можете войти с помощью уже созданного аккаунта администратора (при использовании готовых файлов):  
__Логин:__ admin  
__Пароль:__ 1234

#### И посетителей:
__Логин:__ visitor  
__Пароль:__ 000 

__Логин:__ olga  
__Пароль:__ 999

__Логин:__ maria  
__Пароль:__ 888

#### Вы можете зарегистрироваться в системе:
* Чтобы создать администратора, придумайте логин, пароль, а также введите специальный код `1234` для регистрации администратора
* Чтобы создать посетителя, придумайте логин, пароль, в поле специального кода укажите что угодно

### Функционал приложения

#### После входа в систему вам будут доступен весь необходимый функционал в зависимости от типа вашего пользователя, а также возможность выйти из учетной записи. 
#### Для администратора доступны возможности 
* добавления/удаления позиций в меню ресторана, 
* обновление актуального наличия в ресторане, 
* просмотр статистики по ресторану.
#### Для посетителя доступны возможности 
* создания заказа (внутри нее можно добавить позицию / просмотреть заказ / завершить выбор / отменить заказ), 
* оплаты готовых заказов для пользователя (там же можно сразу оставить отзыв об оплаченных заказах) 
* пополнение счета, с которого оплачиваются заказы
#### Данные пользователей надежно защищены в приложении, для шифрования паролей используется BCrypt.

### Используемые паттерны проектирования
* `Цепочка обязанностей`. Для валидации команд, направляемых пользователем. При получении от пользователя команды, мы проверяем, зарегистрирован ли пользователь в системе, доступна ли ему команда, не является ли она неопознанной и тд, поэтому использование этого паттерна упрощает взаимодействия внутри программы, избавляет разработчика от необходимости использовать сотню ветвлений и улучшает читаемость кода.
* `Абстрактная фабрика`. Для создания объектов блюд. Возможно, сейчас использование этого паттерна в программе выглядит "притянутым за уши", но я подумала о том, что в ресторанах обычно меню делится на какие-то классические категории - супы, горячие блюда, напитки, десерты и тд, а также очень часто бывает так, что в ресторане несколько меню - обычное, зимнее, меню "8 марта" и тд. Если мы захотим все это добавить в программу, то используемый паттерн облегчит создание нужных объектов, поэтому его применение обосновано.
* `Наблюдатель`. Для отслеживания статуса заказа. Каждый заказ может иметь несколько статусов - принят, готовится, готов, и пользователи должны получать сообщения о том, что статус их заказа обновился, поэтому постетители подписываются на обновления их заказов и, каждый раз при изменении статуса заказа, они получают уведомление об этом.

### Использование многопоточности для обработки заказов

#### В объекте ресторана есть `OrderManager`, он отвечает за обработку заказов. Он создает пул из 3 потоков, который потом заполняется шеф-поварами `chef`, которые имеют доступ к одной очереди. Как только заказ попадает в очередь, свободный шеф берет его на обработку. Если все шефы заняты, то заказ находится в очереди, пока кто-нибудь не освободится и не возьмет его. Таким образом, заказы выполняются в том порядке, в котором они попадают в очередь.
