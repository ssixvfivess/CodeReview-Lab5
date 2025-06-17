package ru.vyatkina.Name;

import java.util.Objects;


/**
 * Задание 1.7:
 * Класс для представления полного имени человека (фамилия, имя, отчество).
 * Поддерживает различные форматы вывода и проверку валидности имени.
 * Имя является неизменяемым объектом.
 */
public final class Name {
    private final String lastName;
    private final String firstName;
    private final String middleName;

    /**
     * Создает объект имени.
     * @param firstName имя (может быть null или пустой строкой)
     * @param lastName фамилия (может быть null или пустой строкой)
     * @param middleName отчество (может быть null или пустой строкой)
     * @throws IllegalArgumentException если все параметры null или пустые строки
     */
    public Name(String firstName, String lastName, String middleName) {
        validateAtLeastOneComponentPresent(firstName, lastName, middleName);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    /**
     * Проверяет, что хотя бы один из компонентов имени не является null или пустой строкой.
     * @param components компоненты имени для проверки
     * @throws IllegalArgumentException если все компоненты null или пустые строки
     */
    private void validateAtLeastOneComponentPresent(String... components) {
        for (String component : components) {
            if (component != null && !component.trim().isEmpty()) {
                return;
            }
        }
        throw new IllegalArgumentException("Хотя бы один компонент имени должен быть не пустым");
    }

    /**
     * Возвращает строковое представление имени в формате "Фамилия Имя Отчество".
     * Пропускает пустые компоненты.
     * @return строковое представление имени
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        appendIfNotEmpty(sb, lastName);
        appendIfNotEmpty(sb, firstName);
        appendIfNotEmpty(sb, middleName);
        return sb.toString().trim();
    }

    /**
     * Добавляет компонент имени к StringBuilder, если он не пустой.
     * @param sb StringBuilder для сборки результата
     * @param component компонент имени для добавления
     */
    private void appendIfNotEmpty(StringBuilder sb, String component) {
        if (component != null && !component.isEmpty()) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(component);
        }
    }

    /**
     * Возвращает фамилию.
     * @return фамилия (может быть null)
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Возвращает имя.
     * @return имя (может быть null)
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Возвращает отчество.
     * @return отчество (может быть null)
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Сравнивает этот объект с другим на равенство.
     * @param o объект для сравнения
     * @return true если объекты равны, false в противном случае
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(lastName, name.lastName) &&
                Objects.equals(firstName, name.firstName) &&
                Objects.equals(middleName, name.middleName);
    }

    /**
     * Возвращает хэш-код объекта.
     * @return хэш-код
     */
    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, middleName);
    }
}