import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Servidor {
    private static final int PORTA = 5000;
    private static final String ARQUIVO = "gestaoVeiculos/veiculos.csv";
    private static final List<Veiculo> veiculos = new ArrayList<>();
    private static final ReentrantLock trava = new ReentrantLock();

    public static void main(String[] args) {
        System.out.println("Arquivo salvo em: " + new File(ARQUIVO).getAbsolutePath());

        carregarVeiculos();
        try (ServerSocket servidor = new ServerSocket(PORTA)) {
            System.out.println("Servidor iniciado na porta " + PORTA);
            while (true) {
                Socket cliente = servidor.accept();
                new Thread(new AtendeCliente(cliente)).start();
            }
        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        }
    }

    private static void carregarVeiculos() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                veiculos.add(Veiculo.deCSV(linha));
            }
            System.out.println("Carregados " + veiculos.size() + " veículos.");
        } catch (IOException e) {
            System.err.println("Erro ao carregar veículos: " + e.getMessage());
        }
    }

    private static void salvarVeiculos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO))) {
            for (Veiculo v : veiculos) pw.println(v.paraCSV());
        } catch (IOException e) {
            System.err.println("Erro ao salvar veículos: " + e.getMessage());
        }
    }

    private static class AtendeCliente implements Runnable {
        private Socket socket;
        public AtendeCliente(Socket socket) { this.socket = socket; }

        @Override
        public void run() {
            try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter saida = new PrintWriter(socket.getOutputStream(), true)) {

                String comando;
                while ((comando = entrada.readLine()) != null) {
                    String resposta = processarComando(comando.trim());
                    saida.println(resposta);
                    if (comando.equalsIgnoreCase("SAIR|")) break;
                }
            } catch (IOException e) {
                System.err.println("Erro no cliente: " + e.getMessage());
            }
        }

        private String processarComando(String comando) {
            boolean bloqueou = false;
            try {
                if (comando.startsWith("INSERIR|")) {
                    String[] p = comando.substring(8).split(",");
                    trava.lock();
                    bloqueou = true;
                    veiculos.add(new Veiculo(p[0], p[1], p[2], Integer.parseInt(p[3]), p[4],
                            Integer.parseInt(p[5]), Double.parseDouble(p[6])));
                    salvarVeiculos();
                    return "OK|Veículo inserido com sucesso";
                } else if (comando.startsWith("REMOVER|")) {
                    String placa = comando.split("=")[1];
                    trava.lock();
                    bloqueou = true;
                    boolean removido = veiculos.removeIf(v -> v.getPlaca().equalsIgnoreCase(placa));
                    salvarVeiculos();
                    return removido ? "OK|Veículo removido" : "ERRO|Placa não encontrada";
                } else if (comando.startsWith("BUSCAR|")) {
                    String placa = comando.split("=")[1];
                    for (Veiculo v : veiculos) {
                        if (v.getPlaca().equalsIgnoreCase(placa)) {
                            return "OK|" + v.toString();
                        }
                    }
                    return "ERRO|Veículo não encontrado";
                } else if (comando.startsWith("ALTERAR|")) {
                    String[] dados = comando.substring(8).split(";");
                    String placa = dados[0].split("=")[1];
                    String campo = dados[1].split("=")[1];
                    String valor = dados[2].split("=")[1];
                    trava.lock();
                    bloqueou = true;
                    for (Veiculo v : veiculos) {
                        if (v.getPlaca().equalsIgnoreCase(placa)) {
                            if (campo.equalsIgnoreCase("cor")) v.setCor(valor);
                            if (campo.equalsIgnoreCase("valor")) v.setValor(Double.parseDouble(valor));
                            salvarVeiculos();
                            return "OK|Veículo atualizado";
                        }
                    }
                    return "ERRO|Placa não encontrada";
                } else if (comando.startsWith("LISTAR|")) {
                    StringBuilder sb = new StringBuilder("OK|Lista de veículos:\n");
                    for (Veiculo v : veiculos) sb.append(v).append("\n");
                    return sb.toString();
                } else if (comando.equalsIgnoreCase("SAIR|")) {
                    return "OK|Conexão encerrada";
                }
                return "ERRO|Comando inválido";
            } finally {
                if (bloqueou) trava.unlock();
            }
        }

        }
    }

