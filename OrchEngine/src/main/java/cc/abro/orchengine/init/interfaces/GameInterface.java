package cc.abro.orchengine.init.interfaces;

import cc.abro.orchengine.cycle.Render;

public interface GameInterface {

    default void init() {
    } //Engine: Инициализация игры перед запуском главного цикла

    default void update(long delta) {
    } //Engine: Выполняется каждый степ перед обновлением всех игровых объектов

    default void render() {
    } //Engine: Выполняется каждый степ перед перерисовкой всех игровых объектов

    default Render.Settings getRenderSettings() {
        return new Render.Settings(1280, 720, false, 120, 0, "OrchEngine");
    }
}
