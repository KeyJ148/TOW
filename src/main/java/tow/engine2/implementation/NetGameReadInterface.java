package tow.engine2.implementation;

import tow.engine2.net.client.Message;

public interface NetGameReadInterface {

    void readTCP(Message message);  //Engine: Различные действия с уникальными индексами с пакетами TCP
    void readUDP(Message message); //Engine: Различные действия с уникальными индексами с пакетами UDP
}
