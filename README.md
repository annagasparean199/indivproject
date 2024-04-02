# ASDClab1
Создайте текстовый файл который содержит 50 записей 1. Каждая запись имеет минимум 5 полей с минимум 2-мя типами данных.
Ключевое поле должно быть уникальным. Записи не упорядочены. Тематика структуры данных определяется каждым студентом 
совместно с преподавателем на лабораторных занятиях.

Напишите программу, определяющую структуру данных записей 2 3. Для данной структуры данных определите:
## Lists

### Unordered
* конструктор по умолчанию
* конструктор копирования
* оператор (метод) копирования (=, clone)
* оператор (метод) сравнения ( ==, equal)
* оператор (метод) чтения из потока
* оператор (метод) записи в поток


В программе прочитайте файл в массив объектов и выведите данный массив на экран.

# студент
### Ordered
1. имя
2. фамилия
3. факультет
4. год рождения
5. год поступления
6. идентификационный номер


# Выполнение:

Для выполнения данной работы я использовала *Spring Batch*.
**Язык**: *JAVA*


**Для запуска** необходимо создать базу данных mysql с названием batch. Для этого необходимо открыть 
MySQL Command Line Client консоль и ввести "create database batch;"
После чего в файле application.properties **поменяйте** <br> spring.datasource.username=root <br>
spring.datasource.password=Anna12345!<br> на соответствующие вашей базе данных(логин и пароль). <br>
Это все что нужно для запуска.


После запуска класса *SpringBootApp.java* данные считываются из файла который находится в папке *importedcsv*,
невалидные записи(неправильный idnp или дата рождения/поступления) запишутся в файл *failed_records.csv* <br>
Правильные запишутся в базу данных с помощью чанков (chunk), то есть порциями. **Записей всего не 50, а 40000**.
Процесс займет около 20 секунд (можно и меньше, если правильно настроить chunks). После чего файл переместится в папку обработанных файлов, то есть *processedcsv*. 
Не забудьте переместить файл обратно в *importedcsv* чтобы снова запустить программу(при желании).
Может это и небольшое отклонение от задания но так мне показалось интереснее. Спасибо!
## Images

![This is an alt text.](/image/img.png)
![This is an alt text.](/image/img_1.png)