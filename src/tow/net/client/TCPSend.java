package tow.net.client;

import tow.Global;
import tow.player.Bullet;

public class TCPSend{
	
	public void sendM2(){//Пинг до сервера и обратно
		Global.tcpControl.send("-2 ");
	}
	
	public void sendM1(){//Готовность скачивать карту
		Global.tcpControl.send("-1 ");
	}
	
	public void send0(String str){
		Global.tcpControl.send("0 " + str);
	}
	
	public void send1(Bullet bull){//Сдлеан выстрел
		Global.tcpControl.send("1 " + Math.round(bull.getX()) + " " + Math.round(bull.getY()) + " " 
				+ bull.getDirection() + " " + bull.getSpeed() + " "
				+ bull.getClass().getName() + " " + Global.name + " " + Global.idNet);
	}
	
	public void send2(Bullet bull){//Уничтожение пули
		Global.tcpControl.send("2 " + bull.getIdNet() + " " + Global.name);
	}
	
	public void send3(Bullet bull, String enemyName){//Нанесение дамага
		Global.tcpControl.send("3 " + enemyName + " " + bull.getDamage());
	}
	
	public void send4(){//Танк игрока уничтожен
		Global.tcpControl.send("4 " + Global.name);
	}
	
	public void send5(){//Перезагрузка карты (Имя победителя)
		Global.tcpControl.send("5 " + Global.name);
	}
	
	public void send10(String nameEnemy){//Запросить данные от врага
		Global.tcpControl.send("10 " + nameEnemy);
	}
	
	public void send11(){//Отправить свои данные другим игрокам
		int red = Global.color.getRed();
		int green = Global.color.getGreen();
		int blue = Global.color.getBlue();
		Global.tcpControl.send("11 " + Global.name + " " + red + " " + green + " " + blue);
	}
	
	public void send13(int idBox){//Я подобрал ящик
		Global.tcpControl.send("13 " + idBox);
	}
	
	public void send14(String nameArmor){//Я сменил броню
		Global.tcpControl.send("14 " + Global.name + " " + nameArmor);
	}
	
	public void send15(String nameGun){//Я сменил пушку
		Global.tcpControl.send("15 " + Global.name + " " + nameGun);
	}
}