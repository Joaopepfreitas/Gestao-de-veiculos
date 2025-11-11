import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        String servidorHost = "localhost";
        int porta = 5000;

        try (Socket socket = new Socket(servidorHost, porta);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("==============================================");
            System.out.println("     SISTEMA DE GESTÃO DE VEÍCULOS - CLIENTE  ");
            System.out.println("==============================================");

            boolean continuar = true;
            while (continuar) {
                System.out.println("\nEscolha uma operação:");
                System.out.println("1 - Inserir veículo");
                System.out.println("2 - Remover veículo");
                System.out.println("3 - Buscar veículo");
                System.out.println("4 - Alterar veículo");
                System.out.println("5 - Listar todos os veículos");
                System.out.println("0 - Sair");
                System.out.print("Opção: ");
                String opcao = scanner.nextLine();

                String comando = "";

                switch (opcao) {
                    case "1":
                        System.out.println("\n--- Inserir novo veículo ---");
                        System.out.print("Placa: ");
                        String placa = scanner.nextLine();
                        System.out.print("Marca: ");
                        String marca = scanner.nextLine();
                        System.out.print("Modelo: ");
                        String modelo = scanner.nextLine();
                        System.out.print("Ano: ");
                        String ano = scanner.nextLine();
                        System.out.print("Cor: ");
                        String cor = scanner.nextLine();
                        System.out.print("Quilometragem: ");
                        String km = scanner.nextLine();
                        System.out.print("Valor: ");
                        String valor = scanner.nextLine();

                        comando = String.format("INSERIR|%s,%s,%s,%s,%s,%s,%s",
                                placa, marca, modelo, ano, cor, km, valor);
                        break;

                    case "2":
                        System.out.println("\n--- Remover veículo ---");
                        System.out.print("Informe a placa: ");
                        placa = scanner.nextLine();
                        comando = "REMOVER|placa=" + placa;
                        break;

                    case "3":
                        System.out.println("\n--- Buscar veículo ---");
                        System.out.print("Informe a placa: ");
                        placa = scanner.nextLine();
                        comando = "BUSCAR|placa=" + placa;
                        break;

                    case "4":
                        System.out.println("\n--- Alterar veículo ---");
                        System.out.print("Placa do veículo a alterar: ");
                        placa = scanner.nextLine();
                        System.out.print("Nova marca: ");
                        marca = scanner.nextLine();
                        System.out.print("Novo modelo: ");
                        modelo = scanner.nextLine();
                        System.out.print("Novo ano: ");
                        ano = scanner.nextLine();
                        System.out.print("Nova cor: ");
                        cor = scanner.nextLine();
                        System.out.print("Nova quilometragem: ");
                        km = scanner.nextLine();
                        System.out.print("Novo valor: ");
                        valor = scanner.nextLine();

                        comando = String.format("ALTERAR|%s,%s,%s,%s,%s,%s,%s",
                                placa, marca, modelo, ano, cor, km, valor);
                        break;

                    case "5":
                        comando = "LISTAR";
                        break;

                    case "0":
                        comando = "SAIR";
                        continuar = false;
                        break;

                    default:
                        System.out.println(" Opção inválida. Tente novamente.");
                        continue;
                }

                saida.println(comando);

                String resposta = entrada.readLine();
                if (resposta != null) {
                    System.out.println("\n Servidor: " + resposta);
                } else {
                    System.out.println("\n️ Servidor não respondeu ou conexão encerrada.");
                    break;
                }
            }

            System.out.println("\nEncerrando cliente...");
        } catch (IOException e) {
            System.out.println("Erro na comunicação com o servidor: " + e.getMessage());
        }
    }
}
