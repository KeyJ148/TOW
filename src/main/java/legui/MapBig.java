package legui;

import com.google.gson.internal.$Gson$Preconditions;
import tow.engine.obj.ObjFactory;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MapBig {

    public static class Obj{
        int x, y, direction;
        String name;

        public Obj(int x, int y, int direction, String name) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.name = name;
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new FileReader("maps/town.map"));
        scanner.nextLine();

        List<Obj> list = new LinkedList<>();
        while (scanner.hasNextLine()){
            String[] line = scanner.nextLine().split(" ");
            int x = Integer.parseInt(line[0]);
            int y = Integer.parseInt(line[1]);
            int direction = Integer.parseInt(line[2]);
            String name = line[3];

            list.add(new Obj(x, y, direction, name));
        }

        scanner.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter("maps/town10k.map"));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (Obj obj : list){
                    int x = obj.x + i*2000;
                    int y = obj.y + j*2000;
                    writer.write(x + " " + y + " " + obj.direction + " " + obj.name + "\n");
                }
            }
        }

        writer.close();
    }
}
