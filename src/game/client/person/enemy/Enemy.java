package game.client.person.enemy;

import engine.Global;
import engine.Vector2;
import engine.image.Camera;
import engine.image.TextureHandler;
import engine.image.TextureManager;
import engine.inf.title.Title;
import engine.obj.Obj;
import engine.obj.components.Movement;
import engine.obj.components.render.Animation;
import engine.obj.components.render.Sprite;
import game.client.ClientData;
import org.newdawn.slick.Color;

import java.util.Map;

public class Enemy{

	public int id = -1;
	public String name = "";
	public Color color = Color.white;

	public boolean valid = false; //Этот враг уже инициализирован? (Отправл свои данные: цвет, ник)
	public static final long REQUEST_DATA_EVERY_TIME = (long) (0.5 * Math.pow(10, 9));//Промежуток времени между запроса информации о враге
	public long timeLastRequestDelta = 0;

	public boolean alive = true; //Этот враг в данный момент жив
	public EnemyArmor armor;
	public Obj gun;
	public Obj camera;

	public Enemy(int id){
		this.id = id;
	}

	//Этот объект не находится в комнате, update вызывает броня
	public void update(long delta){
		if (armor != null && armor.position != null) {
			Vector2<Integer> relativePosition = armor.position.getRelativePosition();
			int nameX = (int) Math.round(relativePosition.x - name.length() * 3.25); // lengthChar/2
			int nameY = relativePosition.y - 50;

			Global.engine.render.addTitle(new Title(nameX, nameY, name));
		}

		if (!valid) {
			timeLastRequestDelta -= delta;
			if (timeLastRequestDelta <= 0) {
				timeLastRequestDelta = REQUEST_DATA_EVERY_TIME;
				Global.tcpControl.send(16, String.valueOf(id));
			}
		}
	}

	public void setData(int x, int y, int direction, int speed, int directionGun){
		if (armor == null){
			armor = new EnemyArmor(x, y, direction, TextureManager.getAnimation("a_default"), this);
			Global.room.objAdd(armor);
		}

		if (gun == null){
			TextureHandler gunTexture = TextureManager.getTexture("g_default");
			gun = new Obj(x, y, direction, gunTexture);
			gun.movement = new Movement(gun);
			Global.room.objAdd(gun);
		}

		if (camera == null){
			camera = new Obj(x, y, 0);
			Global.room.objAdd(camera);
		}

		armor.position.x = x;
		armor.position.y = y;
		armor.position.setDirectionDraw(direction);
		armor.movement.speed = speed;
		gun.position.setDirectionDraw(directionGun);
		dragIn();
	}

	public void dragIn(){
		if (gun == null || gun.position == null ||
			armor == null || armor.position == null ||
			camera == null || camera.position == null)
				return;

		gun.position.x = armor.position.x;
		gun.position.y = armor.position.y;
		Global.room.mapControl.update(gun);//Необходимо делать после перемещения объекта, уже прописано в obj.update, но на всякий

		camera.position.x = armor.position.x;
		camera.position.y = armor.position.y;
		Global.room.mapControl.update(camera);
	}

	//Данный танк взорвался (уничтожен)
	public void exploded(){
		alive = false;
		setColor(new Color(110, 15, 0));

		//Если в данный момент игрок мертв и наблюдает за этим врагом
		if (Camera.getFollowObject() != null && Camera.getFollowObject() == camera){
			//Если у врага инициализированна камера  он жив
			for (Map.Entry<Integer, Enemy> entry: ClientData.enemy.entrySet()) {
				if (entry.getValue().camera != null && entry.getValue().alive) {
					Camera.setFollowObject(entry.getValue().camera);
					break;
				}
			}
		}
	}
	
	public void newArmor(String nameArmor){
		armor.rendering = new Animation(armor, TextureManager.getAnimation(nameArmor));
		setColorArmor(color);

		Global.room.mapControl.update(armor);
	}
	
	public void newGun(String nameGun){
		gun.rendering = new Sprite(gun, TextureManager.getTexture(nameGun));
		setColorGun(color);

		Global.room.mapControl.update(gun);
	}

	public void setColor(Color c){
		setColorArmor(c);
		setColorGun(c);
	}

	public void setColorArmor(Color c){
		if (armor == null || armor.rendering == null) return;
		armor.rendering.color = c;
	}

	public void setColorGun(Color c){
		if (gun == null || gun.rendering == null) return;
		gun.rendering.color = c;
	}
}