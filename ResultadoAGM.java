
import java.util.List;

public class ResultadoAGM {

    private List<ArestaAGM> arestas;
    private double pesoTotal;

    public ResultadoAGM(List<ArestaAGM> arestas) {
        this.arestas = arestas;
        this.pesoTotal = 0;

        for (ArestaAGM aresta : arestas) {
            pesoTotal += aresta.getPeso();
        }
    }

    public List<ArestaAGM> getArestas() {
        return arestas;
    }

    public double getPesoTotal() {
        return pesoTotal;
    }

    public void imprimir() {
        System.out.println("Arestas da arvore geradora minima:");

        for (ArestaAGM aresta : arestas) {
            System.out.println(aresta);
        }

        System.out.println("Peso total: " + pesoTotal);
    }
}
