public class BuscaProfundidade {

    public static void executar(Grafo grafo, int inicio) {
        boolean[] visitado = new boolean[grafo.getNumVertices()];

        System.out.println("Busca em Profundidade:");

        dfs(grafo, inicio, visitado);

        System.out.println();
    }

    private static void dfs(Grafo grafo, int atual, boolean[] visitado) {
        visitado[atual] = true;

        System.out.print(atual + " ");

        for (Aresta aresta : grafo.getAdjacentes(atual)) {
            int vizinho = aresta.getDestino();

            if (!visitado[vizinho]) {
                dfs(grafo, vizinho, visitado);
            }
        }
    }
}