package game.client.person.player;


import engine.Global;
import engine.image.TextureHandler;
import engine.map.Border;
import engine.obj.Obj;
import engine.obj.components.Collision;
import engine.obj.components.CollisionDirect;
import engine.obj.components.Movement;
import engine.obj.components.Position;
import engine.obj.components.render.Sprite;
import engine.setting.ConfigReader;
import game.client.ClientData;
import game.client.map.Wall;
import game.client.person.enemy.EnemyArmor;

public class Bullet extends Obj implements Collision.CollisionListener{

	public static final String PATH_SETTING = "bullet/";

	public double damage;//дамаг пушки+патрона
	public int range;//дальность пушки+патрона
	
	public double startX;
	public double startY;
	public long idNet;

	public Player player;
	public TextureHandler texture;

	public Bullet(TextureHandler texture){
		this.texture = texture;
	}

	public void init(Player player, double x, double y, double dir, double damage, int range){
		this.player = player;
		this.damage = damage;//Дамаг исключительно от выстрелевшей пушки
		this.range = range;
		this.idNet = ClientData.idNet;
		this.startX = x;
		this.startY = y;

		this.position = new Position(this, x, y, texture.depth, dir);
		this.movement = new Movement(this);
		this.movement.setDirection(dir);
		this.rendering = new Sprite(this, texture);

		this.collision = new CollisionDirect(this, texture.mask, range);
		this.collision.addCollisionObjects(new Class[] {Wall.class, EnemyArmor.class, Border.class});
		this.collision.addListener(this);
		((CollisionDirect) collision).init();

		String className = getClass().getName();
		loadData(PATH_SETTING + className.substring(className.lastIndexOf('.')+1) + ".properties");

		Global.tcpControl.send(13, getData());
		ClientData.idNet++;
	}

	@Override
	public void collision(Obj obj) {
		if (obj.getClass().equals(Wall.class) ||
			obj.getClass().equals(Border.class)){
				destroyBullet();
		}

		if (obj.getClass().equals(EnemyArmor.class)){
			EnemyArmor ea = (EnemyArmor) obj;

			Global.tcpControl.send(14, damage + " " + ea.enemy.id);
			destroyBullet();
		}
	}
	
	public void destroyBullet(){
		Global.tcpControl.send(15, String.valueOf(idNet));
		destroy();
	}

	@Override
	public void update(long delta) {
		if (!destroy) {
			if (Math.sqrt(Math.pow(startX - position.x, 2) + Math.pow(startY - position.y, 2)) >= range) {
				destroyBullet();
			}
		}

		super.update(delta);
	}

	public String getData(){
		return  Math.round(position.x)
		+ " " +	Math.round(position.y)
		+ " " +	movement.getDirection()
		+ " " +	movement.speed
		+ " " +	texture.name
		+ " " +	idNet;
	}
	
	public void loadData(String fileName){
		ConfigReader cr = new ConfigReader(fileName);
		
		movement.speed = cr.findDouble("SPEED");
		damage += cr.findDouble("DAMAGE");//К дамагу пушки прибавляем дамаг патрона
		range += cr.findInteger("RANGE");//К дальности пушки прибавляем дальность патрона
	}
}