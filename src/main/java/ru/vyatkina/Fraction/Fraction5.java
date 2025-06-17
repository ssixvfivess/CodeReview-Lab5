package ru.vyatkina.Fraction;

import ru.vyatkina.Fraction.Interfaces.FractionOperations;

import java.util.Objects;

/**
 * Класс для представления математических дробей с целыми числителем и знаменателем.
 * Поддерживает основные арифметические операции: сложение, вычитание, умножение и деление.
 * Реализует интерфейс Cloneable для поддержки клонирования.
 */
public class Fraction5 implements Cloneable, FractionOperations<Fraction5> {
    private final int numerator;
    private final int denominator;

    /**
     * Создает новую дробь.
     *
     * @param numerator числитель дроби
     * @param denominator знаменатель дроби (не может быть нулем)
     * @throws IllegalArgumentException если знаменатель равен нулю
     */
    public Fraction5(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Знаменатель не может быть равен нулю.");
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * Создает копию объекта дроби.
     *
     * @return копию текущей дроби
     * @throws CloneNotSupportedException если клонирование не поддерживается
     */
    @Override
    public Fraction5 clone() throws CloneNotSupportedException {
        try {
            return (Fraction5) super.clone();
        } catch (CloneNotSupportedException e) {
            // В случае ошибки создаем новый объект вручную
            return new Fraction5(this.numerator, this.denominator);
        }
    }

    /**
     * Складывает текущую дробь с другой дробью.
     *
     * @param other другая дробь для сложения
     * @return новая дробь - результат сложения
     * @throws NullPointerException если other равен null
     */
    public Fraction5 add(Fraction5 other) {
        Objects.requireNonNull(other, "Другая дробь не может быть null");
        int newNumerator = this.numerator * other.denominator + other.numerator * this.denominator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction5(newNumerator, newDenominator);
    }

    /**
     * Вычитает другую дробь из текущей дроби.
     *
     * @param other другая дробь для вычитания
     * @return новая дробь - результат вычитания
     * @throws NullPointerException если other равен null
     */
    public Fraction5 subtract(Fraction5 other) {
        Objects.requireNonNull(other, "Другая дробь не может быть null");
        int newNumerator = this.numerator * other.denominator - other.numerator * this.denominator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction5(newNumerator, newDenominator);
    }

    /**
     * Умножает текущую дробь на другую дробь.
     *
     * @param other другая дробь для умножения
     * @return новая дробь - результат умножения
     * @throws NullPointerException если other равен null
     */
    public Fraction5 multiply(Fraction5 other) {
        Objects.requireNonNull(other, "Другая дробь не может быть null");
        int newNumerator = this.numerator * other.numerator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction5(newNumerator, newDenominator);
    }

    /**
     * Делит текущую дробь на другую дробь.
     *
     * @param other другая дробь для деления
     * @return новая дробь - результат деления
     * @throws NullPointerException если other равен null
     * @throws IllegalArgumentException если other представляет нулевую дробь
     */
    public Fraction5 divide(Fraction5 other) {
        Objects.requireNonNull(other, "Другая дробь не может быть null");
        if (other.numerator == 0) {
            throw new IllegalArgumentException("Деление на ноль невозможно.");
        }
        int newNumerator = this.numerator * other.denominator;
        int newDenominator = this.denominator * other.numerator;
        return new Fraction5(newNumerator, newDenominator);
    }

    /**
     * Вычитает целое число из текущей дроби.
     *
     * @param number целое число для вычитания
     * @return новая дробь - результат вычитания
     */
    public Fraction5 subtract(int number) {
        return subtract(new Fraction5(number, 1));
    }

    /**
     * Возвращает строковое представление дроби в формате "числитель/знаменатель".
     *
     * @return строковое представление дроби
     */
    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }

    /**
     * Возвращает числитель дроби.
     *
     * @return числитель дроби
     */
    public int getNumerator() {
        return numerator;
    }

    /**
     * Возвращает знаменатель дроби.
     *
     * @return знаменатель дроби
     */
    public int getDenominator() {
        return denominator;
    }

    /**
     * Сравнивает текущую дробь с другим объектом на равенство.
     *
     * @param obj объект для сравнения
     * @return true если дроби равны, false в противном случае
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Fraction5 fraction = (Fraction5) obj;
        return numerator * fraction.denominator == fraction.numerator * denominator;
    }

    /**
     * Возвращает хэш-код дроби.
     *
     * @return хэш-код дроби
     */
    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }
}
