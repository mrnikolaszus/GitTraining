import java.util.ArrayList;

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

    public static String brutForce(String encText) {    // Начало: Взлом BruteForce
        encText = encText.toLowerCase();
        String decText = ",,,,";
        int index = 33;
        while (!isDecrypted(encText)) {
            decText = "";
            for (int i = 0; i < encText.length(); i++) {
                int charPosition = alphabet.indexOf(encText.charAt(i));
                int keyVal = (charPosition - index) % 34;
                if (keyVal < 0) {
                    keyVal = alphabet.length() + keyVal;
                }
                char replaceChar = alphabet.charAt(keyVal);
                decText += replaceChar;
            }
            index--;
            if (isDecrypted(decText)) {
                return decText;
            }
            if (index < 0) {
                return "Не удалось расшифровать";
            }
        }
        return decText;
    }       // Конец: Взлом BruteForce

    public static String hacking(String encText) {   // Начало: Взлом по анализу
        String xxx = encText;
        // пробел - самый частый символ, 33 - index в алфавите
        // e - 2ой самый частый символ, 4 - index в алфавите
        // a - 3тий самый частый символ, 0 - индекс в алфавите
        // o - 4ый самый частый символ, - 14 индекс в алфавите
        char charOne = ' ';
        int countOne = 0;
        char charTwo = 'e';
        int countTwo = 0;
        char charThree = 'a';
        int countThree = 0;
        char charFour = 'o';
        int countFour = 0;
        char temp;
        int tempCount = 0;
        ArrayList<Character> chars = new ArrayList<Character>();   // для поиска самых частых символов используем массив
        for (char c : encText.toCharArray()) {
            chars.add(c);
        }
        for (int i = 0; i < chars.size(); i++) {
            temp = chars.get(i);
            int j = 0;
            while (j < chars.size()) {
                if (temp == chars.get(j)) {

                    tempCount++;
                    j++;
                } else j++;
            }
            if (tempCount > countOne) {
                charFour = charThree;
                countFour = countThree;
                charThree = charTwo;
                countThree = countTwo;
                charTwo = charOne;
                countTwo = countOne;
                charOne = temp;
                countOne = tempCount;
                tempCount = 0;
            } else if (tempCount < countOne && tempCount > countTwo) {
                charFour = charThree;
                countFour = countThree;
                charThree = charTwo;
                countThree = countTwo;
                charTwo = temp;
                countTwo = tempCount;
                tempCount = 0;
            } else if (tempCount < countOne && tempCount < countTwo && tempCount > countThree) {
                charFour = charThree;
                countFour = countThree;
                charThree = temp;
                countThree = tempCount;
                tempCount = 0;
            } else if (tempCount < countOne && tempCount < countTwo && tempCount < countThree && tempCount > countFour) {
                charFour = temp;
                countFour = tempCount;
                tempCount = 0;
            } else {
                tempCount = 0;
            }

            for (int k = 0; k < chars.size(); k++) {
                if (chars.get(k) == temp) {
                    chars.remove(k);
                    k--;
                }
            }
            i--;
        }

        ArrayList<Character> charsTest = new ArrayList<Character>(); // массив самых часто встреающихся чаров
        charsTest.add(charOne);
        charsTest.add(charTwo);
        charsTest.add(charThree);
        charsTest.add(charFour);

        ArrayList<Integer> keyValTests = new ArrayList<>(16);   // создаем массив возможных совпадений на основании символов (запишем туда 16 индексов дял проверки)
        for (int i = 0; i < charsTest.size(); i++) {

            int charPosition1 = alphabet.indexOf(charsTest.get(i));         // четыре самые частые буквы в шифре * четыре самые частые буквы (о,e,a,пробел) = 16 возможных
            int keyVal1 = (charPosition1 - alphabet.indexOf(' ')) % 34;
            if (keyVal1 < 0) {
                keyVal1 = alphabet.length() + keyVal1;
            }
            keyValTests.add(keyVal1);

            int charPosition2 = alphabet.indexOf(charsTest.get(i));
            int keyVal2 = (charPosition2 - alphabet.indexOf('e')) % 34;
            if (keyVal2 < 0) {
                keyVal2 = alphabet.length() + keyVal2;
            }
            keyValTests.add(keyVal2);

            int charPosition3 = alphabet.indexOf(charsTest.get(i));
            int keyVal3 = (charPosition3 - alphabet.indexOf('a')) % 34;
            if (keyVal3 < 0) {
                keyVal3 = alphabet.length() + keyVal3;
            }
            keyValTests.add(keyVal3);

            int charPosition4 = alphabet.indexOf(charsTest.get(i));
            int keyVal4 = (charPosition4 - alphabet.indexOf('o')) % 34;
            if (keyVal4 < 0) {
                keyVal4 = alphabet.length() + keyVal4;
            }
            keyValTests.add(keyVal4);
        }

        int[] keyValTry = new int[3];      // индексы(ключи шифра) которые появлялись чаще всего за 16 попыток, применим только самые частые и посмотрим на текст
        int tempMax = 0;
        int z = 0;
        for (int i = 0; i < keyValTests.size(); i++) {
            tempMax = keyValTests.get(i);
            keyValTests.remove(i);
            i++;
            if (keyValTests.contains(tempMax)) {
                keyValTry[z] = tempMax;
                z++;
            }
            if (z >= 3) {
                break;
            }
        }

        for (int i = 0; i < keyValTry.length; i++) {     // В итоге, проверяем индексы от самых частых совпадений на зашифрованном тексте
            if (isDecrypted(decrypt(xxx, keyValTry[i]))) {
                xxx = decrypt(xxx, keyValTry[i]);
                return xxx;
            }
        }
        return "не удалось расшифровать";
    }       // Конец: Взлом по анализу
    public static void main(String[] args) {

    }
}
