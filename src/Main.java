import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) throws IOException {
        FileSystemView view = FileSystemView.getFileSystemView();
        File file = view.getHomeDirectory();
        String desktopPath = file.getPath();
        String inputFileName = desktopPath + "\\input_file.txt";

        int quantidade = 0;
        String algoritmo = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            quantidade = Integer.parseInt(reader.readLine());
            algoritmo = reader.readLine();
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de entrada: " + e.getMessage());
            return;
        }

        // Vetor para o melhor caso (ordenado)
        int[] vetorMelhorCaso = new int[quantidade];
        for (int i = 0; i < vetorMelhorCaso.length; i++) {
            vetorMelhorCaso[i] = i + 1;
        }

        // Vetor para o pior caso (ordenado de forma decrescente)
        int[] vetorPiorCaso = new int[quantidade];
        for (int i = 0; i < vetorPiorCaso.length; i++) {
            vetorPiorCaso[i] = quantidade - i;
        }

        String outputFileName = desktopPath + "\\output_file.csv";

        if (!new File(outputFileName).exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName))) {
                bw.write("Data,HH:mm:ss,Algoritmo,Quantidade,Caso,Tempo (ms),Memória (MB)\n");
            } catch (IOException e) {
                System.err.println("Erro ao escrever cabeçalho no arquivo de saída: " + e.getMessage());
                return;
            }
        }

        executarOrdenacao(vetorMelhorCaso, "Melhor Caso", outputFileName, algoritmo, quantidade);
        executarOrdenacao(vetorPiorCaso, "Pior Caso", outputFileName, algoritmo, quantidade);

        long totalTempoMedio = 0;
        double totalMemoriaMedio = 0;

        // Executa o caso médio 5 vezes e calcula a média do tempo e memória
        for (int i = 1; i <= 5; i++) {
            int[] vetorMedioCaso = new int[quantidade];
            for (int j = 0; j < vetorMedioCaso.length; j++) {
                vetorMedioCaso[j] = (int) (Math.random() * quantidade);
            }
            double[] resultados = executarOrdenacao(vetorMedioCaso, "Caso Médio - Execução " + i, outputFileName, algoritmo, quantidade);
            totalTempoMedio += resultados[0];
            totalMemoriaMedio += resultados[1];
        }

        // Calcula a média do tempo e memória para o caso médio
        long mediaTempo = totalTempoMedio / 5;
        double mediaMemoria = totalMemoriaMedio / 5;

        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName, true))) {
            bw.write(agora.format(dateFormatter) + ", " + agora.format(timeFormatter) + ", " + algoritmo + ", " + quantidade + ",Caso Médio, " + mediaTempo + "ms, " + String.format("%.2f", mediaMemoria) + "MB\n");
        } catch (IOException e) {
            System.err.println("Erro ao escrever média no arquivo de saída: " + e.getMessage());
        }
    }

    private static double[] executarOrdenacao(int[] vetor, String caso, String outputFileName, String algoritmo, int quantidade) {
        int[] tempArray = new int[vetor.length]; // Usado apenas pelo MergeSort

        LocalDateTime startDateTime = LocalDateTime.now();

        long startTime = System.currentTimeMillis();
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long startMemory = runtime.totalMemory() - runtime.freeMemory();

        switch (algoritmo.toLowerCase()) {
            case "heapsort":
                HeapSort.heapSort(vetor);
                break;
            case "mergesort":
                MergeSort.mergeSort(vetor, tempArray, 0, vetor.length - 1);
                break;
            case "quicksort":
                QuickSort.quickSort(vetor);
                break;
            default:
                System.err.println("Algoritmo desconhecido: " + algoritmo);
                return new double[]{0, 0};
        }

        long endTime = System.currentTimeMillis();
        long endMemory = runtime.totalMemory() - runtime.freeMemory();

        long durationMillis = endTime - startTime;

        double memoryUsed = (endMemory - startMemory) / (1024.0 * 1024.0);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        System.out.println(caso + ":");
        System.out.println("Data de início da execução: " + startDateTime.format(dateFormatter));
        System.out.println("Hora: " + timeFormatter.format(startDateTime));
        System.out.println("Tempo de execução: " + durationMillis + " milissegundos");
        System.out.println("Consumo de memória: " + String.format("%.2f", memoryUsed) + "MB\n");
        System.out.println();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName, true))) {
            if (!caso.startsWith("Caso Médio")) {
                bw.write(startDateTime.format(dateFormatter) + ", " + startDateTime.format(timeFormatter) + ", " + algoritmo + ", " + quantidade + ", " + caso + ", " + durationMillis + "ms, " + String.format("%.2f", memoryUsed) + "MB\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever resultados no arquivo de saída: " + e.getMessage());
        }

        return new double[]{durationMillis, memoryUsed};
    }
}

