package dam.psp;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ClienteControladorUDP {

    private Scanner sc = new Scanner(System.in);
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    private SocketAddress socketAddress;
    private int numeroPuerto;
    private String mensaje;

    public ClienteControladorUDP() throws IOException {
        datagramSocket = new DatagramSocket();
        pedirOpciones();
    }

    public static void main(String[] args) throws IOException {
        ClienteControladorUDP clienteControladorUDP = new ClienteControladorUDP();

    }


    private void pedirOpciones() throws IOException {
        while (true) {
            System.out.println("Introduce el número de puerto sobre el que deseas hacer la modificación o (0) para salir.");
            numeroPuerto = sc.nextInt();
            if (numeroPuerto == 0) {
                return;
            }
            System.out.println("¿Qué deseas hacer, activar(1) o desactivar(2)?");
            int opcion = sc.nextInt();
            if (opcion == 1) {
                mensaje = String.format("ACTIVAR %d", numeroPuerto);
            } else if (opcion == 2) {
                mensaje = String.format("DESACTIVAR %d", numeroPuerto);
            }
            byte[] buffer = mensaje.getBytes();
            socketAddress = new InetSocketAddress("localhost", 7000);
            datagramPacket = new DatagramPacket(buffer, buffer.length, socketAddress);

            datagramSocket.send(datagramPacket);


        }
    }
}
