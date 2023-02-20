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

    public static boolean isDecrypted(String text) {        // Начало: Анализ стилистики текста, расшифрован ли текст?
        text = text.toLowerCase();
        int badStyle = 0;                     // проверяем как часто, после знаков препинаний не было пробела
        int noVowels = 0;                     // проверяем как часто в словах нет гласных (последовательность букв без гласных)
        int noSpaces = 0;                    // проверяем вообще как часто слова делятся пробелами
        char[] vowels = {'a', 'e', 'i', 'u', 'o'};
        for (int i = 1; i < text.length() - 1; i++) {
            if (text.charAt(i) == ',' && text.charAt(i + 1) != ' ') {
                badStyle = badStyle + 2;
            }
            if (text.charAt(i) != ' ') {
                noSpaces++;
            }
            if (text.charAt(i) == ' ') {
                noSpaces = 0;
            }
            if (noSpaces > 15) {
                return false;
            }
            if (text.charAt(i) == '!' && text.charAt(i + 1) != ' ') {
                badStyle++;
            }
            if (text.charAt(i) == '?' && text.charAt(i + 1) != ' ') {
                badStyle++;
            }
            if (text.charAt(i) == ':' && text.charAt(i + 1) != ' ') {
                badStyle++;
            }
            if (text.charAt(i) == '.' && text.charAt(i + 1) != ' ') {
                badStyle++;
            }
            if (text.charAt(i) == '"' && (text.charAt(i + 1) != ' ' || text.charAt(i - 1) != ' ')) {
                badStyle++;
            }

            if (noVowels > 10) {
                badStyle = badStyle + 2;
                noVowels = 0;
            }
            String test = String.valueOf(text.charAt(i));
            if (!(new String(vowels).contains(test))) {
                noVowels++;
            } else {
                noVowels = 0;
            }
        }

        return badStyle < 2; // чем выше индекс задаем, тем больше ошибок в тексте допустимо, но тем больше шанс, что не определит, что текст расшифрован.
    }       // Конец: Анализ стилистики текста, расшифрован ли текст?

    public static void main(String[] args) {

    }
}
