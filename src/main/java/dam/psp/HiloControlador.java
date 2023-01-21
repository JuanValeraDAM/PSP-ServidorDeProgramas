package dam.psp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;

/*
 */
public class HiloControlador implements Runnable {
    private final List<ServerSocket> serverSocketList;
    private final DatagramPacket datagramPacket;
    private boolean estabaAbierto=false;


    public HiloControlador(List<ServerSocket> serverSocketList, DatagramPacket datagramPacket) {
        this.serverSocketList = serverSocketList;
        this.datagramPacket = datagramPacket;
    }

    @Override
    public void run() {

        String mensajeRecibido = new String(datagramPacket.getData(), 0, datagramPacket.getLength(), StandardCharsets.UTF_8);
        String[] ordenYPuerto = mensajeRecibido.split(" ");

        if (ordenYPuerto[0].equals("DESACTIVAR")) {
            for (ServerSocket serverSocket :
                    serverSocketList) {
                if (serverSocket.getLocalPort() == Integer.parseInt(ordenYPuerto[1])) {
                    try {
                        estabaAbierto=true;
                        serverSocket.close();
                        System.out.println("Conexión cerrada con el puerto: " + ordenYPuerto[1]);
                    } catch (IOException e) {
                        System.err.println("Soy el hilo controlador y ha habido un error en la parte de cerrar la conexión "+e.getMessage());
                    }
                }
            }
            if(!estabaAbierto){
                System.out.println("No se puede cerrar porque no estaba abierto");
            }
        } else {
            try {
                System.out.println("Abierto el puerto: " + ordenYPuerto[1]);
                ServerSocket serverSocket = new ServerSocket(Integer.parseInt(ordenYPuerto[1]));
                serverSocketList.add(serverSocket);
                while (true) {
                    Socket socketConectado = serverSocket.accept();
                    HiloSolicitador solicitador = new HiloSolicitador(socketConectado);
                    Thread hiloSolicitador = new Thread(solicitador);
                    hiloSolicitador.start();
                    System.out.println("Conexión establecida con el puerto: " + ordenYPuerto[1]);
                }
            } catch (IOException ignored) {
            }


        }
    }
}
