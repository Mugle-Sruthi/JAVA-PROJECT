import javax.crypto.*;
import java.security.SecureRandom;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.*;

class cryptography {
    public static SecretKey generateSymKey(String algo, int bitn) throws Exception {
        KeyGenerator keyGen;
        SecureRandom ranNum = new SecureRandom();
        keyGen = KeyGenerator.getInstance(algo);
        keyGen.init(bitn, ranNum);
        return keyGen.generateKey();
    }

    public static String encrypt(SecretKey key1, String plaintext, String algo) throws Exception {
        Cipher algo_decl;
        if (algo.equals("AES"))
            algo_decl = Cipher.getInstance("AES/ECB/PKCS5Padding");
        else
            algo_decl = Cipher.getInstance("DES/ECB/PKCS5Padding");
        algo_decl.init(Cipher.ENCRYPT_MODE, key1);
        byte[] byte_arr = algo_decl.doFinal(plaintext.getBytes());
        System.out.println();
        System.out.print("The encrypted string is : ");
        // for(byte i: byte_arr) System.out.print(i+" ");
        String encrypted_message = Base64.getEncoder().encodeToString(byte_arr);
        System.out.println(encrypted_message);
        return encrypted_message;
    }

    public static void decrypt(String hex_key, String encrypted_text, String algo) throws Exception {
        byte[] byte_key_array = DatatypeConverter.parseHexBinary(hex_key);
        Cipher algo_decl;
        SecretKeySpec key;
        if (algo.equals("AES")) {
            algo_decl = Cipher.getInstance("AES/ECB/PKCS5Padding");
            key = new SecretKeySpec(byte_key_array, "AES");
        } else {
            algo_decl = Cipher.getInstance("DES/ECB/PKCS5Padding");
            key = new SecretKeySpec(byte_key_array, "DES");
        }

        algo_decl.init(Cipher.DECRYPT_MODE, key);
        byte[] ciphertextBytes = Base64.getDecoder().decode(encrypted_text);
        byte[] plaintextBytes = algo_decl.doFinal(ciphertextBytes);
        String plaintext = new String(plaintextBytes);
        System.out.println("The decrypted text is : " + plaintext);
    }
}

public class SymCrypt {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1.encryption\n2.decryption");
            int n2 = sc.nextInt();
            if (n2 != 1 && n2 != 2) {
                System.out.println("Exiting.........");
                break;
            }
            System.out.println("1.AES\n2.DES(56 bits)");
            int n = sc.nextInt(), bitn = 56, n1 = 0;
            String algo;
            if (n == 1) {
                algo = "AES";
                System.out.println("1.256 bits\n2.128 bits");
                n1 = sc.nextInt();
                if (n1 == 1)
                    bitn = 256;
                else
                    bitn = 128;
            } else
                algo = "DES";

            sc.nextLine();
            if (n2 == 1) {
                System.out.println("enter the string to be encrypted : ");
                String to_encrypt = sc.nextLine();
                SecretKey SymmetricKey = cryptography.generateSymKey(algo, bitn);
                byte[] arr = SymmetricKey.getEncoded();
                // System.out.println("The key in bit array is : ");
                System.out
                        .println("The key in hex is : " + DatatypeConverter.printHexBinary(SymmetricKey.getEncoded()));
                for (byte i : arr)
                    System.out.print(i + " ");
                cryptography.encrypt(SymmetricKey, to_encrypt, algo);
            } else if (n2 == 2) {
                System.out.println("Enter the key(in hexadecimal string) :");
                String hex_key = sc.nextLine();
                System.out.println("Enter the cypher text :");
                String cypher_text = sc.nextLine();
                cryptography.decrypt(hex_key, cypher_text, algo);
            }

        }
    }
}