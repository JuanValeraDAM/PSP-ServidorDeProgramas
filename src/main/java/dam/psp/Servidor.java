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
Explicaciones pertinentes:
- El servidor está tan tranquilo y le viene un cliente y le dice: oye, ponte a escuchar en X puerto. O en su defecto;
deja de escuchar en X puerto. Tod0 esto por UDP.
A su vez, viene un pavo que sabe en qué puerto está el tío escuchando y le dice: sosio, arme un fabor y ponte argo wapo.
mira eto ermano, buena mielda. Obviamente aquí ya hablamos de TCP. Lo de que hay que lanzar los procesos con el ProccessBuilder
no te creas que me ha quedado muy claro y menos aún lo que deWulve al servidor. La verdad es que ahora mismo no tengo muy
claro el protocolo, voy a picar un poco de código a ver si así me se asientan las ideas.
 */

import java.net.DatagramSocket;
import java.net.SocketException;

public class Servidor {
    public static void main(String[] args) throws SocketException {

        DatagramSocket datagramSocket=new DatagramSocket(7000);

    }
}