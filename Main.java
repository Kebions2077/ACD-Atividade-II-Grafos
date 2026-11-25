
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int opcao;

        do {

            System.out.println("\n===== TP2 - GRAFOS =====");

            System.out.println("1 - Testar Dijkstra");
            System.out.println("2 - Testar Bellman-Ford");
            System.out.println("3 - Experimento Dijkstra");
            System.out.println("4 - Experimento Bellman-Ford");
            System.out.println("5 - Testar Kruskal");
            System.out.println("6 - Testar Prim");
            System.out.println("7 - Testar Floyd-Warshall");
            System.out.println("8 - Testar Ford-Fulkerson");
            System.out.println("9 - Sair");

            System.out.print("Escolha: ");

            try {

                opcao = scanner.nextInt();

                switch (opcao) {

                    case 1:
                        testarAlgoritmo("Dijkstra", scanner);
                        break;

                    case 2:
                        testarAlgoritmo("BellmanFord", scanner);
                        break;

                    case 3:
                        executarExperimento("Dijkstra", scanner);
                        break;

                    case 4:
                        executarExperimento("BellmanFord", scanner);
                        break;

                    case 5:
                        testarAGM("Kruskal", scanner);
                        break;

                    case 6:
                        testarAGM("Prim", scanner);
                        break;

                    case 7:
                        testarFloyd(scanner);
                        break;

                    case 8:
                        testarFordFulkerson(scanner);
                        break;

                    case 9:
                        System.out.println("Encerrando...");
                        break;

                    default:
                        System.out.println("Opcao invalida");
                }

            } catch (Exception e) {

                System.out.println("Erro: " + e.getMessage());

                scanner.nextLine();

                opcao = 0;
            }

        } while (opcao != 9);

        scanner.close();
    }


    private static void testarAlgoritmo(
            String algoritmo,
            Scanner scanner) throws Exception {

        System.out.print("Arquivo .gr: ");

        String arquivo = scanner.next();

        System.out.print("Representacao (1-LISTA / 2-MATRIZ): ");

        int tipo = scanner.nextInt();

        Grafo.Representacao representacao =
                tipo == 1
                        ? Grafo.Representacao.LISTA
                        : Grafo.Representacao.MATRIZ;

        System.out.print("Vertice de origem (comecando em 0): ");

        int origem = scanner.nextInt();

        Grafo grafo = GrafoLoader.carregar(
                arquivo,
                representacao
        );

        System.out.println("\nExecutando " + algoritmo);

        double[] resultado;

        if (algoritmo.equals("Dijkstra")) {
            resultado = Dijkstra.executar(grafo, origem);
        } else {
            resultado = BellmanFord.executar(grafo, origem);
        }

        for (int i = 0; i < resultado.length; i++) {

            System.out.println(
                    "Vertice " + i + ": " + resultado[i]
            );
        }
    }


    private static void executarExperimento(
            String algoritmo,
            Scanner scanner) throws Exception {

        System.out.print("Arquivo .gr: ");

        String arquivo = scanner.next();

        System.out.print("Vertice de origem (comecando em 0): ");

        int origem = scanner.nextInt();

        System.out.println("1 - Lista de adjacencia");
        System.out.println("2 - Matriz de adjacencia");

        int tipo = scanner.nextInt();

        Grafo.Representacao representacao =
                tipo == 1
                        ? Grafo.Representacao.LISTA
                        : Grafo.Representacao.MATRIZ;

        Experimento.executarConfiguracao(
                arquivo,
                representacao,
                algoritmo,
                origem,
                "resultados/resultados.csv"
        );
    }


    private static void testarAGM(
            String algoritmo,
            Scanner scanner) throws Exception {

        System.out.print("Arquivo .gr: ");

        String arquivo = scanner.next();

        System.out.println("1 - Lista de adjacencia");
        System.out.println("2 - Matriz de adjacencia");

        int tipo = scanner.nextInt();

        Grafo.Representacao representacao =
                tipo == 1
                        ? Grafo.Representacao.LISTA
                        : Grafo.Representacao.MATRIZ;

        Grafo grafo = GrafoLoader.carregar(
                arquivo,
                representacao
        );

        System.out.println("\nExecutando " + algoritmo);

        ResultadoAGM resultado;

        if (algoritmo.equals("Kruskal")) {
            resultado = Kruskal.executar(grafo);
        } else {
            resultado = Prim.executar(grafo);
        }

        resultado.imprimir();
    }


    private static void testarFloyd(
            Scanner scanner) throws Exception {

        System.out.print("Arquivo .gr: ");

        String arquivo = scanner.next();

        System.out.println("1 - Lista de adjacencia");
        System.out.println("2 - Matriz de adjacencia");

        int tipo = scanner.nextInt();

        Grafo.Representacao representacao =
                tipo == 1
                        ? Grafo.Representacao.LISTA
                        : Grafo.Representacao.MATRIZ;

        Grafo grafo = GrafoLoader.carregar(
                arquivo,
                representacao
        );

        System.out.println("\nExecutando Floyd-Warshall");

        double[][] distancias =
                FloydWarshall.executar(grafo);

        FloydWarshall.imprimir(distancias);
    }


    private static void testarFordFulkerson(
            Scanner scanner) throws Exception {

        System.out.print("Arquivo .gr: ");

        String arquivo = scanner.next();

        System.out.println("1 - Lista de adjacencia");
        System.out.println("2 - Matriz de adjacencia");

        int tipo = scanner.nextInt();

        Grafo.Representacao representacao =
                tipo == 1
                        ? Grafo.Representacao.LISTA
                        : Grafo.Representacao.MATRIZ;

        Grafo grafo = GrafoLoader.carregar(
                arquivo,
                representacao
        );

        System.out.print("Vertice de origem: ");

        int origem = scanner.nextInt();

        System.out.print("Vertice de destino: ");

        int destino = scanner.nextInt();

        System.out.println("\nExecutando Ford-Fulkerson");

        FordFulkerson.Resultado resultado =
                FordFulkerson.executar(
                        grafo,
                        origem,
                        destino
                );

        System.out.println(
                "Fluxo maximo: " +
                        resultado.getFluxoMaximo()
        );
    }
}
