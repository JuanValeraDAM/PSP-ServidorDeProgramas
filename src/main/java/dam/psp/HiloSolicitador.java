package dam.psp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HiloSolicitador implements Runnable {
    private Socket socketConectado;
    public HiloSolicitador(Socket socketConectado){
        this.socketConectado=socketConectado;
    }

    @Override
    public void run() {
        System.out.println("HiloSolicitador ha recibido la conexión con el socket: "+socketConectado.getLocalPort());
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socketConectado.getInputStream()));
            PrintWriter pr=new PrintWriter(socketConectado.getOutputStream());

            String proceso=br.readLine();
            ProcessBuilder pb=new ProcessBuilder(proceso);
            Process procesoIniciado = pb.start();
            System.out.println("Proceso iniciado");
            procesoIniciado.waitFor();
            System.out.println("Proceso finalizado");

            pr.write(procesoIniciado.exitValue());
            pr.flush();


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
