package org.example.Classes;

import org.example.Interfaces.FractionOperations;
import java.util.Scanner;


/**
 * Главный класс приложения, предоставляющий пользовательский интерфейс для работы с различными функциями.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Точка входа в приложение.
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        runApplication();
    }

    /**
     * Основной цикл приложения, обрабатывающий пользовательский ввод.
     */
    private static void runApplication() {
        while (true) {
            displayMenu();
            int choice = getIntInput("Введите число от 0 до 7: ", 0, 7);

            if (choice == 0) {
                System.out.println("Выход из программы.");
                return;
            }

            handleUserChoice(choice);
        }
    }

    /**
     * Отображает главное меню приложения.
     */
    private static void displayMenu() {
        System.out.println("Выберите действие:");
        System.out.println("0. Выход");
        System.out.println("1. Работа с дробями 1 ");
        System.out.println("2. Работа с дробями 2 ");
        System.out.println("3. Работа с дробями 3" );
        System.out.println("4. Работа с дробями 4");
        System.out.println("5. Создать имена (расширенная)");
        System.out.println("6. Создать секрет");
        System.out.println("7. Решение готовых уравнений");
    }

    /**
     * Обрабатывает выбор пользователя.
     * @param choice выбранный пункт меню
     */
    private static void handleUserChoice(int choice) {
        switch (choice) {
            case 1 -> workWithFractions(FractionType.TYPE1);
            case 2 -> workWithFractions(FractionType.TYPE2);
            case 3 -> workWithFractions(FractionType.TYPE3);
            case 4 -> workWithFractions(FractionType.TYPE4);
            case 5 -> createNames();
            case 6 -> createSecret();
            case 7 -> performAdditions();
            default -> System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    /**
     * Перечисление типов дробей для унификации обработки.
     */
    private enum FractionType {
        TYPE1, TYPE2, TYPE3, TYPE4
    }

    /**
     * Выполняет операции с дробями указанного типа
     * @param type тип дроби из перечисления FractionType
     */
    private static void workWithFractions(FractionType type) {
        System.out.println("Работа с дробями:");

        // Создаем дроби конкретного типа
        switch (type) {
            case TYPE1 -> {
                Fraction1 f1 = (Fraction1) createFraction(type, "первой");
                Fraction1 f2 = (Fraction1) createFraction(type, "второй");
                Fraction1 f3 = (Fraction1) createFraction(type, "третьей");
                performFractionOperations(f1, f2, f3);
            }
            case TYPE2 -> {
                Fraction2 f1 = (Fraction2) createFraction(type, "первой");
                Fraction2 f2 = (Fraction2) createFraction(type, "второй");
                Fraction2 f3 = (Fraction2) createFraction(type, "третьей");
                performFractionOperations(f1, f2, f3);
            }
            case TYPE3 -> {
                Fraction3 f1 = (Fraction3) createFraction(type, "первой");
                Fraction3 f2 = (Fraction3) createFraction(type, "второй");
                Fraction3 f3 = (Fraction3) createFraction(type, "третьей");
                performFractionOperations(f1, f2, f3);
                displayNumberValues(f3);
            }
            case TYPE4 -> {
                Fraction4 f1 = (Fraction4) createFraction(type, "первой");
                Fraction4 f2 = (Fraction4) createFraction(type, "второй");
                Fraction4 f3 = (Fraction4) createFraction(type, "третьей");
                performFractionOperations(f1, f2, f3);
                displayFractionComparisons(f1, f2, f3);
            }
        }
    }

    /**
     * Создает дробь указанного типа.
     * @param type тип дроби
     * @param ordinal порядковое название дроби (первая, вторая и т.д.)
     * @return созданная дробь
     */
    private static FractionOperations<?> createFraction(FractionType type, String ordinal) {
        int numerator = getIntInput("Введите числитель для " + ordinal + " дроби: ", Integer.MIN_VALUE, Integer.MAX_VALUE);
        int denominator = getIntInput("Введите знаменатель для " + ordinal + " дроби: ", 1, Integer.MAX_VALUE);

        return switch (type) {
            case TYPE1 -> new Fraction1(numerator, denominator);
            case TYPE2 -> new Fraction2(numerator, denominator);
            case TYPE3 -> new Fraction3(numerator, denominator);
            case TYPE4 -> new Fraction4(numerator, denominator);
        };
    }

    /**
     * Выполняет основные операции с дробями.
     * @param f1 первая дробь
     * @param f2 вторая дробь
     * @param f3 третья дробь
     */
    private static <T extends FractionOperations<T>> void performFractionOperations(T f1, T f2, T f3) {
        System.out.println(f1 + " * " + f2 + " = " + f1.multiply(f2));
        System.out.println(f1 + " + " + f2 + " = " + f1.add(f2));
        System.out.println(f1 + " / " + f2 + " = " + f1.divide(f2));
        System.out.println(f1 + " - " + f2 + " = " + f1.subtract(f2));

        T result = f1.add(f2).divide(f3).subtract(5);
        System.out.println("f1.add(f2).divide(f3).subtract(5) = " + result);
    }

    /**
     * Отображает числовые значения дроби (для Fraction3).
     * @param fraction дробь для отображения значений
     */
    private static void displayNumberValues(Fraction3 fraction) {
        System.out.println("intValue: " + fraction.intValue());
        System.out.println("longValue: " + fraction.longValue());
        System.out.println("floatValue: " + fraction.floatValue());
        System.out.println("doubleValue: " + fraction.doubleValue());
    }

    /**
     * Отображает сравнения дробей (для Fraction4).
     * @param f1 первая дробь
     * @param f2 вторая дробь
     * @param f3 третья дробь
     */
    private static void displayFractionComparisons(Fraction4 f1, Fraction4 f2, Fraction4 f3) {
        System.out.println("f1.equals(f2): " + f1.equals(f2));
        System.out.println("f1.equals(f3): " + f1.equals(f3));

        System.out.println("f1.hashCode(): " + f1.hashCode());
        System.out.println("f2.hashCode(): " + f2.hashCode());
        System.out.println("f3.hashCode(): " + f3.hashCode());
    }

    /**
     * Создает и отображает три имени.
     */
    private static void createNames() {
        System.out.println("Создание имен:");
        Name[] names = new Name[3];

        for (int i = 0; i < 3; i++) {
            String firstName = getStringInput("Введите ИМЯ для " + (i+1) + " имени: ");
            String lastName = getStringInput("Введите ФАМИЛИЮ для " + (i+1) + " имени: ");
            String middleName = getStringInput("Введите ОТЧЕСТВО для " + (i+1) + " имени: ");
            names[i] = new Name(firstName, lastName, middleName);
        }

        for (Name name : names) {
            System.out.println(name);
        }
    }

    /**
     * Создает и управляет секретом.
     */
    private static void createSecret() {
        System.out.println("Создание нового секрета:");
        String keeperName = getStringInput("Введите имя хранителя: ");
        String secretText = getStringInput("Введите текст секрета: ");
        Secret secret = new Secret(keeperName, secretText);

        while (true) {
            displaySecretMenu();
            int choice = getIntInput("Введите число от 0 до 5: ", 0, 5);

            if (choice == 0) {
                System.out.println("Выход из создания секрета.");
                return;
            }

            handleSecretChoice(secret, choice);
        }
    }

    /**
     * Отображает меню работы с секретом.
     */
    private static void displaySecretMenu() {
        System.out.println("Выберите действие:");
        System.out.println("0. Выход");
        System.out.println("1. Передать секрет другому человеку");
        System.out.println("2. Узнать порядок хранителя");
        System.out.println("3. Узнать количество людей, узнавших секрет после текущего хранителя");
        System.out.println("4. Узнать имя N-го человека, узнавшего секрет");
        System.out.println("5. Узнать разницу в количестве символов текста секрета с N-ым человеком");
    }

    /**
     * Обрабатывает выбор пользователя для работы с секретом.
     * @param secret текущий секрет
     * @param choice выбранный пункт меню
     */
    private static void handleSecretChoice(Secret secret, int choice) {
        switch (choice) {
            case 1 -> {
                String newKeeperName = getStringInput("Введите имя нового хранителя: ");
                secret = secret.tellSecretTo(newKeeperName);
            }
            case 2 -> System.out.println("Порядок хранителя: " + secret.getKeeperOrder());
            case 3 -> System.out.println("Количество людей, узнавших секрет после текущего хранителя: " +
                    secret.getNumberOfPeopleKnowAfter());
            case 4 -> {
                int n = getIntInput("Введите N (положительное для следующего, отрицательное для предыдущего): ",
                        Integer.MIN_VALUE, Integer.MAX_VALUE);
                try {
                    System.out.println("Имя N-го человека, узнавшего секрет: " + secret.getKeeperName(n));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
            }
            case 5 -> {
                int m = getIntInput("Введите N (положительное для следующего, отрицательное для предыдущего): ",
                        Integer.MIN_VALUE, Integer.MAX_VALUE);
                try {
                    System.out.println("Разница в количестве символов текста секрета с N-ым человеком: " +
                            secret.getTextDifferenceWith(m));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /**
     * Выполняет сложение числовых значений.
     */
    private static void performAdditions() {
        System.out.println("Сложение числовых значений:");

        Number[][] valuesSets = {
                {2, new Fraction3(3, 5), 2.3},
                {3.6, new Fraction3(49, 12), 3, new Fraction3(3, 2)},
                {new Fraction3(1, 3), 1}
        };

        String[] descriptions = {
                "2 + 3/5 + 2.3",
                "3.6 + 49/12 + 3 + 3/2",
                "1/3 + 1"
        };

        for (int i = 0; i < valuesSets.length; i++) {
            System.out.println(descriptions[i] + " = " + sum(valuesSets[i]));
        }
    }

    /**
     * Суммирует массив числовых значений.
     * @param values массив чисел для суммирования
     * @return результат суммирования
     */
    private static double sum(Number[] values) {
        double sum = 0;
        for (Number value : values) {
            sum += value.doubleValue();
        }
        return sum;
    }

    /**
     * Получает целочисленный ввод от пользователя с проверкой диапазона.
     * @param prompt приглашение для ввода
     * @param min минимальное допустимое значение
     * @param max максимальное допустимое значение
     * @return введенное пользователем число
     */
    private static int getIntInput(String prompt, int min, int max) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("Ошибка: Число должно быть от %d до %d. Попробуйте снова.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите только целое число. Попробуйте снова.");
            }
        }
    }

    /**
     * Получает строковый ввод от пользователя с проверкой
     * @param prompt приглашение для ввода
     * @return введенную строку (только буквы, длина 2-15 символов)
     */
    private static String getStringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            // Проверка на пустую строку
            if (input.isEmpty()) {
                System.out.println("Ошибка: Ввод не может быть пустым. Попробуйте снова.");
                continue;
            }

            // Проверка, что строка содержит только буквы (включая кириллицу)
            if (!input.matches("[a-zA-Zа-яА-ЯёЁ\\s]+")) {
                System.out.println("Ошибка: Введите только буквы (без цифр и символов). Попробуйте снова.");
                continue;
            }

            // Проверка длины строки
            if (input.length() < 2 || input.length() > 15) {
                System.out.println("Ошибка: Слово должно быть от 2 до 15 символов. Попробуйте снова.");
                continue;
            }

            return input;
        }
    }
}