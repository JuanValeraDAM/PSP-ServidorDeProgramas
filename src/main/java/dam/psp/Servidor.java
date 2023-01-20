package dam.psp;

/*
Implementa un servidor que acepte conexiones por TCP, por las cuales el cliente le enviará el nombre de un programa ejecutable.
 El servidor ejecutará el programa, esperará a que termine, y enviará por la conexión una respuesta incluyendo el código de
  retorno de dicho programa. Entonces, cliente y servidor cerrarán la conexión.

Inicialmente, el servidor no estará escuchando en ningún puerto. Para activar la escucha,
el servidor debe recibir un mensaje UDP conteniendo el puerto en que debe escuchar.
Similarmente, el servidor también puede dejar de escuchar en un puerto determinado, si recibe un mensaje UDP que se lo indique.

Esto significa que hay dos tipos de cliente: el cliente TCP, que se conecta para pedir que se ejecute un programa,
y el cliente UDP, que actúa como un "controlador" del servidor, diciéndole que se ponga a escuchar o que deje
de escuchar en los puertos que sean.

Es posible que el servidor tenga el servicio TCP funcionando a la vez en varios puertos;
tod0 depende de lo que le ordene hacer el cliente controlador.
 */
/*
Borrador:

 */

/*
Explicaciones pertinentes:


 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
    private static ExecutorService pool= Executors.newFixedThreadPool(10);
    private static List<ServerSocket> serverSocketList= new ArrayList<>();
    public static void main(String[] args) throws SocketException {

        DatagramSocket datagramSocket = new DatagramSocket(7000);
        while (true) {
            byte[] buffer = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
            try {
                datagramSocket.receive(datagramPacket);
                pool.submit(new HiloControlador(serverSocketList, datagramPacket, pool));
            } catch (IOException e) {
                System.err.println("Error al recibir el datagramPacket" + e.getMessage());
            }



        }
    }
}