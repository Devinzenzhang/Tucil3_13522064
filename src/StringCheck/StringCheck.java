package StringCheck;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class StringCheck {
    // validasi string
    public static boolean checkSameLength(String src, String dest){
        if (src.length() != dest.length()){
            System.out.println("Kedua kata panjangnya harus sama!!!");
            return false;
        }
        return true;
    }

    public static boolean checkValid (String s){
        if ((s.length() < 2) || (s.length() > 8)){
            System.out.println("Katanya harus punya panjang 2 sampai 8 huruf!!!");
            return false;
        }
        if (s.matches(".*[^a-z].*")){
            System.out.println("Kata tidak boleh mengandung angka atau karakter lain selain huruf!!!");
            return false;
        }
        return true;
    }

    // baca line dari file txt
    public static String getWordAtLine(String path, long line) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.lines().skip(line).findFirst().orElse(null);
        }
    }

    // cek binary search
    public static boolean checkDictionary (String path, String s, int mode){
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            long bottom = 0;
            long top = br.lines().count() - 1;
            long mid;
            String word;
            while (bottom <= top) {
                mid = (bottom + top) / 2;
                word = getWordAtLine(path, mid);
                if (word == null){return false;}
                int cumpare = s.compareTo(word);
                if (cumpare == 0) {
                    return true;
                } else if (cumpare < 0) {
                    top = mid - 1;
                } else {
                    bottom = mid + 1;
                }
            }
        } catch (IOException e) {
            System.out.println("File tidak ditemukan, tidak bisa validasi kata...");
            return false;
        }
        if (mode == 1) {System.out.println("Kata tidak ditemukan di dictionary!!!");}
        return false;
    }

    // hitung estimasi f(n)
    // kedua string lengthnya sama
    public static int countEstimate(String word, String dest){
        int fn = 0;
        for (int i = 0; i < word.length(); i++){
            if (word.charAt(i) != dest.charAt(i)){fn++;}
        }
        return fn;
    }
}