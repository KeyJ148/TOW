package cc.abro.orchengine.init.interfaces;

public interface ServerInterface {

    default void init() {
    } //Engine: Инициализация сервера сразу после создания сокета (Цикл подключения ещё не запущен, но сокет существует)

    default void startProcessingData() {
    } //Engine: Инициализация сервера перед запуском главного цикла, после подключения всех игроков

    default void update(long delta) {
    } //Engine: Выполняется каждый степ перед обработкой сообщений

}
