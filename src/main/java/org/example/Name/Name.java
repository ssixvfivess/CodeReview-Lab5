package org.example.Name;

import java.util.Objects;

/**
 * Класс для представления полного имени человека (фамилия, имя, отчество).
 * Поддерживает различные форматы вывода и проверку валидности имени.
 */
public final class Name {
    private final String lastName;
    private final String firstName;
    private final String middleName;

    /**
     * Создает объект имени.
     * @param lastName фамилия (может быть null или пустой)
     * @param firstName имя (может быть null или пустой)
     * @param middleName отчество (может быть null или пустой)
     * @throws IllegalArgumentException если все компоненты имени null или пустые
     */
    public Name(String firstName, String lastName, String middleName) {
        validateNameComponents(firstName, lastName, middleName);
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }

    /**
     * Проверяет, что хотя бы один компонент имени не пустой.
     */
    private void validateNameComponents(String... components) {
        boolean allEmpty = true;
        for (String component : components) {
            if (component != null && !component.trim().isEmpty()) {
                allEmpty = false;
                break;
            }
        }
        if (allEmpty) {
            throw new IllegalArgumentException("Хотя бы один компонент имени должен быть не пустым");
        }
    }

    /**
     * Возвращает имя в формате "Фамилия Имя Отчество".
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        appendIfNotEmpty(sb, lastName);
        appendIfNotEmpty(sb, firstName);
        appendIfNotEmpty(sb, middleName);
        return sb.toString().trim();
    }

    private void appendIfNotEmpty(StringBuilder sb, String component) {
        if (component != null && !component.isEmpty()) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(component);
        }
    }

    // Геттеры
    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(lastName, name.lastName) &&
                Objects.equals(firstName, name.firstName) &&
                Objects.equals(middleName, name.middleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, middleName);
    }
}