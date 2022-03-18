package ru.ubrr.knutarev.CloneDemo;

public class Runner {

    public static void main(String[] args) {

        Shape[] shapes = {
                new Circle(4.0),
                new Ellipse(2.0,6.0),
                new Triangle(4.0,5.0,6.0),
                new IssoscelesTriangle(4.0,5.0),
                new EquilaterialTriangle(2.0,4.0),
                new RightTriangle(4.0,5.0,6.0),
                new Tetragon(4.0,5.0,30.0),
                new Parallelogramm(5.0,6.0),
                new Rectangle(4.0,5.0),
                new Square(2.0)
        };

        //Общая сумма площадей
        double sumS = 0;
        for (Shape shape : shapes) {
            sumS += shape.getArea();
        }
        System.out.println("Сумма площадей = "+sumS);




        //Клонирование фигур для которых есть такая возможность

        for (int i = 0; i < shapes.length; i++) {
            if(shapes[i] instanceof CloneableShape) {
                CloneableShape shapesClone = (CloneableShape)shapes[i];
                shapes[i] = (Shape)shapesClone.clone(2);
            }
        }

        //Вывод реестра фигур
        System.out.println("Список фигур:");
        for (int i = 0; i < shapes.length; i++) {
            System.out.println("ИД " + shapes[i].id + ", площадь " + shapes[i].getArea() + ", цвет " + shapes[i].color + ", тип фигуры " + shapes[i].getClass().getSimpleName());
        }

        //Прирост площади
        double sumS_new = 0;
        for (Shape shape : shapes) {
            sumS_new += shape.getArea();
        }
        System.out.println("Прирост площади составил: " + (sumS_new - sumS));


        //Кол-во фигур по цветам
        System.out.println("Кол-во фигур по цветам:");
        int colorNum = 0;
        int colorNumCircle = 0;
        int colorNumTriangle = 0;
        int colorNumTetragon = 0;

        for (Colors c : Colors.values()) {
            for (int i = 0; i < shapes.length; i++) {
                if(shapes[i].color.equals(c.toString())) {
                    colorNum++;
                    if(shapes[i] instanceof Circle) colorNumCircle++;
                    if(shapes[i] instanceof Triangle) colorNumTriangle++;
                    if(shapes[i] instanceof Tetragon) colorNumTetragon++;
                }
            }
            System.out.println(c + ": всего " + colorNum + ", круглых " + colorNumCircle + ", треугольных " + colorNumTriangle + ", четырехугольных " + colorNumTetragon);
            colorNum = 0;
            colorNumCircle = 0;
            colorNumTriangle = 0;
            colorNumTetragon = 0;
        }
    }
}

