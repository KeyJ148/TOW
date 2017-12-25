package engine.inf;

import java.util.ArrayList;

public class InfMain {

    public ArrayList<Inf> infs = new ArrayList<>();

    public void update(){
        //Перебираем все объекты, если с ними взаимодействуют, то лочим обработчики мыши и клавиатуры
        //Перебирать начинаем с самого верхнего окна (Минимальная глубина)
        //Объекты в массиве рассположены по глубине, первый объект -- самый глубокий
        //Перебор массива осуществляется в обратном порядке, с самых верхних элементов
        for (int i=infs.size()-1; i >= 0; i--){
            Inf inf = infs.get(i);
            if (!inf.delete) inf.update();
            else infs.remove(i);
        }
    }

    public void draw(){
        for (Inf inf : infs) inf.draw();
    }

}
