package tow.game.client.inf;

import tow.engine.Global;
import tow.engine.image.TextureManager;
import tow.engine.inf.Inf;
import tow.engine.inf.examples.Label;
import tow.engine.inf.frame.ColorFrame;
import tow.game.client.ClientData;
import tow.game.client.tanks.Tank;
import org.newdawn.slick.Color;

public class PlayerTable{

    public static final Color GRAY_1 = new Color(99, 99, 99, 230);
    public static final Color GRAY_2 = new Color(73, 73, 73, 230);
    public static final Color GRAY_3 = new Color(48, 48, 48, 230);
    public static final Color GRAY_4 = new Color(0, 0, 0, 230);
    public static final Color KILLS =  new Color(87, 96, 87, 230);
    public static final Color DEATHS = new Color(94, 85, 85, 230);
    public static final Color WINS =   new Color(85, 86, 94, 230);

    public static final int PANEL_WIDTH = 424;
    public static final int ELEMENT_HEIGHT = 26;
    public static final int ELEMENT_WIDTH = 33;
    public static final int NICKNAME_WIDTH = 280;

    public static Inf[] staticLines = new Inf[7];
    public static Inf[] playersLines;

    //=======


    //public static final int PANEL_WIDTH = 500;
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
        int h = (ClientData.peopleMax+1)*(ELEMENT_HEIGHT+2);

        table = new Inf(wf/2, hf/2, w, h, GRAY_1);
        table.frame = new ColorFrame(GRAY_4);

        staticLines[0] = new Inf(wf/2-w/2+NICKNAME_WIDTH+ELEMENT_WIDTH*1, hf/2+ELEMENT_HEIGHT/2, 2, h-ELEMENT_HEIGHT, GRAY_2);
        staticLines[1] = new Inf(wf/2-w/2+NICKNAME_WIDTH+ELEMENT_WIDTH*2, hf/2+ELEMENT_HEIGHT/2, 2, h-ELEMENT_HEIGHT, GRAY_2);
        staticLines[2] = new Inf(wf/2, hf/2-h/2+ELEMENT_HEIGHT, PANEL_WIDTH, 2, GRAY_4);
        staticLines[3] = new Inf(wf/2-w/2+NICKNAME_WIDTH, hf/2, 2, h, GRAY_4);
        staticLines[4] = new Inf(wf/2-w/2+NICKNAME_WIDTH+ELEMENT_WIDTH*3, hf/2, 2, h, GRAY_4);
        staticLines[5] = new Inf(wf/2-w/2+NICKNAME_WIDTH+ELEMENT_WIDTH*1, hf/2-h/2+ELEMENT_HEIGHT/2, 2, ELEMENT_HEIGHT, GRAY_4);
        staticLines[6] = new Inf(wf/2-w/2+NICKNAME_WIDTH+ELEMENT_WIDTH*2, hf/2-h/2+ELEMENT_HEIGHT/2, 2, ELEMENT_HEIGHT, GRAY_4);

        playersLines = new Inf[ClientData.peopleMax-1];
        for (int i = 0; i < playersLines.length; i++) {
            playersLines[i] = new Inf(wf/2, hf/2-h/2+(i+2)*(ELEMENT_HEIGHT+2), PANEL_WIDTH, 2, GRAY_3);
        }

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
        for(int i=0; i<staticLines.length; i++) Global.infMain.add(staticLines[i]);
        for(int i=0; i<playersLines.length; i++) Global.infMain.add(playersLines[i]);

        Global.infMain.add(labelMain);
        for (Label label : labels) {
            Global.infMain.add(label);
        }

        setText();
        enable = true;
    }

    public static void disable(){
        table.delete();
        for(int i=0; i<staticLines.length; i++) staticLines[i].delete();
        for(int i=0; i<playersLines.length; i++) playersLines[i].delete();

        labelMain.delete();
        for (Label label : labels) {
            label.delete();
        }

        enable = false;
    }
}
