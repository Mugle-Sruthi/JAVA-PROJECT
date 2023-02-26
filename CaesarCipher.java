import java.util.Scanner;

public class CaesarCipher {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the plain text: ");
        String plainText = input.nextLine();

        System.out.print("Enter the shift: ");
        int shift = input.nextInt();

        String cipherText = encrypt(plainText, shift);
        System.out.println("Cipher text: " + cipherText);

        String decryptedText = decrypt(cipherText, shift);
        System.out.println("Decrypted text: " + decryptedText);

        input.close();
    }

    public static String encrypt(String plainText, int shift) {
        String cipherText = "";
        for (int i = 0; i < plainText.length(); i++) {
            char ch = plainText.charAt(i);
            if (ch >= 'a' && ch <= 'z') {
                ch = (char) (ch + shift);
                if (ch > 'z') {
                    ch = (char) (ch - 'z' + 'a' - 1);
                }
                cipherText += ch;
            } else if (ch >= 'A' && ch <= 'Z') {
                ch = (char) (ch + shift);
                if (ch > 'Z') {
                    ch = (char) (ch - 'Z' + 'A' - 1);
                }
                cipherText += ch;
            } else {
                cipherText += ch;
            }
        }
        return cipherText;
    }

    public static String decrypt(String cipherText, int shift) {
        String plainText = "";
        for (int i = 0; i < cipherText.length(); i++) {
            char ch = cipherText.charAt(i);
            if (ch >= 'a' && ch <= 'z') {
                ch = (char) (ch - shift);
                if (ch < 'a') {
                    ch = (char) (ch + 'z' - 'a' + 1);
                }
                plainText += ch;
            } else if (ch >= 'A' && ch <= 'Z') {
                ch = (char) (ch - shift);
                if (ch < 'A') {
                    ch = (char) (ch + 'Z' - 'A' + 1);
                }
                plainText += ch;
            } else {
                plainText += ch;
            }
        }
        return plainText;
    }
}