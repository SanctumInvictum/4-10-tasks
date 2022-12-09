package com.BVT2105;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        System.out.println("task 1");
        formatLetter(10, 7, "hello my name is Bessie and this is my essay");
        System.out.println("task 2");
        System.out.println(split("((()))"));
        System.out.println("task 3");
        System.out.println(toCamelCase("hello_edabit"));
        System.out.println(toSnakeCase("helloEdabit"));
        System.out.println("task 4");
        System.out.println(overTime(9, 17, 30, 1.5F));
        System.out.println("task 5");
        System.out.println(BMI("205 pounds", "73 inches"));
        System.out.println("task 6");
        System.out.println(bugger(39));
        System.out.println("task 7");
        System.out.println(toStarShorthand("abbvvccc"));
        System.out.println("task 8");
        System.out.println(doesRhyme("Sam I am!", "Green eggs and ham."));
        System.out.println("task 9");
        System.out.println(trouble(451999277, 41177722899L));
        System.out.println("task 10");
        System.out.println(countUniqueBooks("AZYWABBCATTTA", 'A'));
    }

    // №1: Эта функция принимает текст и форматирует его таким образом: каждая строка должна содержать не более
    // K(1<=K<=80) символов, не считая пробелов. Если набранное слово может поместиться в строке, помещает его в эту
    // Строку. Иначе в следующую и продолжать добавлять к этой строке. Последовательные слова разделены пробелом, в
    // конце строки не должно быть места. Эссе содержит N слов(1<=N<=100)
    public static void formatLetter(int n, int k, String text) {
        // разбиваем вводимый текст на массив слов
        String[] arrayWords = text.split(" ");
        // Вводим изменяемую строку через StringBuilder
        StringBuilder finalString = new StringBuilder();
        //Запустим цикл по массиву слов
        for (int i = 0; i<n; i++) {
            // Записываем все слова, даже если они превышают исходную длину
            finalString.append(arrayWords[i]);
            finalString.append(" ");
            // пока длинна строки и следующего слова меньше допустимой добавляем слова
            while (i < n && (finalString + arrayWords[i]).replaceAll(" ", "").length() <= k) {
                finalString.append(arrayWords[i]);
                finalString.append(" ");
                i += 1;
            }
            // выводим и обнуляем изменяемую строку
            System.out.println(finalString);
            finalString.setLength(0);
        }
    }

    // №2: Функция, которая группирует строку в кластер скобок, каждый кластер должен быть сбалансирован
    public static ArrayList<String> split(String text) {
        int open = 0;
        int begin = 0;
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            // идём по символам строки
            char currentCharacter = text.charAt(i);
            //Считаем сколько открытых и закрытых скобок,
            //Если число равно 0 после добавления, собираем в кластер через индекс начальной и конечной скобы
            if (currentCharacter == '(') open++;
            else if (currentCharacter == ')') open--;
            if (open == 0) {
                arrayList.add(text.substring(begin, i + 1));
                begin = i + 1;
            }
        }
        return arrayList;
    }
    // №3: Даны две функции, каждая из которых берет строку и преобразует ее либо в camelCase, либо в snake_case
    public static String toCamelCase(String line) {
        //Разделим строку на слова, и создадим изменяемую строку
        String[] concatLine = line.split("_");
        StringBuilder result = new StringBuilder(concatLine[0]);
        //Добавим все слова кроме первого в изменяемую строку, а первый символ в из строчного в прописной
        for (int i = 1; i < concatLine.length; i++) {
            String currentWord = concatLine[i];
            // Поменяем первый символ слова на прописной
            result.append(currentWord.substring(0, 1).toUpperCase());
            result.append(currentWord.substring(1));
        }
        return String.valueOf(result);
    }
    public static String toSnakeCase(String line) {
        StringBuilder result = new StringBuilder();
        //Запустим цикл, если он встретит прописную букву, то добавит разделитель и переведет в нижний
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (Character.isUpperCase(ch)) {
                result.append('_');
                result.append(Character.toLowerCase(ch));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    // №4: Функция, которая вычисляет сверхурочную работу и оплату связанную с ней
    // с 9 до 17 - обычные часы, после 17 - сверхурочная. На вход подается массив с 4 значениями:
    // начало и конец рабочего дня, ставка и множитель сверхурочных
    public static String overTime(float... massive) {
        // Зададим формулу рассчета оплаты за сверхурочную и обычную работу,
        // время обычной работы считаем просто перемножая время на стандартную ставку,
        // время переработки находим через половину суммы времени переработки и модуля времени переработки, чтобы учесть случаи, когда переработки нет
        double salary = (massive[1] - massive[0] + ((massive[1] - 17) + Math.abs(massive[1] - 17)) / 2 * (massive[3] - 1)) * massive[2];
        // Отформатируем возвращаемое значение
        return '$' + String.format("%.2f", salary);
    }

    // №5: Функция, которая принимает вес и рост и возвращает индекс массы тела и связанную с ним категорию
    public static String BMI(String weight, String height) {
        // Разделим вес и рост на значение и единицы измерения
        String[] weightArray = weight.split(" ");
        String[] heightArray = height.split(" ");
        float BMI;
        //всё переводим в систему СИ
        float weightNumber = Float.parseFloat(weightArray[0]);
        if (weightArray[1].equals("pounds")) weightNumber *= 0.453592F;
        float heightNumber = Float.parseFloat(heightArray[0]);
        if (heightArray[1].equals("inches")) heightNumber *= 0.0254F;
        // Рассчитаем ИМТ
        BMI = weightNumber / (heightNumber * heightNumber);
        String BMIString = String.format("%.1f", BMI);
        // Определим категорию
        if (BMI < 18.5) {
            return BMIString + " Underweight";
        } else if (BMI < 25) {
            return BMIString + " Normal weight";
        }
        return BMIString + " Overweight";
    }

    // №6: Эта функция принимает число и возвращает его мультипликативное постоянство, которое представляет собой
    // кол-во раз, которое мы должны умножать цифры в num, пока не достигнем одной цифры
    public static int bugger(int num) {
        // Зададим счетчик
        int count = 0;
        // пока наше число представляет из себя более чем одну цифру заведем новое число, которое будет последней цифрой исходного числа
        while (num > 9) {
            int tempNum = 1;
            while (num > 0) {
                // новое число становится последней цифрой исходного
                tempNum *= num % 10;
                // от исходного числа отрезаем последнюю цифру
                num /= 10;
            }
            num = tempNum;
            // Увеличиваем счетчик
            count++;
        }
        return count;
    }

    // №7: Функция, преобразующая строку в звездную стенографию. Если символ повторяется n раз, то он преобразуется в символ*n
    public static StringBuilder toStarShorthand(String line) {
        StringBuilder NewLine = new StringBuilder();
        line += " ";
        //записываем первый символ в изменяемую строку
        char last = line.charAt(0);
        int count = 1;
        for (int i = 1; i < line.length(); i++) {
            //Если символ равен предыдущему, увеличиваем счётчик, если нет, добавляем значение
            char a = line.charAt(i);
            if (a == last) count++;
            else if (count != 1) {
                NewLine.append(last).append('*').append(count);
                count = 1;
            } else {
                NewLine.append(last);
            }
            // при любых условиях предыдущий приравнивается к текущему
            last = a;
        }
        return NewLine;
    }

    // №8: Функция, возвращающая рифмуются ли две строки, или нет. Две строки рифмуются, если последнее слово из каждого
    // предложения содержит одни и те же гласные
    public static boolean doesRhyme(String firstLine, String secondLine) {
        // Находим через регулярное выражение только глассные в обоих последних словах. Порядок глассных важен.
        String word1 = firstLine.substring(firstLine.lastIndexOf(' ')).toLowerCase().replaceAll("[^aeiouy]", "");
        String word2 = secondLine.substring(secondLine.lastIndexOf(' ')).toLowerCase().replaceAll("[^aeiouy]", "");
        return word2.equals(word1);
    }

    //9 включение подстроки на 3 и 2 подряд одинаковых символа в первом и втором натуральном числе соответственно
    public static boolean trouble(long firstNumber, long secondNumber) {
        //проверяем содержит ли строка одну из 10 комбинаций
        String first = Long.toString(firstNumber);
        String second = Long.toString(secondNumber);
        for (int i = 1000; i < 2000; i += 111) if (first.contains(Integer.toString(i % 1000)) && second.contains(Integer.toString(i % 100))) return true;
        return false;
    }

    //10 книги
    public static int countUniqueBooks(String line, char marker) {
        // Hashset хранит только уникальные эллементы
        Set<Character> characters = new HashSet<>();
        boolean flag = false;
        // если встречен первый разделитель, пока не будет встречен второй, все символы буду добавляться в set
        for (int i = 0; i < line.length(); i++) {
            char currentCharacter = line.charAt(i);
            if (currentCharacter == marker) {
                flag = !flag;
            } else if (flag) characters.add(currentCharacter);
        }
        return characters.size();
    }
}

