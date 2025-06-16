package org.example.Secret;

import java.util.*;

/**
 * Класс для представления секрета, который передается между людьми.
 * При каждой передаче текст секрета немного искажается.
 */
public final class Secret {
    private static final Random RANDOM = new Random();
    private static final int TEXT_MODIFICATION_RATE = 10;

    private final String text;
    private final String keeperName;
    private final List<String> keepersHistory;

    /**
     * Создает новый секрет.
     * @param keeperName имя первого хранителя секрета
     * @param text исходный текст секрета
     * @throws IllegalArgumentException если keeperName или text null/пустые
     */
    public Secret(String keeperName, String text) {
        validateInput(keeperName, text);
        this.text = text;
        this.keeperName = keeperName;
        this.keepersHistory = new ArrayList<>();
        this.keepersHistory.add(keeperName);
        printCreationMessage();
    }

    private Secret(String keeperName, String text, List<String> keepersHistory) {
        this.text = text;
        this.keeperName = keeperName;
        this.keepersHistory = new ArrayList<>(keepersHistory);
        this.keepersHistory.add(keeperName);
        printCreationMessage();
    }

    private void validateInput(String keeperName, String text) {
        if (keeperName == null || keeperName.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя хранителя не может быть пустым");
        }
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Текст секрета не может быть пустым");
        }
    }

    private void printCreationMessage() {
        System.out.printf("%s сказал(a) что %s%n", keeperName, text);
    }

    /**
     * Передает секрет новому хранителю.
     * @param newKeeperName имя нового хранителя
     * @return новый объект Secret с измененным текстом
     * @throws IllegalArgumentException если newKeeperName уже знал секрет
     */
    public Secret tellSecretTo(String newKeeperName) {
        Objects.requireNonNull(newKeeperName, "Имя нового хранителя не может быть null");

        if (keepersHistory.contains(newKeeperName)) {
            throw new IllegalArgumentException("Секрет уже был передан " + newKeeperName);
        }

        String modifiedText = modifyText(text);
        return new Secret(newKeeperName, modifiedText, keepersHistory);
    }

    private String modifyText(String originalText) {
        int modificationsCount = originalText.length() / TEXT_MODIFICATION_RATE;
        int actualModifications = RANDOM.nextInt(modificationsCount + 1);

        StringBuilder sb = new StringBuilder(originalText);
        for (int i = 0; i < actualModifications; i++) {
            char randomChar = (char) (RANDOM.nextInt(26) + 'a');
            int randomPosition = RANDOM.nextInt(sb.length() + 1);
            sb.insert(randomPosition, randomChar);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return keeperName + ": это секрет!";
    }

    /**
     * Возвращает порядковый номер текущего хранителя.
     */
    public int getKeeperOrder() {
        return keepersHistory.indexOf(keeperName) + 1;
    }

    /**
     * Возвращает количество людей, узнавших секрет после текущего хранителя.
     */
    public int getNumberOfPeopleKnowAfter() {
        return keepersHistory.size() - getKeeperOrder();
    }

    /**
     * Возвращает имя N-го хранителя относительно текущего.
     * @param n положительное число для следующих хранителей, отрицательное для предыдущих
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
     */
    public int getTextDifferenceWith(int n) {
        String otherKeeperName = getKeeperName(n);
        int otherTextLength = calculateTextLengthForKeeper(otherKeeperName);
        return Math.abs(text.length() - otherTextLength);
    }

    private int calculateTextLengthForKeeper(String keeper) {
        // Для оригинального хранителя - исходная длина текста
        if (keepersHistory.get(0).equals(keeper)) {
            return text.length() - countModifications(keeper);
        }
        return text.length();
    }

    private int countModifications(String keeper) {
        int keeperIndex = keepersHistory.indexOf(keeper);
        return keeperIndex * (text.length() / TEXT_MODIFICATION_RATE / 2);
    }

    /**
     * Возвращает неизменяемую историю хранителей.
     */
    public List<String> getKeepersHistory() {
        return Collections.unmodifiableList(keepersHistory);
    }
}