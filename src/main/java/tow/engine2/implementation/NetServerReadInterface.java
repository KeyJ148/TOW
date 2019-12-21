package tow.engine2.implementation;

import tow.engine.net.server.MessagePack;

public interface NetServerReadInterface {

    void readTCP(MessagePack.Message message); //Engine: вызывается каждый раз при получение сервером сообщения по протоколу TCP
    void readUDP(MessagePack.Message message); //Engine: вызывается каждый раз при получение сервером сообщения по протоколу UDP
}
