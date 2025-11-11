import java.io.Serializable;

public class Veiculo implements Serializable {
    private String placa;
    private String marca;
    private String modelo;
    private int ano;
    private String cor;
    private int quilometragem;
    private double valor;

    public Veiculo(String placa, String marca, String modelo, int ano, String cor, int quilometragem, double valor) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.quilometragem = quilometragem;
        this.valor = valor;
    }

    public String getPlaca() { return placa; }
    public void setCor(String cor) { this.cor = cor; }
    public void setValor(double valor) { this.valor = valor; }

    @Override
    public String toString() {
        return String.format("Placa: %s | Marca: %s | Modelo: %s | Ano: %d | Cor: %s | Km: %d | Valor: %.2f",
                placa, marca, modelo, ano, cor, quilometragem, valor);
    }

    public String paraCSV() {
        return String.join(",", placa, marca, modelo, String.valueOf(ano), cor, String.valueOf(quilometragem), String.valueOf(valor));
    }

    public static Veiculo deCSV(String linha) {
        String[] dados = linha.split(",");
        return new Veiculo(dados[0], dados[1], dados[2],
                Integer.parseInt(dados[3]), dados[4],
                Integer.parseInt(dados[5]), Double.parseDouble(dados[6]));
    }
}
