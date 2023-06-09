import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class HashDictionnary extends Hashing {
    public HashDictionnary(){}
   


    @Override
    public  String getPassword(String hash){
         File passwordsFile=new File("../doc/1000-passwords.csv");
        int hashLenght=hash.length();
        Random random = new Random();
        boolean isFound=false;

        //on recupere d'abords tous les mots de passe dans une liste de chaine de caracteres
        List<String> passwords = readCSV(passwordsFile.getPath(),10);
        int randomIndex;
        String randomPassword="not found";

        while (!isFound) {

            randomIndex = random.nextInt(passwords.size());
            System.out.println("index"+randomIndex+"\t");
            randomPassword=passwords.get(randomIndex);
            System.out.println("test taille"+hashLenght);
            System.out.println("randomPassword:"+randomPassword);

            // on va faire un controle pour verifier le type de hash fournis et quel traitement faire

            switch (hashLenght) {
                case 32:{
                        if(hash.equals(getHash(randomPassword,"MD5")) )
                            isFound=true;
                    break;
                }
                case 64:
{
                    if(hash.equals(getHash(randomPassword,"SHA-256")) )
                        isFound=true;
                    break;
}

                default:
                
                    return "Hash nom prise en compte";

            }
    
        }
        return randomPassword;
    }

    // la fonction qui permet de lire les documents csv 

    private static List<String> readCSV(String filePath, int limit) {
    List<String> lines = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        int count = 0; // Counter to track the number of lines read
        while ((line = reader.readLine()) != null && count <= limit) {
            if (count > 1) {
                String[] parts = line.split(","); // Split the line by comma
                if (parts.length > 1) {
                    lines.add(parts[1]); // Add the text after the comma to the list
                }
            }
            count++;
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return lines;
}


    // private static List<String> readCSV(String filePath, int limit) {
    //     List<String> lines = new ArrayList<>();
    //     try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
    //         String line;
    //         int count = 0; // Compteur pour suivre le nombre de mots lus
    //         while ((line = reader.readLine()) != null && count <= limit) {
    //             if (count > 1) {
    //                 lines.add(line);
    //             }
    //             count++;
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return lines;
    // }
    

    //la fonction qui permet d'hasher en SHAH-256 ou MD5 
    public static String getHash(String password,String type) {
        try {
            
            // Créer une instance de MessageDigest avec l'algorithme correspondant
            MessageDigest md = MessageDigest.getInstance(type);

            // Calculer le hachage MD5
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            System.out.println(sb.toString());
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    
}
