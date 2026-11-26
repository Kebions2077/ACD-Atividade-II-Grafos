import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n===== TP2 - GRAFOS =====");
            System.out.println("1  - Testar Dijkstra");
            System.out.println("2  - Testar Bellman-Ford");
            System.out.println("3  - Experimento Dijkstra");
            System.out.println("4  - Experimento Bellman-Ford");
            System.out.println("5  - Busca em Largura (BFS)");
            System.out.println("6  - Busca em Profundidade (DFS)");
            System.out.println("7  - Ordenacao Topologica");
            System.out.println("8  - Testar Prim (MST)");
            System.out.println("9  - Testar Kruskal (MST)");
            System.out.println("10 - Testar Floyd-Warshall");
            System.out.println("11 - Testar Ford-Fulkerson");
            System.out.println("12 - Experimento Prim (MST)");
            System.out.println("13 - Experimento Kruskal (MST)");
            System.out.println("14 - Gerar grafico de linhas (CSV -> PNG)");
            System.out.println("15 - Gerar boxplot (CSV -> PNG)");
            System.out.println("16 - Gerar todos os graficos");
            System.out.println("17 - Sair");
            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());

                switch (opcao) {
                    case 1  -> testarCaminhoMinimo("Dijkstra", scanner);
                    case 2  -> testarCaminhoMinimo("BellmanFord", scanner);
                    case 3  -> executarExperimentoCaminho("Dijkstra", scanner);
                    case 4  -> executarExperimentoCaminho("BellmanFord", scanner);
                    case 5  -> testarBusca("BFS", scanner);
                    case 6  -> testarBusca("DFS", scanner);
                    case 7  -> testarTopologica(scanner);
                    case 8  -> testarMST("Prim", scanner);
                    case 9  -> testarMST("Kruskal", scanner);
                    case 10 -> testarFloyd(scanner);
                    case 11 -> testarFordFulkerson(scanner);
                    case 12 -> executarExperimentoMST("Prim", scanner);
                    case 13 -> executarExperimentoMST("Kruskal", scanner);
                    case 14 -> executarGraficoLinhas(scanner);
                    case 15 -> executarGraficoBoxplot(scanner);
                    case 16 -> executarGraficos(scanner);
                    case 17 -> System.out.println("Encerrando...");
                    default -> System.out.println("Opcao invalida");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                opcao = 0;
            }

        } while (opcao != 17);

        scanner.close();
    }

    // ---------- Helpers ----------

    private static Grafo carregarGrafo(Scanner scanner, boolean dirigido) throws Exception {
        System.out.print("Arquivo .gr: ");
        String arquivo = scanner.nextLine().trim();

        System.out.print("Representacao (1-LISTA / 2-MATRIZ): ");
        int tipo = Integer.parseInt(scanner.nextLine().trim());

        Grafo.Representacao representacao =
                tipo == 1 ? Grafo.Representacao.LISTA : Grafo.Representacao.MATRIZ;

        return dirigido
                ? GrafoLoader.carregarDigrafo(arquivo, representacao)
                : GrafoLoader.carregar(arquivo, representacao);
    }

    // ---------- Caminho minimo ----------

    private static void testarCaminhoMinimo(String algoritmo, Scanner scanner) throws Exception {
        Grafo grafo = carregarGrafo(scanner, true);

        System.out.print("Vertice de origem (comecando em 0): ");
        int origem = Integer.parseInt(scanner.nextLine().trim());

        System.out.println("\nExecutando " + algoritmo);

        double[] resultado = algoritmo.equals("Dijkstra")
                ? Dijkstra.executar(grafo, origem)
                : BellmanFord.executar(grafo, origem);

        for (int i = 0; i < resultado.length; i++) {
            System.out.println("Vertice " + i + ": " + resultado[i]);
        }
    }

    // ---------- Buscas ----------

    private static void testarBusca(String tipo, Scanner scanner) throws Exception {
        Grafo grafo = carregarGrafo(scanner, tipo.equals("Topologica"));

        System.out.print("Vertice de inicio: ");
        int inicio = Integer.parseInt(scanner.nextLine().trim());

        if (tipo.equals("BFS")) {
            BuscaLargura.executar(grafo, inicio);
        } else {
            BuscaProfundidade.executar(grafo, inicio);
        }
    }

    private static void testarTopologica(Scanner scanner) throws Exception {
        Grafo grafo = carregarGrafo(scanner, true);
        OrdenacaoTopologica.executar(grafo);
    }

    // ---------- MST ----------

    private static void testarMST(String algoritmo, Scanner scanner) throws Exception {
        Grafo grafo = carregarGrafo(scanner, false);

        System.out.println("\nExecutando " + algoritmo + " (MST)");

        ResultadoAGM r = algoritmo.equals("Prim")
                ? Prim.executar(grafo, 0)
                : Kruskal.executar(grafo);

        r.imprimir();
    }

    // ---------- Floyd / Ford-Fulkerson ----------

    private static void testarFloyd(Scanner scanner) throws Exception {
        Grafo grafo = carregarGrafo(scanner, true);

        System.out.println("\nExecutando Floyd-Warshall");
        double[][] distancias = FloydWarshall.executar(grafo);
        FloydWarshall.imprimir(distancias);
    }

    private static void testarFordFulkerson(Scanner scanner) throws Exception {
        Grafo grafo = carregarGrafo(scanner, true);

        System.out.print("Vertice de origem: ");
        int origem = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Vertice de destino: ");
        int destino = Integer.parseInt(scanner.nextLine().trim());

        System.out.println("\nExecutando Ford-Fulkerson");
        FordFulkerson.Resultado resultado =
                FordFulkerson.executar(grafo, origem, destino);

        System.out.println("Fluxo maximo: " + resultado.getFluxoMaximo());
    }

    // ---------- Experimentos ----------

    private static void executarExperimentoCaminho(String algoritmo, Scanner scanner) throws Exception {
        System.out.print("Arquivo .gr: ");
        String arquivo = scanner.nextLine().trim();

        System.out.print("Vertice de origem (comecando em 0): ");
        int origem = Integer.parseInt(scanner.nextLine().trim());

        System.out.println("1 - Lista de adjacencia");
        System.out.println("2 - Matriz de adjacencia");
        System.out.print("Escolha: ");
        int tipo = Integer.parseInt(scanner.nextLine().trim());

        Grafo.Representacao representacao =
                tipo == 1 ? Grafo.Representacao.LISTA : Grafo.Representacao.MATRIZ;

        Experimento.executarConfiguracao(
                arquivo, representacao, algoritmo, origem,
                "resultados/resultados.csv"
        );
    }

    private static void executarExperimentoMST(String algoritmo, Scanner scanner) throws Exception {
        System.out.print("Arquivo .gr: ");
        String arquivo = scanner.nextLine().trim();

        System.out.println("1 - Lista de adjacencia");
        System.out.println("2 - Matriz de adjacencia");
        System.out.print("Escolha: ");
        int tipo = Integer.parseInt(scanner.nextLine().trim());

        Grafo.Representacao representacao =
                tipo == 1 ? Grafo.Representacao.LISTA : Grafo.Representacao.MATRIZ;

        Experimento.executarConfiguracaoMST(
                arquivo, representacao, algoritmo,
                "resultados/resultados.csv"
        );
    }

    // ---------- Graficos ----------

    private static String[] perguntarCsvSaida(Scanner scanner) {
        System.out.print("CSV de entrada (enter = resultados/resultados.csv): ");
        String csv = scanner.nextLine().trim();
        if (csv.isEmpty()) csv = "resultados/resultados.csv";

        System.out.print("Diretorio de saida (enter = graficos): ");
        String dir = scanner.nextLine().trim();
        if (dir.isEmpty()) dir = "graficos";

        return new String[]{csv, dir};
    }

    private static void executarGraficoLinhas(Scanner scanner) throws Exception {
        String[] cfg = perguntarCsvSaida(scanner);
        Graficos.gerarLinhas(Graficos.lerCSV(cfg[0]), cfg[1] + "/linha_tempo_medio.png");
        System.out.println("Grafico de linhas gerado em: " + cfg[1]);
    }

    private static void executarGraficoBoxplot(Scanner scanner) throws Exception {
        String[] cfg = perguntarCsvSaida(scanner);
        Graficos.gerarBoxplots(Graficos.lerCSV(cfg[0]), cfg[1] + "/boxplot_tempo.png");
        System.out.println("Boxplot gerado em: " + cfg[1]);
    }

    private static void executarGraficos(Scanner scanner) throws Exception {
        String[] cfg = perguntarCsvSaida(scanner);
        Graficos.gerarTodos(cfg[0], cfg[1]);
        System.out.println("Graficos gerados em: " + cfg[1]);
    }
}