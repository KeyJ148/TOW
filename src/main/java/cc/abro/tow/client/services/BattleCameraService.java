package cc.abro.tow.client.services;

import cc.abro.orchengine.context.GameService;

@GameService
public class BattleCameraService { //TODO сделать в виде компонента, который будет создаваться в BattleLocation, т.к. управление необходимо только в BattleLocation
    /*
    //Если в данный момент камера установлена на этот объект //TODO это из класса Tank
          if (locationManager.getActiveLocation().getCamera().hasFollowObject() &&
                locationManager.getActiveLocation().getCamera().getFollowObject().orElse(null) == cameraComponent) {
            //Выбираем живого врага с инициализированной камерой, переносим камеру туда
            for (Map.Entry<Integer, Enemy> entry : Context.getService(ClientData.class).enemy.entrySet()) {
                if (entry.getValue().isAlive()) {
                    entry.getValue().setLocationCameraToThisObject();
                    break;
                }
            }
        }

    //В контроллер
    //Переключение камер после смерти //TODO в соответствующий сервис
    List<KeyEvent<?>> keyboardEvents = getGameObject().getLocation().getGuiLocationFrame().getKeyboard().getEventHistory().getList();
        for (KeyEvent<?> event : keyboardEvents) {
        if (event.getAction() == GLFW_PRESS) {// Клавиша нажата
            switch (event.getKey()) {
                    case GLFW_KEY_LEFT -> cameraToNextEnemy();
                    case GLFW_KEY_RIGHT -> cameraToPrevEnemy();

    //TODO разобрать и реализовать
    private void cameraToNextEnemy() {
        if (!locationManager.getActiveLocation().getCamera().hasFollowObject()){
            return;
        }
        List<Enemy> enemies = getEnemiesWithCamera();
        List<Enemy> enemiesDouble = Stream.concat(enemies.stream(), enemies.stream())
                .collect(Collectors.toList());
        int pos = getEnemyWithCameraPos(enemies);
        if (pos == -1){
            return;
        }

        for (int i = pos+1; i < enemiesDouble.size(); i++) {
            Enemy enemy = enemiesDouble.get(i);
            if (enemy.alive) {
                enemy.setLocationCameraToThisObject();
                break;
            }
        }
    }

    private void cameraToPrevEnemy() {
        if (!locationManager.getActiveLocation().getCamera().hasFollowObject()){
            return;
        }
        List<Enemy> enemies = getEnemiesWithCamera();
        List<Enemy> enemiesDouble = Stream.concat(enemies.stream(), enemies.stream())
                .collect(Collectors.toList());
        int pos = getEnemyWithCameraPos(enemies)+enemies.size();
        if (pos == -1){
            return;
        }

        for (int i = pos-1; i >= 0 ; i--) {
            Enemy enemy = enemiesDouble.get(i);
            if (enemy.alive) {
                enemy.setLocationCameraToThisObject();
                break;
            }
        }
    }

    private List<Enemy> getEnemiesWithCamera() {
        return Context.getService(ClientData.class).enemy.entrySet().stream()
                .filter(e -> e.getValue().alive)
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    private int getEnemyWithCameraPos(List<Enemy> enemies) {
        int pos = -1;
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).locationCameraIsThisObject()) {
                pos = i;
                break;
            }
        }
        return pos;
    }
     */
}
