package cc.abro.orchengine.services;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.cycle.Engine;
import cc.abro.orchengine.cycle.LeguiRender;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.cycle.Update;
import cc.abro.orchengine.gameobject.LocationManager;
import cc.abro.orchengine.resources.animations.AnimationStorage;
import cc.abro.orchengine.resources.audios.AudioService;
import cc.abro.orchengine.resources.audios.AudioStorage;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.TextureService;

public interface ServiceConsumer {

    default Engine getEngine() {
        return Context.getService(Engine.class);
    }

    default Update getUpdate() {
        return Context.getService(Update.class);
    }

    default Render getRender() {
        return Context.getService(Render.class);
    }

    default LeguiRender getLeguiRender() {
        return Context.getService(LeguiRender.class);
    }

    default OpenGlService getOpenGlService() {
        return Context.getService(OpenGlService.class);
    }

    default TextureService getTextureService() {
        return Context.getService(TextureService.class);
    }

    default SpriteStorage getSpriteStorage() {
        return Context.getService(SpriteStorage.class);
    }

    default AnimationStorage getAnimationStorage() {
        return Context.getService(AnimationStorage.class);
    }

    default AudioService getAudioService() {
        return Context.getService(AudioService.class);
    }

    default AudioStorage getAudioStorage() {
        return Context.getService(AudioStorage.class);
    }

    default LocationManager getLocationManager() {
        return Context.getService(LocationManager.class);
    }

    default BlockingGuiService getBlockingGuiService() {
        return Context.getService(BlockingGuiService.class);
    }

}
