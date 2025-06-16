package org.example.Fraction.Interfaces;


/**
 * Интерфейс для операций с дробями.
 * @param <T> тип дроби
 */
public interface FractionOperations<T> {
    T add(T other);
    T subtract(T other);
    T multiply(T other);
    T divide(T other);
    T subtract(int number);
}
