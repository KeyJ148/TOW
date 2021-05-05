package cc.abro.orchengine.implementation;

import cc.abro.orchengine.net.server.MessagePack;

public interface NetServerReadInterface {

    void readTCP(MessagePack.Message message); //Engine: вызывается каждый раз при получение сервером сообщения по протоколу TCP

    void readUDP(MessagePack.Message message); //Engine: вызывается каждый раз при получение сервером сообщения по протоколу UDP
}
