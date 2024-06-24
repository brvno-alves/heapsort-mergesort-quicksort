class HeapSort {

    public static void heapSort(int[] array) {
        int tam = array.length;

        // Constroi o heap
        for (int i = tam / 2 - 1; i >= 0; i--) {
            maxHeapifica(array, tam, i);
        }

        // Extrai um a um os elementos do heap
        for (int i = tam - 1; i > 0; i--) {
            troca(array, 0, i);

            // Chama maxHeapifica na heap reduzida
            maxHeapifica(array, i, 0);
        }
    }

    private static void troca(int[] array, int i, int j) {
        int aux = array[i];
        array[i] = array[j];
        array[j] = aux;
    }

    private static void maxHeapifica(int[] array, int tam, int pai) {
        int maior = pai;
        int esquerda = 2 * pai + 1;
        int direita = 2 * pai + 2;

        // Se o filho da esquerda é maior que a raiz
        if (esquerda < tam && array[esquerda] > array[maior])
            maior = esquerda;

        // Se o filho da direita é maior que o maior até agora
        if (direita < tam && array[direita] > array[maior])
            maior = direita;

        // Se o maior não é a raiz
        if (maior != pai) {
            troca(array, pai, maior);

            // Recursivamente heapifica a subárvore afetada
            maxHeapifica(array, tam, maior);
        }
    }
}
