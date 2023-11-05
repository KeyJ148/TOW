package cc.abro.tow.client;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.gameobject.LocationManager;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.init.interfaces.NetGameReadInterface;
import cc.abro.orchengine.net.client.Message;
import cc.abro.orchengine.net.client.PingChecker;
import cc.abro.orchengine.net.client.tcp.TCPControl;
import cc.abro.orchengine.resources.audios.AudioService;
import cc.abro.orchengine.resources.audios.AudioStorage;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.tow.client.map.BattleLocation;
import cc.abro.tow.client.map.objects.Box;
import cc.abro.tow.client.map.objects.destroyed.DestroyedMapObject;
import cc.abro.tow.client.map.specification.MapSpecification;
import cc.abro.tow.client.map.specification.MapSpecificationLoader;
import cc.abro.tow.client.services.BattleStatisticService;
import cc.abro.tow.client.settings.GameSettingsService;
import cc.abro.tow.client.settings.Settings;
import cc.abro.tow.client.settings.SettingsService;
import cc.abro.tow.client.tanks.enemy.EnemyTank;
import cc.abro.tow.client.tanks.equipment.EquipmentService;
import cc.abro.tow.client.tanks.equipment.bullet0.EnemyBullet;
import cc.abro.tow.client.tanks.player.PlayerTank;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Vector;

@GameService
@RequiredArgsConstructor
public class NetGameRead implements NetGameReadInterface {

	private final AudioService audioService;
	private final AudioStorage audioStorage;
	private final PingChecker pingChecker;
	private final TCPControl tcpControl;
	private final SpriteStorage spriteStorage;
	private final LocationManager locationManager;
	private final ClientData clientData;
	private final SettingsService settingsService;
	private final BattleStatisticService battleStatisticService;
	private final GameSettingsService gameSettingsService;
	private final EquipmentService equipmentService;

	@Override
	public void readTCP(Message message) {
		switch (message.type) {
			case 1 -> take1(message.data);
			case 3 -> take3(message.data);
			case 4 -> take4(message.data);
			case 5 -> take5(message.data);
			case 7 -> take7(message.data);
			case 8 -> take8(message.data);
			case 10 -> take10(message.data);
			case 11 -> take11(message.data);
			case 12 -> take12(message.data);
			case 13 -> take13(message.data);
			case 14 -> take14(message.data);
			case 15 -> take15(message.data);
			case 18 -> take18(message.data);
			case 19 -> take19(message.data);
			case 20 -> take20(message.data);
			case 21 -> take21(message.data);
			case 22 -> take22(message.data);
			case 23 -> take23(message.data);
			case 24 -> take24(message.data);
			case 25 -> take25(message.data);
		}
	}

	@Override
	public void readUDP(Message message) {
		switch (message.type) {
			case 2:
				take2(message.data);
				break;
		}
	}

	public void take1(String str) {
		pingChecker.takePing();
	}

	//координаты игрока - (int x, int y, int direction, int speed, int directionDraw, int id)
	public void take2(String str) {
		int x = Integer.parseInt(str.split(" ")[0]);
		int y = Integer.parseInt(str.split(" ")[1]);
		int direction = Integer.parseInt(str.split(" ")[2]);
		int directionGun = Integer.parseInt(str.split(" ")[3]);
		double speed = Double.parseDouble(str.split(" ")[4]);
		double moveDirection = Double.parseDouble(str.split(" ")[5]);
		double animSpeed = Double.parseDouble(str.split(" ")[6]);
		long numberPackage = Long.parseLong(str.split(" ")[7]);
		int enemyId = Integer.parseInt(str.split(" ")[8]);

		if (clientData.enemy.containsKey(enemyId)) {
			clientData.enemy.get(enemyId).setData(x, y, direction, directionGun, speed, moveDirection, numberPackage);
		}
	}

	//данные о карте - (int width, int height, String background)
	public void take3(String str) {
		String mapPath = str.split(" ")[0];
		mapPath = mapPath.replace("\\", "/");
		MapSpecification mapSpecification = MapSpecificationLoader.getMapSpecification(mapPath);
		locationManager.setActiveLocation(new BattleLocation(mapSpecification));
	}

