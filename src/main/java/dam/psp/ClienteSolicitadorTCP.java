package dam.psp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteSolicitadorTCP {


    public static void main(String[] args) throws IOException {
        Socket socket;
        Scanner sc = new Scanner(System.in);
        System.out.println("¿En qué puerto deseas conectarte?");
        int puerto = sc.nextInt();
        System.out.println("¿Qué proceso deseas ejecutar?");
        String proceso=sc.next();

        socket=new Socket("localhost", puerto);

        BufferedReader br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw=new PrintWriter(socket.getOutputStream());

        pw.println(proceso);
        pw.flush();

        int codigoDeRetorno = br.read();
        System.out.println(codigoDeRetorno);
        socket.close();
    }


}
