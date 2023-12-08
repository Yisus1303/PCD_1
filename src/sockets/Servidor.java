package sockets;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Servidor {
    private static final int PUERTO = 5000;
    private static ExecutorService pool = Executors.newFixedThreadPool(10); // Pool de hilos para manejar múltiples clientes

    public static void main(String[] args) {
        ServerSocket servidor = null;

        try {
            servidor = new ServerSocket(PUERTO);
            System.out.println("Servidor iniciado. Esperando clientes...");

            while (true) {
                Socket clienteSocket = servidor.accept();
                System.out.println("Cliente conectado desde: " + clienteSocket);

                ClienteHandler cliente = new ClienteHandler(clienteSocket);
                pool.execute(cliente);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (servidor != null) {
                try {
                    servidor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ClienteHandler implements Runnable {
        private Socket clienteSocket;
        private BufferedReader entrada;
        private PrintWriter salida;

        public ClienteHandler(Socket socket) {
            try {
                this.clienteSocket = socket;
                entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
                salida = new PrintWriter(clienteSocket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                salida.println("Elige opcion");
                salida.println("Hola");
                salida.println("Adios");
                salida.println("Que tal");
                salida.println("Como esta el tiempo");
                salida.println("En que mes estamos");

                String opcion;
                while ((opcion = entrada.readLine()) != null) {
                    int seleccion = Integer.parseInt(opcion.trim());
                    String respuesta = obtenerRespuesta(seleccion);
                    salida.println(respuesta);
                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            } finally {
                try {
                    clienteSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private String obtenerRespuesta(int opcionSeleccionada) {
            switch (opcionSeleccionada) {
                case 1:
                    return "Buenas, que tal?";
                case 2:
                    return "Hasta pronto";
                case 3:
                    return "Estupendamente feliz";
                case 4:
                    return "El timepo se encuentra lluvioso";
                case 5:
                    return "2023 casi ya en 2024";
                default:
                    return "Opción inválida, por favor selecciona una opción válida.";
            }
        }
    }
}
