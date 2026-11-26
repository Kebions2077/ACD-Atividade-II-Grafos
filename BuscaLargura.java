import java.util.*;

public class BuscaLargura {

    public static void executar(Grafo grafo, int inicio) {
        int vertices = grafo.getNumVertices();

        boolean[] visitado = new boolean[vertices];

        Queue<Integer> fila = new LinkedList<>();

        visitado[inicio] = true;
        fila.add(inicio);

        System.out.println("Busca em Largura:");

        while (!fila.isEmpty()) {
            int atual = fila.poll();

            System.out.print(atual + " ");

            for (Aresta aresta : grafo.getAdjacentes(atual)) {
                int vizinho = aresta.getDestino();

                if (!visitado[vizinho]) {
                    visitado[vizinho] = true;
                    fila.add(vizinho);
                }
            }
        }
        System.out.println();
    }
}