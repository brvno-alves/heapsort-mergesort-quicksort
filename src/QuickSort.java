/**
 * O Quick Sort utiliza uma estratégia de dividir para conquistar,
 * onde a recursão é utilizada para dividir o vetor em partições menores.
 * Se não houver um controle adequado,
 * a recursão pode se tornar muito profunda e levar a um estouro de pilha "StackOverflowError".
 * Para o pior caso, onde o vetor está ordenado de forma decrescente,
 * foi implementado uma versão iterativa do QuickSort.
 * Isso elimina o risco de ocorrer StackOverflowError.
 * Para casos em que a recursão é inevitável,
 * podemos aumentar o tamanho da pilha de recursão do Java usando a opção -Xss (tamanho da pilha).
 * No entanto, isso é menos preferível,
 * pois limita a portabilidade e pode não resolver completamente o problema.
 * */

public class QuickSort {
    static void quickSort(int[] vetor) {
        int inicio = 0;
        int fim = vetor.length - 1;

        // Cria uma pilha para o Quick Sort iterativo
        int[] pilha = new int[fim - inicio + 1];

        // Inicializa o topo da pilha
        int topo = -1;

        // Empilha os valores inicial e final
        pilha[++topo] = inicio;
        pilha[++topo] = fim;

        // Processa os elementos da pilha
        while (topo >= 0) {
            // Desempilha o topo e o final
            fim = pilha[topo--];
            inicio = pilha[topo--];

            // Configura o pivo adequado
            int pivo = particionar(vetor, inicio, fim);

            // Se houver elementos à esquerda do pivo, adicione à pilha
            if (pivo - 1 > inicio) {
                pilha[++topo] = inicio;
                pilha[++topo] = pivo - 1;
            }

            // Se houver elementos à direita do pivo, adicione à pilha
            if (pivo + 1 < fim) {
                pilha[++topo] = pivo + 1;
                pilha[++topo] = fim;
            }
        }
    }

    static int particionar(int[] vetor, int inicio, int fim) {
        int pivo = vetor[inicio];
        int i = inicio + 1, f = fim;

        while (i <= f) {
            if (vetor[i] <= pivo)
                i++;
            else if (pivo < vetor[f])
                f--;
            else {
                int troca = vetor[i];
                vetor[i] = vetor[f];
                vetor[f] = troca;
                i++;
                f--;
            }
        }
        vetor[inicio] = vetor[f];
        vetor[f] = pivo;
        return f;
    }
}

