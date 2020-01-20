package tow.engine.cycle;

public class FPSLimit {

    private int fpsLimit;
    private long variableYieldTime, lastTime;

    public FPSLimit(int fpsLimit){
        this.fpsLimit = fpsLimit;
    }

    //Для работы ограничителя кадров эту функцию необходимо вызывать каждый кадр
    public void sync(){
        if (fpsLimit <= 0) return;

        long sleepTime = 1000000000 / fpsLimit;
        long yieldTime = Math.min(sleepTime, variableYieldTime + sleepTime % (1000000));
        long overSleep = 0;

        do {
            long t = System.nanoTime() - lastTime;

            if (t < sleepTime - yieldTime) {
                try {Thread.sleep(1); } catch (InterruptedException e) {}
            } else if (t <= sleepTime) {
                Thread.yield();
            } else {
                overSleep = t - sleepTime;
            }
        } while (overSleep == 0);

        lastTime = System.nanoTime() - Math.min(overSleep, sleepTime);

        if (overSleep > variableYieldTime) {
            variableYieldTime = Math.min(variableYieldTime + 200*1000, sleepTime);
        } else if (overSleep < variableYieldTime - 200*1000) {
            variableYieldTime = Math.max(variableYieldTime - 2*1000, 0);
        }
    }
}
