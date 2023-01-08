package cc.abro.orchengine.resources.animations;

import cc.abro.orchengine.resources.masks.Mask;
import cc.abro.orchengine.resources.textures.Texture;

import java.util.List;

public record Animation(List<Texture> textures, Mask mask) { }
