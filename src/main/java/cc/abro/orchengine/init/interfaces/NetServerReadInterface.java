package cc.abro.orchengine.init.interfaces;

import cc.abro.orchengine.net.server.MessagePack;

public interface NetServerReadInterface {

    default void readTCP(MessagePack.Message message) {
    } //Engine: вызывается каждый раз при получение сервером сообщения по протоколу TCP

    default void readUDP(MessagePack.Message message) {
    } //Engine: вызывается каждый раз при получение сервером сообщения по протоколу UDP
}
