package cc.abro.orchengine.net.server.readers;

import cc.abro.orchengine.exceptions.EngineException;
import cc.abro.orchengine.net.NetTools;
import cc.abro.orchengine.net.server.Connect;
import cc.abro.orchengine.net.server.GameServer;
import cc.abro.orchengine.net.server.MessagePack;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.DatagramPacket;

@Log4j2
public class ServerReadUDP extends Thread {

    @Override
    public void run() {
        //Постоянный обмен данными (на UDP)
        //Только после подключения всех игроков
        String str, ipSender;
        int portSender;
        try {
            int size = 2048;//SettingsStorage.NETWORK.UDP_READ_BYTE_ARRAY_LEN;

            while (true) {
                //Ждем сообщение
                DatagramPacket packet = new DatagramPacket(new byte[size], size);
                GameServer.socketUDP.receive(packet);

                //Получаем информацию из сообщения
                str = new String(NetTools.clearByteData(packet.getData()));
                ipSender = packet.getAddress().getHostAddress();
                portSender = packet.getPort();

                //Находим игрока-отправителя
                for (Connect connect : GameServer.connects) {
                    if (connect.ipRemote.equals(ipSender) && connect.portUDP == portSender) {
                        //Добавляем в очередь сообщений
                        connect.messagePack.add(str, MessagePack.Message.InetType.UDP);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            log.warn("UDP server socket closed");
            throw new EngineException(e);
        }
    }
}