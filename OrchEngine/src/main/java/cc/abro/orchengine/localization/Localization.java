package cc.abro.orchengine.localization;

//import cc.abro.orchengine.resources.audios.Audio;
//import cc.abro.orchengine.resources.sprites.Sprite;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class Localization {

    @Getter
    @Setter
    private String Language;

    private final HashMap<String, String> stringTokenHashMap = new HashMap<>();

    protected String getText (String tokenName){
        return stringTokenHashMap.get(tokenName);
    }
    //private final HashMap<String, Audio> AudioHashMap = new HashMap<String, Audio> ();
    //private final HashMap<String, Sprite> SpritesHashMap = new HashMap<String, Sprite> ();
}