# CRUDConsole
This program the demonstration simple crud app in console. 
## CRUDconsole

This program the demonstration simple crud app in console.

### Задача

Реализовать консольное CRUD приложение, которое имеет следующие сущености: 

*Writer (id, firstName, lastName, List posts);*

*Post (id, content, created, updated, List labels);*

*Label (id, name);*

*PostStatus (enum ACTIVE, UNDER_REVIEW, DELETED);*

> **Каждая сущность имеет поле Status. В момент удаления, мы не удаляем запись из файла, а меняем её статус на DELETED.**
> 

### В качестве хранилища данных необходимо использовать текстовые файлы:

*writers.json, posts.json, labels.json*

### Работа с консолью

Пользователь в консоли должен иметь возможность создания, получения, редактирования и удаления данных.

### Слои:

model - POJO клаcсы repository - классы, реализующие доступ к текстовым файлам controller - обработка запросов от пользователя view - все данные, необходимые для работы с консолью

### Для репозиторного слоя используется базовый интерфейс:

*interface GenericRepository<T,ID>; interface WriterRepository extends GenericRepository<Writer, Integer>; class GsonWriterRepositoryImpl implements WriterRepository;*

### Импортируемые библиотеки

Для работы с json необходимо использовать библиотеку Gson(https://mvnrepository.com/artifact/com.google.code.gson/gson) Для импорта зависимостей - Maven/Gradle на выбор.

## Чтобы воспользоваться приложением
Нужно скачать и распаковать в нужную Вам папку, а затем запустить.

Либо выполнить команду git clone (клонируемый_репозиторий).
