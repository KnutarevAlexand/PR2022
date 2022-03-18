package ru.ubrr.knutarev.LambdaException;

class EmptyArrayException extends Exception {
    EmptyArrayException() {
        super("Массив пуст");
    }
}
