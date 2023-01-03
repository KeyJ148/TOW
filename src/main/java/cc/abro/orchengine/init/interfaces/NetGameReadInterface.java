package cc.abro.orchengine.init.interfaces;

import cc.abro.orchengine.net.client.Message;

public interface NetGameReadInterface {

    default void readTCP(Message message) {
    }  //Engine: Различные действия с уникальными индексами с пакетами TCP

    default void readUDP(Message message) {
    }//Engine: Различные действия с уникальными индексами с пакетами UDP
}
