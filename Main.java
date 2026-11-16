public class Main {

    public static void main(String[] args) {

        GrafoNaoDirecionado grafo = new GrafoNaoDirecionado(
                5,
                true,
                Grafo.Representacao.LISTA
        );

        grafo.adicionarAresta(0, 1, 10);
        grafo.adicionarAresta(0, 2, 5);
        grafo.adicionarAresta(1, 2, 3);
        grafo.adicionarAresta(1, 3, 7);
        grafo.adicionarAresta(2, 4, 8);
        grafo.adicionarAresta(3, 4, 2);

        System.out.println("GRAFO:");

        grafo.imprimir();

        System.out.println("\nArestas adjacentes ao vertice 1:");

        for (Aresta aresta : grafo.getAdjacentes(1)) {
            System.out.println(aresta);
        }

        System.out.println("\nPeso da aresta entre 0 e 2:");

        System.out.println(grafo.getPeso(0, 2));
    }
}