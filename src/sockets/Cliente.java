package sockets;

import java.io.*;
import java.net.*;

public class Cliente {
    private static final String ip_servidor = "127.0.0.1"; // Cambiar a la IP del servidor si es remoto
    private static final int puerto = 5000;

    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader entradaUsuario = null;
        PrintWriter salidaServidor = null;

        try {
            socket = new Socket(ip_servidor, puerto);
            System.out.println("Conectado al servidor.");

            entradaUsuario = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader entradaServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salidaServidor = new PrintWriter(socket.getOutputStream(), true);

            String mensajeServidor;
            while ((mensajeServidor = entradaServidor.readLine()) != null) {
                System.out.println(mensajeServidor);

                String seleccion = entradaUsuario.readLine();
                salidaServidor.println(seleccion);

                String respuesta = entradaServidor.readLine();
                System.out.println("Respuesta del servidor: " + respuesta);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (entradaUsuario != null)
                    entradaUsuario.close();
                if (salidaServidor != null)
                    salidaServidor.close();
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
