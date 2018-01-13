package game.client.inf;

import engine.Global;
import engine.image.TextureManager;
import engine.inf.Inf;
import engine.inf.examples.Label;
import engine.inf.frame.ColorFrame;
import game.client.ClientData;
import game.client.tanks.Tank;
import org.newdawn.slick.Color;

public class PlayerTable{

    public static final int PANEL_WIDTH = 500;
    public static final int POS_WIDTH_NICK = 10;
    public static final int POS_WIDTH_KILL = 350;
    public static final int POS_WIDTH_DEATH = 400;
    public static final int POS_WIDTH_WIN = 450;
    public static final int LEN_HEIGHT_STR_MAIN = 37;
    public static final int LEN_HEIGHT_STR = 20;

    public static boolean enable = false;

    public static Inf table;
    public static Label labelMain;
    public static Label[] labels;


    private static void create(){
        int wf = Global.engine.render.getWidth();
        int hf = Global.engine.render.getHeight();
        int w = PANEL_WIDTH;
        int h = LEN_HEIGHT_STR_MAIN + ClientData.peopleMax*LEN_HEIGHT_STR;

        table = new Inf(wf/2, hf/2, w, h, new Color(190, 190, 190));
        table.frame = new ColorFrame(Color.black);

        labelMain = new Label(wf/2, hf/2-h/2+LEN_HEIGHT_STR_MAIN/2, w, LEN_HEIGHT_STR_MAIN, TextureManager.getTexture("sys_null"));
        labelMain.label = "Nick: Kill/Death/Win";
        labelMain.size = 16;

        labels = new Label[ClientData.peopleMax];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new Label(wf/2, hf/2-h/2+LEN_HEIGHT_STR_MAIN/2+(LEN_HEIGHT_STR)*i+LEN_HEIGHT_STR/2, w, LEN_HEIGHT_STR, TextureManager.getTexture("sys_null"));
        }

        setText();
    }

    private static void setText(){
        for (int i = 0; i < labels.length; i++) {
            String text;
            if (ClientData.enemy.containsKey(i)){
                text = tankToString(ClientData.enemy.get(i));
            } else {
                text = tankToString(ClientData.player);
            }

            labels[i].label = text;
        }
    }

    private static String tankToString(Tank tank){
        return tank.name + ":   "
             + tank.kill + "/" +
             + tank.death + "/" +
             + tank.win;
    }

    public static void enable(){
        if (table == null) create();

        Global.infMain.add(table);
        Global.infMain.add(labelMain);
        for (Label label : labels) {
            Global.infMain.add(label);
        }

        setText();
        enable = true;
    }

    public static void disable(){
        table.delete();
        labelMain.delete();
        for (Label label : labels) {
            label.delete();
        }

        enable = false;
    }
}