	//старт сервера - (int peopleMax, int myIdFromServer)
	public void take4(String str) {
		clientData.peopleMax = Integer.parseInt(str.split(" ")[0]);
		clientData.myIdFromServer = Integer.parseInt(str.split(" ")[1]);

		Color tankColor = new Color(settingsService.getSettings().getProfile().getColor());
		//В ответ отправляем свои данные (цвет и ник)
		String message = tankColor.getRed()
				+ " " + tankColor.getGreen()
				+ " " + tankColor.getBlue()
				+ " " + settingsService.getSettings().getProfile().getNickname();
		tcpControl.send(17, message);

		//Запускаем пингатор
		pingChecker.start();
	}

	//позиция танка игрока при генерации карты (int x, int y, int direction)
	public void take5(String str) {
		int x = Integer.parseInt(str.split(" ")[0]);
		int y = Integer.parseInt(str.split(" ")[1]);
		int direction = Integer.parseInt(str.split(" ")[2]);

		clientData.player = new PlayerTank(
				Context.getService(LocationManager.class).getActiveLocation(), x, y, direction,
				equipmentService.createDefaultArmor(),
				equipmentService.createDefaultGun(),
				equipmentService.createDefaultBullet());

		Settings.Profile profile = Context.getService(SettingsService.class).getSettings().getProfile();
		clientData.player.setNickname(profile.getNickname());
		clientData.player.setColor(new Color(profile.getColor()));
		clientData.player.setLocationCameraToThisObject();

		//Заполнение таблицы врагов (в соответствтие с id)
		for (int id = 0; id < clientData.peopleMax; id++) {
			if (id != clientData.myIdFromServer) {
				clientData.enemy.put(id, new EnemyTank(
						Context.getService(LocationManager.class).getActiveLocation(), 0, 0, 0,
						equipmentService.createDefaultArmor(),
						equipmentService.createDefaultGun(),
						equipmentService.createDefaultBullet(),
						id));
			}
		}
	}

	//ящик создан - (int x, int y, int type, int idBox)
	public void take7(String str) {
		int x = Integer.parseInt(str.split(" ")[0]);
		int y = Integer.parseInt(str.split(" ")[1]);
		int type = Integer.parseInt(str.split(" ")[2]);
		int idBox = Integer.parseInt(str.split(" ")[3]);

		new Box(Context.getService(LocationManager.class).getActiveLocation(), x, y, type, idBox);
	}

	//начало рестарта
	public void take8(String str) {
		if (clientData.player != null && clientData.player.isAlive()) {
			tcpControl.send(24, "");
			battleStatisticService.getPlayerStatistic().incrementWin();
		}

		clientData.battle = false;
		Context.getService(LocationManager.class).getActiveLocation().destroy();
		Context.getService(LocationManager.class).getActiveLocation().getCamera().deleteFollowObject();

		clientData.mapObjects = new Vector<>();
		clientData.enemyBullet = new ArrayList<>();
	}

	//конец отправки карты
	public void take10(String str) {
		tcpControl.send(6, "");
	}

	//старт игры (рестарт полностью завершен)
	public void take11(String str) {
		clientData.battle = true;
	}

	//я умер - (int id)
	public void take12(String str) {
		int id = Integer.parseInt(str.split(" ")[0]);

		clientData.enemy.get(id).exploded(); //Взорвался
	}

	//я выстрелил - (int x, int y, double direction, double speed, String texture, long idNet, int id)
	public void take13(String str) {
		int x = Integer.parseInt(str.split(" ")[0]);
		int y = Integer.parseInt(str.split(" ")[1]);
		double direction = Double.parseDouble(str.split(" ")[2]);
		double speed = Double.parseDouble(str.split(" ")[3]);
		String texture = str.split(" ")[4];
		long idNet = Long.parseLong(str.split(" ")[5]);
		int idEmeny = Integer.parseInt(str.split(" ")[6]);

		EnemyBullet enemyBullet = new EnemyBullet(Context.getService(LocationManager.class).getActiveLocation(),
				x, y, speed, direction, spriteStorage.getSprite(texture).texture(), idEmeny, idNet);
		clientData.enemyBullet.add(enemyBullet);
	}

