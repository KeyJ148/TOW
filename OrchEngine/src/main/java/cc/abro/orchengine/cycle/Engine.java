package cc.abro.orchengine.cycle;

import cc.abro.orchengine.analysis.Analyzer;
import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.init.Finalizer;
import cc.abro.orchengine.init.Loader;
import org.lwjgl.glfw.GLFW;

@EngineService
public class Engine {

    private final Update update;
    private final Render render;
    private final Analyzer analyzer;

    private boolean isRun = true;

    public Engine(Update update, Render render, Analyzer analyzer) {
        this.update = update;
        this.render = render;
        this.analyzer = analyzer;
    }

    public void run() {
        render.showWindow();
        while (isRun) {
            //Цикл Update
            analyzer.startUpdate();
            update.loop(); //Обновление состояния у всех объектов в активной локации
            analyzer.endUpdate();

            //Цикл Render
            analyzer.startRender();
            render.loop(); //Отрисовка всех объектов в активной локациии
            analyzer.endRender();

            //Пауза для синхронизации кадров
            analyzer.startSync();
            render.vSync(); //Вертикальная синхронизация
            render.fpsSync(); //Ограничитель FPS (если вертикальная синхронизация отключена или не сработала)
            analyzer.endSync();

            //Если пользователь закрыл окно, то запускаем процесс остановки движка
            if (GLFW.glfwWindowShouldClose(render.getWindowID())) {
                stop();
            }
        }
    }

    public void stop() {
        isRun = false;
    }

    /**
     * Завершение программы. Вызывается из {@link Loader}.
     * Будет вызван callback для освобождения ресурсов.
     * Callback был зарегистрирован в {@link Finalizer#registryShutdownCallback()}.
     * Callback вызовет функцию завершения {@link Finalizer#stopServicesAndCloseResources()}.
     */
    public void stoppingCallback() {
        System.exit(0);
    }
}
