import java.util.*;

public class OrdenacaoTopologica {

    public static void executar(Grafo grafo) {

        if (!grafo.isDirigido()) {
            throw new IllegalArgumentException(
                    "Ordenacao topologica requer grafo dirigido"
            );
        }

        int vertices = grafo.getNumVertices();

        int[] grauEntrada = new int[vertices];

        // Calcula o grau de entrada
        for (int i = 0; i < vertices; i++) {
            for (Aresta aresta : grafo.getAdjacentes(i)) {
                grauEntrada[aresta.getDestino()]++;
            }
        }

        Queue<Integer> fila = new LinkedList<>();

        // Adiciona os vértices sem dependências
        for (int i = 0; i < vertices; i++) {
            if (grauEntrada[i] == 0) {
                fila.add(i);
            }
        }

        int contador = 0;

        System.out.println("Ordenacao Topologica:");

        while (!fila.isEmpty()) {
            int atual = fila.poll();

            System.out.print(atual + " ");
            contador++;

            for (Aresta aresta : grafo.getAdjacentes(atual)) {
                int vizinho = aresta.getDestino();

                grauEntrada[vizinho]--;

                if (grauEntrada[vizinho] == 0) {
                    fila.add(vizinho);
                }
            }
        }

        System.out.println();

        if (contador != vertices) {
            System.out.println("O grafo possui ciclo! Ordenacao topologica impossivel.");
        }
    }
}