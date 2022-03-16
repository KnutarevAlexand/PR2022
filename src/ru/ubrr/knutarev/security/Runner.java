package ru.ubrr.knutarev.security;

public class Runner {
    public static void main(String[] args) {
        SympleSecurityFunction ssf = new SympleSecurityFunction();
        ssf.info();
        System.out.println(ssf.encrypt("1qwerty"));
        ssf.trace("trace message"); //Реализация метода взята из интерфейса т.к. в классе нет описания

        SecurityFunction sf = ssf;  //Создание ссылки на призму др класса
        ScreenPrinter sp = ssf; //Создание ссылки на призму др класса
        ComplexSecurityFunction csf = ssf; //Создание ссылки на призму др класса

        System.out.println(sf.decrypt("encryptedData"));    //Использование функции др. интерфейса
        sp.printOnScreen();                                 //Использование функции др. интерфейса

        SecurityFunction.display();  //Вызов статической функции интерфейса

    }
}
