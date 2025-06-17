package ru.vyatkina.Secret;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Задание 2.2:
 * Класс для представления секрета, который может быть передан только одному человеку.
 * При передаче текст секрета незначительно искажается добавлением случайных символов.
 * Секрет хранит историю всех своих хранителей.
 */
public final class Secret {
    private static final Random RANDOM = new Random();
    private static final double TEXT_MODIFICATION_PERCENT = 0.1;

    private final String text;
    private final String keeperName;
    private final List<String> keepersHistory;

    /**
     * Создает новый секрет.
     * @param keeperName имя первого хранителя секрета (не может быть null или пустым)
     * @param text текст секрета (не может быть null или пустым)
     * @throws IllegalArgumentException если keeperName или text невалидны
     */
    public Secret(String keeperName, String text) {
        validateInput(keeperName, text);
        this.text = text;
        this.keeperName = keeperName;
        this.keepersHistory = new ArrayList<>();
        this.keepersHistory.add(keeperName);
        printCreationMessage();
    }

    /**
     * Внутренний конструктор для создания нового секрета при передаче.
     * @param keeperName имя нового хранителя
     * @param text модифицированный текст секрета
     * @param keepersHistory история предыдущих хранителей
     */
    private Secret(String keeperName, String text, List<String> keepersHistory) {
        this.text = text;
        this.keeperName = keeperName;
        this.keepersHistory = new ArrayList<>(keepersHistory);
        this.keepersHistory.add(keeperName);
        printCreationMessage();
    }

    /**
     * Проверяет валидность входных параметров.
     * @throws IllegalArgumentException если параметры невалидны
     */
    private void validateInput(String keeperName, String text) {
        if (keeperName == null || keeperName.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя хранителя не может быть пустым");
        }
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Текст секрета не может быть пустым");
        }
    }

    /**
     * Выводит сообщение о создании/передаче секрета.
     */
    private void printCreationMessage() {
        System.out.printf("%s сказал(a) что %s%n", keeperName, text);
    }

    /**
     * Передает секрет новому хранителю.
     * @param newKeeperName имя нового хранителя (не может быть null)
     * @return новый объект Secret с измененным текстом
     * @throws IllegalArgumentException если newKeeperName уже знал секрет
     */
    public Secret tellSecretTo(String newKeeperName) {
        Objects.requireNonNull(newKeeperName, "Имя нового хранителя не может быть null");

        if (keepersHistory.contains(newKeeperName)) {
            throw new IllegalArgumentException(
                    String.format("%s уже знает этот секрет", newKeeperName));
        }

        String modifiedText = modifyText(text);
        return new Secret(newKeeperName, modifiedText, keepersHistory);
    }

    /**
     * Модифицирует текст секрета добавлением случайных символов.
     * @param originalText исходный текст
     * @return модифицированный текст
     */
    private String modifyText(String originalText) {
        int maxModifications = (int) (originalText.length() * TEXT_MODIFICATION_PERCENT);
        int modificationsCount = RANDOM.nextInt(maxModifications + 1);

        StringBuilder sb = new StringBuilder(originalText);
        for (int i = 0; i < modificationsCount; i++) {
            char randomChar = (char) (RANDOM.nextInt(26) + 'a');
            int randomPosition = RANDOM.nextInt(sb.length() + 1);
            sb.insert(randomPosition, randomChar);
        }
        return sb.toString();
    }

    /**
     * Возвращает строковое представление секрета.
     * @return строка в формате "Имя: это секрет!"
     */
    @Override
    public String toString() {
        return keeperName + ": это секрет!";
    }

    /**
     * Возвращает порядковый номер текущего хранителя.
     * @return номер хранителя (начиная с 1)
     */
    public int getKeeperOrder() {
        return keepersHistory.indexOf(keeperName) + 1;
    }

    /**
     * Возвращает количество людей, узнавших секрет после текущего хранителя.
     * @return количество последующих хранителей
     */
    public int getNumberOfPeopleKnowAfter() {
        return keepersHistory.size() - getKeeperOrder();
    }

    /**
     * Возвращает имя N-го хранителя относительно текущего.
     * @param n положительное число для следующих хранителей, отрицательное для предыдущих
     * @return имя запрошенного хранителя
     * @throws IndexOutOfBoundsException если индекс выходит за границы
     */
    public String getKeeperName(int n) {
        int currentIndex = keepersHistory.indexOf(keeperName);
        int targetIndex = currentIndex + n;

        if (targetIndex < 0 || targetIndex >= keepersHistory.size()) {
            throw new IndexOutOfBoundsException("Не существует хранителя с таким индексом");
        }

        return keepersHistory.get(targetIndex);
    }

    /**
     * Возвращает разницу в длине текста секрета с N-ым хранителем.
     * @param n смещение относительно текущего хранителя
     * @return абсолютная разница в длине текста
     */
    public int getTextDifferenceWith(int n) {
        String otherKeeperName = getKeeperName(n);
        int otherTextLength = calculateTextLengthForKeeper(otherKeeperName);
        return Math.abs(text.length() - otherTextLength);
    }

    /**
     * Вычисляет предполагаемую длину текста для указанного хранителя.
     */
    private int calculateTextLengthForKeeper(String keeper) {
        int keeperIndex = keepersHistory.indexOf(keeper);
        int modifications = keeperIndex * (int)(text.length() * TEXT_MODIFICATION_PERCENT / 2);
        return text.length() + modifications;
    }

    /**
     * Возвращает неизменяемую историю хранителей.
     * @return список имен хранителей в порядке получения секрета
     */
    public List<String> getKeepersHistory() {
        return Collections.unmodifiableList(keepersHistory);
    }
}