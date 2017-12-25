package game.client.person.player;

import engine.Global;
import engine.Vector2;
import engine.image.Camera;
import engine.inf.title.Title;
import engine.obj.Obj;
import game.client.ClientData;
import game.client.person.equipment.armor.ADefault;
import game.client.person.equipment.bullet.BDefault;
import game.client.person.enemy.Enemy;
import game.client.person.equipment.gun.GDefault;
import org.newdawn.slick.Color;

import java.awt.*;
import java.util.Map;

public class Player extends Obj {

	private static final int SEND_DATA_EVERY_TICKS = 2;//Отправлять данные каждые N степов
	private int sendDataLastTicks = 0;//Как давно отправляли данные
	
	public boolean takeArmor = true;//Разрешение на подбор определённого ящика
	public boolean takeGun = true;
	public boolean takeBullet = true;
	public boolean takeHealth = true;

	public PlayerController controller;
	public boolean alive = true; //Мы живы

	public Armor armor;
	public Gun gun;
	public Class bullet;
	
	public Player(double x, double y, double direction){
		super(x, y, 0);

		this.controller = new PlayerController(this);

		this.bullet = BDefault.class;

		this.armor = new ADefault();
		this.armor.init(this, x, y, direction);

		this.gun = new GDefault();
		this.gun.init(this, x, y, direction);

		Global.room.objAdd(armor);
		Global.room.objAdd(gun);

		setColor(ClientData.color);
	}
	
	//Обработка потока ввода
	@Override
	public void update(long delta){
		super.update(delta);

		//Если мы мертвы, то ничего не делать
		if (!alive) return;

		controller.update(delta);

		//Отрисовка ника и хп
		Vector2<Integer> relativePosition = position.getRelativePosition();
		int nameX = (int) Math.round(relativePosition.x-ClientData.name.length()*3.25); // lengthChar/2
		int nameY = relativePosition.y-50;
		Global.engine.render.addTitle(new Title(nameX, nameY, ClientData.name));

		Global.engine.render.addTitle(new Title(1, -3, "HP: " +  Math.round(armor.hp) + "/" + Math.round(armor.hpMax), Color.black, 20, Font.BOLD));

		//Отправка данных о игроке
		sendDataLastTicks++;
		if (ClientData.battle && sendDataLastTicks >= SEND_DATA_EVERY_TICKS){
			sendDataLastTicks = 0;
			Global.tcpControl.send(2, getData());
		}
	}

	public void exploded(){
		setColor(new Color(110, 15, 0));
		alive = false;

		//Если у врага инициализированна камера  он жив
		for (Map.Entry<Integer, Enemy> entry: ClientData.enemy.entrySet()) {
			if (entry.getValue().camera != null && entry.getValue().alive) {
				Camera.setFollowObject(entry.getValue().camera);
				break;
			}
		}
	}

	public String getData(){

		return  Math.round(position.x)
		+ " " + Math.round(position.y)
		+ " " + Math.round(armor.position.getDirectionDraw())
		+ " " + Math.round(gun.position.getDirectionDraw())
		+ " " + Math.round(armor.movement.speed)
		+ " " + armor.movement.getDirection();
	}
	
	public void setColor(Color c){
		setColorArmor(c);
		setColorGun(c);
	}
	
	public void setColorArmor(Color c){
		armor.rendering.color = c;
	}
	
	public void setColorGun(Color c){
		gun.rendering.color = c;
	}
}