public class Main {

    public static final String alphabet = "abcdefghijklmnopqrstuvwxyz.,\":-!? ";
    // test commit
    public static String encrypt(String text, int shiftKey) {  // Начало: Шифрования
        text = text.toLowerCase();
        String decText = "";
        for (int i = 0; i < text.length(); i++) {
            int charPosition = alphabet.indexOf(text.charAt(i));
            int keyVal = (shiftKey + charPosition) % 34;
            char replaceVal = alphabet.charAt(keyVal);
            decText += replaceVal;
        }
        return decText;
    }   // Конец: Шифрования

    public static String decrypt(String encText, int shiftKey) {   // Начало: Дешифровки
        encText = encText.toLowerCase();
        String decText = "";
        for (int i = 0; i < encText.length(); i++) {
            int charPosition = alphabet.indexOf(encText.charAt(i));
            int keyVal = (charPosition - shiftKey) % 34;
            if (keyVal < 0) {
                keyVal = alphabet.length() + keyVal;
            }
            char replaceChar = alphabet.charAt(keyVal);
            decText += replaceChar;
        }
        return decText;
    }       // Конец: Дешифровки


    public static void main(String[] args) {

    }
}