	//я нанёс урон игроку enemyId (double damage, int idSuffer, int idDamager)
	public void take14(String str) {
		double damage = Double.parseDouble(str.split(" ")[0]);
		int idSuffer = Integer.parseInt(str.split(" ")[1]);
		int idDamager = Integer.parseInt(str.split(" ")[2]);

		if (idSuffer == clientData.myIdFromServer) {
			clientData.player.changeHp(-damage);
			clientData.lastDamageDealerEnemyId = idDamager;
		}
	}

	//моя пуля уничтожилась (long idNet, int explosion, int id)
	public void take15(String str) {
		long idNet = Long.parseLong(str.split(" ")[0]);
		int explosionSize = Integer.parseInt(str.split(" ")[1]);
		int idEnemy = Integer.parseInt(str.split(" ")[2]);

		for (EnemyBullet bullet : clientData.enemyBullet) {
			if (bullet.idEnemy == idEnemy && bullet.idNet == idNet) {
				bullet.destroy(explosionSize);
				break;
			}
		}
	}

	//сервер отправил данные об игроке id - (int red, int green, int blue, String name, int id)
	public void take18(String str) {
		int red = Integer.parseInt(str.split(" ")[0]);
		int green = Integer.parseInt(str.split(" ")[1]);
		int blue = Integer.parseInt(str.split(" ")[2]);
		String name = str.split(" ")[3];
		int id = Integer.parseInt(str.split(" ")[4]);

		if (clientData.enemy.get(id).getEnemyTankNetworkComponent().isValid()) return;

		clientData.enemy.get(id).setNickname(name);
		clientData.enemy.get(id).setColor(new Color(red, green, blue));
		clientData.enemy.get(id).setColor(clientData.enemy.get(id).getColor());
		clientData.enemy.get(id).getEnemyTankNetworkComponent().setValid(true);
	}

	//я сменил броню - (String armorName, int id)
	public void take19(String str) {
		String armorName = str.split(" ")[0];
		int enemyId = Integer.parseInt(str.split(" ")[1]);

		clientData.enemy.get(enemyId).changeArmor(equipmentService.createArmor(armorName));
	}

	//я сменил оружие - (String gunName, int id)
	public void take20(String str) {
		String gunName = str.split(" ")[0];
		int enemyId = Integer.parseInt(str.split(" ")[1]);

		clientData.enemy.get(enemyId).changeGun(equipmentService.createGun(gunName));
	}

	//я подобрал ящик - (int idBox)
	public void take21(String str) {
		int idBox = Integer.parseInt(str.split(" ")[0]);
		/* TODO реализовать уничтожение ящика при подборе его противником
		for (GameObject gameObject : Context.getService(LocationManager.class).getActiveLocation().getObjects()) {
			if (gameObject instanceof Box && ((Box) gameObject).getId() == idBox) {
				gameObject.destroy();
			}
		}*/
	}

	//объект карты уничтожен бронёй - (int mid)
	public void take22(String str) {
		int mid = Integer.parseInt(str.split(" ")[0]);
		if (mid < clientData.mapObjects.size()){
			((DestroyedMapObject) clientData.mapObjects.get(mid)).destroy();
			clientData.mapObjects.set(mid, null);
		}
	}

	//прибавить этому игроку одно убийство, он меня убил - (int id)
	public void take23(String str) {
		int id = Integer.parseInt(str.split(" ")[0]);

		if (clientData.myIdFromServer == id) {
			battleStatisticService.getPlayerStatistic().incrementKill();
		} else {
			battleStatisticService.getEnemyStatistic(id).incrementKill();
		}
	}

	//прибавить мне одну победу, я остался один - (int id)
	public void take24(String str) {
		int id = Integer.parseInt(str.split(" ")[0]);

		battleStatisticService.getEnemyStatistic(id).incrementWin();
	}

	//я инициировал событие звука - (int x, int y, String sound)
	public void take25(String str) {
		int x = Integer.parseInt(str.split(" ")[0]);
		int y = Integer.parseInt(str.split(" ")[1]);
		String sound = str.split(" ")[2];

		audioService.playSoundEffect(audioStorage.getAudio(sound), x, y,
				gameSettingsService.getGameSettings().getSoundRange());
	}

}
