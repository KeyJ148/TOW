package tow.engine.implementation;

public interface ServerInterface {

    void init(); //Engine: Инициализация сервера сразу после создания сокета (Цикл подключения ещё не запущен, но сокет существует)
    void startProcessingData(); //Engine: Инициализация сервера перед запуском главного цикла, после подключения всех игроков
    void update(long delta); //Engine: Выполняется каждый степ перед обработкой сообщений

}
