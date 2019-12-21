package tow.engine2.implementation;

public interface GameInterface {

    void init(); //Engine: Инициализация игры перед запуском главного цикла
    void update(long delta); //Engine: Выполняется каждый степ перед обновлением всех игровых объектов
    void render(); //Engine: Выполняется каждый степ перед перерисовкой всех игровых объектов

}
