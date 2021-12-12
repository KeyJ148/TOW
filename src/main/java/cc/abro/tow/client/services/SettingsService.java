package cc.abro.tow.client.services;

import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.resources.settings.SettingsLoader;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.SettingsStorage;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class SettingsService {

    public void setSettings(String nickname, Color tankColor){
        if (nickname.isEmpty()) {
            throw new EmptyNicknameException();
        }

        SettingsStorage.PROFILE.NICKNAME = nickname;
        SettingsStorage.PROFILE.COLOR = tankColor.getRGBArray();
        try {
            SettingsLoader.saveExternalSettings(SettingsStorage.PROFILE);
        } catch (IOException e) {
            log.warn("Settings can't be saved", e);
            throw new CantSaveSettingException();
        }

        ClientData.name = nickname;
        ClientData.color = tankColor;
    }

    public class EmptyNicknameException extends RuntimeException {}
    public class CantSaveSettingException extends RuntimeException {}
}
