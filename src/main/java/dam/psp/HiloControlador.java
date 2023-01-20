package dam.psp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class HiloControlador implements Runnable {
    private final List<ServerSocket> serverSocketList;
    private final DatagramPacket datagramPacket;
    private final ExecutorService pool;

    public HiloControlador(List<ServerSocket> serverSocketList, DatagramPacket datagramPacket, ExecutorService pool) {
        this.serverSocketList = serverSocketList;
        this.datagramPacket = datagramPacket;
        this.pool = pool;
    }

    @Override
    public void run() {

        String mensajeRecibido = new String(datagramPacket.getData(), 0, datagramPacket.getLength(), StandardCharsets.UTF_8);
        String[] ordenYPuerto = mensajeRecibido.split(" ");

        if (ordenYPuerto[0].equals("ACTIVAR")) {
            try {
                System.out.println("Soy el hilo controlador y voy a escuchar en el puerto: " + ordenYPuerto[1]);
                ServerSocket serverSocket = new ServerSocket(Integer.parseInt(ordenYPuerto[1]));
                Socket socketConectado = serverSocket.accept();
                pool.submit(new HiloSolicitador(socketConectado));
                System.out.println("Conexión establecida con el puerto: " + ordenYPuerto[1]);
                serverSocketList.add(serverSocket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Soy el hilo controlador y estoy en la parte de desactivar");
            for (ServerSocket serverSocket :
                    serverSocketList) {
                if (serverSocket.getLocalPort() == Integer.parseInt(ordenYPuerto[1])) {
                    try {
                        serverSocket.close();
                        System.out.println("Conexión cerrada con el puerto: " + ordenYPuerto[1]);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            System.out.println("Soy el hilo controlador, he salido del for sin encontrar el puerto que cerrar");
        }
    }
}
