import java.io.*;
import java.net.*;

public class TesteCarga {
    public static void main(String[] args) {
        int numClientes = 20;

        for (int i = 0; i < numClientes; i++) {
            int id = i;
            new Thread(() -> {
                try (Socket s = new Socket("localhost", 5000);
                     PrintWriter saida = new PrintWriter(s.getOutputStream(), true);
                     BufferedReader entrada = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
                    saida.println("LISTAR|");
                    String resposta = entrada.readLine();
                    System.out.println("Cliente " + id + ": " + resposta);
                } catch (IOException e) {
                    System.err.println("Cliente " + id + " falhou: " + e.getMessage());
                }
            }).start();
        }
    }
}
