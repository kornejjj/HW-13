package org.example;

import solution.Task1;
import solution.Task2;
import solution.Task3;

public class Main {

    public static void main(String[] args) throws Exception {
        /**
         * Программа должна содержать методы для реализации следующего функционала:
         *
         * создание нового объекта в https://jsonplaceholder.typicode.com/users. Возможно, вы не увидите обновлений на сайте. Метод создания работает правильно, если в ответ на JSON с объектом вернулся такой же JSON, но с полем id со значением на 1 больше, чем самый большой id на сайте.
         *
         * обновление объекта в https://jsonplaceholder.typicode.com/users. Возможно, обновления не будут видны на сайте напрямую. Будем считать, что метод работает правильно, если в ответ на запрос придет обновленный JSON (он должен быть точно таким же, какой вы отправляли).
         *
         * удаление объекта из https://jsonplaceholder.typicode.com/users. Здесь будем считать корректным результат - статус из группы 2хх в ответ на запрос.
         *
         * получение информации обо всех пользователях https://jsonplaceholder.typicode.com/users
         *
         * получение информации о пользователе с определенным id https://jsonplaceholder.typicode.com/users/{id}
         *
         * получение информации о пользователе с опредленным username - https://jsonplaceholder.typicode.com/users?username={username}
         */
        Task1 task1 = new Task1();
        task1.run();

        /**
         * Дополните программу методом, который будет выводить все комментарии к последнему посту определенного пользователя и записывать их в файл.
         *
         * https://jsonplaceholder.typicode.com/users/1/posts Последним будем считать пост с наибольшим id.
         *
         * https://jsonplaceholder.typicode.com/posts/10/comments
         *
         * Файл должен называться "user-X-post-Y-comments.json", где Х - номер пользователя, Y - номер поста.
         */
        System.out.println("***************************************************");
        Task2 task2 = new Task2();
        task2.run();

        /**
         * Дополните программу методом, который будет выводить все открытые задачи для пользователя Х.
         *
         * https://jsonplaceholder.typicode.com/users/1/todos.
         *
         * Открытыми считаются все задачи, у которых completed = false.
         */
        System.out.println("***************************************************");
        Task3 task3 = new Task3();
        task3.run();
    }
}