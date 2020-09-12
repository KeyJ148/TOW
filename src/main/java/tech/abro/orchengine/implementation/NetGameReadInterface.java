package tech.abro.orchengine.implementation;

import tech.abro.orchengine.net.client.Message;

public interface NetGameReadInterface {

    void readTCP(Message message);  //Engine: Различные действия с уникальными индексами с пакетами TCP
    void readUDP(Message message); //Engine: Различные действия с уникальными индексами с пакетами UDP
}
