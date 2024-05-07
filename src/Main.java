import java.util.Scanner;
import StringCheck.*;
import SearchAlgorithm.*;

public class Main {
    public static void main (String[] Args){
        // input
        Scanner scanner = new Scanner(System.in);
        String path = "../dictionary/dictionary.txt";
        System.out.println("Masukkan kata awal:");
        String start = scanner.nextLine();
        start = start.toLowerCase();
        while (!(StringCheck.checkValid(start) && StringCheck.checkDictionary(path, start, 1))){
            System.out.println("Masukkan kata awal:");
            start = scanner.nextLine();
            start = start.toLowerCase();
        }
        System.out.println("Masukkan kata yang ingin dicapai:");
        String dest = scanner.nextLine();
        dest = dest.toLowerCase();
        while (!(StringCheck.checkValid(dest) && StringCheck.checkSameLength(start, dest) && StringCheck.checkDictionary(path, dest, 1))){
            System.out.println("Masukkan kata yang ingin dicapai:");
            dest = scanner.nextLine();
            dest = dest.toLowerCase();
        }
        System.out.println("Pilih algoritma, masukkan angka diantara 1-3:\n1. Uniform Cost Search\n2. Greedy Best First Search\n3. A*");
        int choice = scanner.nextInt();
        while ((choice < 1) || (choice > 3)){
            System.out.println("Pilih algoritma, masukkan angka diantara 1-3:\n1. Uniform Cost Search\n2. Greedy Best First Search\n3. A*");
            choice = scanner.nextInt();
        }

        long startTime = System.nanoTime();

        // proses
        SearchAlgorithm.AddNode(new StringNode(start, 0, StringCheck.countEstimate(start, dest), -1));
        SearchAlgorithm.AddTask(0);
        int result;
        if (start.equals(dest)){result = 0;}
        else if (choice == 1){result = SearchAlgorithm.UCS(dest, path);}
        else if (choice == 2){result = SearchAlgorithm.Greedy(dest, path);}
        else {result = SearchAlgorithm.AStar(dest, path);}

        long endTime = System.nanoTime();
        long exeTime = (endTime - startTime) / 1000000;

        // output
        if (result == -1){System.out.println("Tidak ada solusi yang ditemukan");} else {
            System.out.println("Solusi ditemukan:");
            SearchAlgorithm.printPath(result);
        }
        System.out.println("Banyak node yang dikunjungi: " + SearchAlgorithm.getVisitedNodes());
        System.out.println("Waktu eksekusi: " + (exeTime) + " ms");
    }
}