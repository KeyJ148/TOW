package tow.game.client.tanks.player;


import tow.engine.Global;
import tow.engine.image.TextureHandler;
import tow.engine.image.TextureManager;
import tow.engine.map.Border;
import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.GameObjectFactory;
import tow.engine.gameobject.components.Collision;
import tow.engine.gameobject.components.CollisionDirect;
import tow.engine.gameobject.components.Movement;
import tow.engine.gameobject.components.Position;
import tow.engine.gameobject.components.particles.Particles;
import tow.engine.gameobject.components.render.Sprite;
import tow.engine.setting.ConfigReader;
import tow.game.client.ClientData;
import tow.game.client.GameSetting;
import tow.game.client.map.Wall;
import tow.game.client.particles.Explosion;
import tow.game.client.tanks.enemy.EnemyArmor;

public class Bullet extends GameObject implements Collision.CollisionListener{

	public static final String PATH_SETTING = "game/bullet/";
	public String name, title; //name - техническое название, title - игровое

	public double damage; //Дамаг (пушка и в loadData добавляем дамаг пули)
	public int range; //Дальность (пушка и в loadData добавляем дальность пули)
	public int explosionSize;
	
	public double startX;
	public double startY;
	public long idNet;

	public String sound_shot;
	public String sound_hit;

	public Player player;
	public TextureHandler texture;

	public void init(Player player, double x, double y, double dir, double damage, int range, String name){
		this.player = player;
		this.name = name;
		this.damage = damage; //Дамаг исключительно от выстрелевшей пушки
		this.range = range; //Дальность исключительно от выстрелевшей пушки
		this.idNet = ClientData.idNet;
		this.startX = x;
		this.startY = y;

		setComponent(new Movement());
		this.getComponent(Movement.class).setDirection(dir);
		loadData();

		setComponent(new Position(x, y, texture.depth, dir));
		setComponent(new Sprite(texture));

		setComponent(new CollisionDirect(texture.mask, range));
		getComponent(Collision.class).addCollisionObjects(new Class[] {Wall.class, EnemyArmor.class, Border.class});
		getComponent(Collision.class).addListener(this);
		((CollisionDirect) getComponent(Collision.class)).init();

		Global.tcpControl.send(13, getData());
		ClientData.idNet++;

		playSoundShot();
	}

	@Override
	public void collision(GameObject gameObject) {
		if (isDestroy()) return;

		if (gameObject.getClass().equals(Border.class)){
			destroy(0);
		}

		if (gameObject.getClass().equals(Wall.class)){
			destroy(explosionSize);

			Global.audioPlayer.playSoundEffect(Global.audioStorage.getAudio(sound_hit), (int) getComponent(Position.class).x, (int) getComponent(Position.class).y, GameSetting.SOUND_RANGE);
			Global.tcpControl.send(25, (int) getComponent(Position.class).x + " " + (int) getComponent(Position.class).y + " " + sound_hit);
		}

		if (gameObject.getClass().equals(EnemyArmor.class)){
			EnemyArmor ea = (EnemyArmor) gameObject;

			Global.tcpControl.send(14, damage + " " + ea.enemy.id);
			destroy(explosionSize);

			Global.audioPlayer.playSoundEffect(Global.audioStorage.getAudio(sound_hit), (int) getComponent(Position.class).x, (int) getComponent(Position.class).y, GameSetting.SOUND_RANGE);
			Global.tcpControl.send(25, (int) getComponent(Position.class).x + " " + (int) getComponent(Position.class).y + " " + sound_hit);

			//Для вампирского сета
			if (ea.enemy.alive) player.hitting(damage);
		}
	}

	public void destroy(int expSize){
		destroy();
		Global.tcpControl.send(15, idNet + " " + expSize);

		if (explosionSize > 0) {
			GameObject explosion = GameObjectFactory.create(getComponent(Position.class).x, getComponent(Position.class).y, -100);
			explosion.setComponent(new Explosion(expSize));
			explosion.getComponent(Particles.class).destroyObject = true;
			Global.location.objAdd(explosion);
		}
	}

	@Override
	public void update(long delta) {
		if (!isDestroy()) {
			if (Math.sqrt(Math.pow(startX - getComponent(Position.class).x, 2) + Math.pow(startY - getComponent(Position.class).y, 2)) >= range) {
				destroy(0);
			}
		}

		super.update(delta);
	}

	public void playSoundShot(){
		Global.audioPlayer.playSoundEffect(Global.audioStorage.getAudio(sound_shot), (int) getComponent(Position.class).x, (int) getComponent(Position.class).y, GameSetting.SOUND_RANGE);
		Global.tcpControl.send(25, (int) getComponent(Position.class).x + " " + (int) getComponent(Position.class).y + " " + sound_shot);
	}

	public String getData(){
		return  Math.round(getComponent(Position.class).x)
		+ " " +	Math.round(getComponent(Position.class).y)
		+ " " +	getComponent(Movement.class).getDirection()
		+ " " +	getComponent(Movement.class).speed
		+ " " +	texture.name
		+ " " +	idNet;
	}

	public String getConfigFileName(){
		return PATH_SETTING + name + ".properties";
	}
	
	public void loadData(){
		ConfigReader cr = new ConfigReader(getConfigFileName());

		getComponent(Movement.class).speed = cr.findDouble("SPEED") + player.stats.speedTankUp/2;
		getComponent(Movement.class).speed = Math.max(getComponent(Movement.class).speed, player.stats.speedTankUp*GameSetting.MIN_BULLET_SPEED_KOEF);

		damage += cr.findDouble("DAMAGE");//К дамагу пушки прибавляем дамаг патрона
		range += cr.findInteger("RANGE");//К дальности пушки прибавляем дальность патрона
		texture = TextureManager.getTexture(cr.findString("IMAGE_NAME"));
		title = cr.findString("TITLE");
		sound_shot = cr.findString("SOUND_SHOT");
		sound_hit = cr.findString("SOUND_HIT");
		explosionSize = cr.findInteger("EXPLOSION_SIZE");
	}

}