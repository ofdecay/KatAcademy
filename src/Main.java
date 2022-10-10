import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[] keys = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        Integer[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Scanner sсanner = new Scanner(System.in);
        System.out.println("Введите выражение");
        String string = sсanner.nextLine();
        System.out.println(calc(string));
    }

    public static String calc(String input) {
        String[] keys = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        Integer[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        String done = new String();
        String woutSpaces = input.replaceAll("\\s", "");
        if (woutSpaces.length() < 3) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("throws Exception //т.к. строка не является математической операцией");
                System.exit(0);
            }
        }
        String[] twoNumbers = woutSpaces.split("[+-/*]");
        if (twoNumbers.length != 2) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда (целые числа от 1 до 10 включительно) и один оператор (+, -, /, *)");
                System.exit(0);
            }
        }
        Check toCheck = new Check();
        Result1 toCalculate = new Result1();
        Mapper converter = new Mapper();
        ArToRom metamorph = new ArToRom();
        if (toCheck.checker(twoNumbers[0]) && toCheck.checker(twoNumbers[1]) && Arrays.asList(values).contains(Integer.parseInt(twoNumbers[0])) && Arrays.asList(values).contains(Integer.parseInt(twoNumbers[1]))) {
            int num1 = Integer.parseInt(twoNumbers[0]);
            int num2 = Integer.parseInt(twoNumbers[1]);
            int doneInt = toCalculate.rawResult(num1, num2, woutSpaces);
            done = Integer.toString(doneInt);
            return done;
        } else if(Arrays.asList(keys).contains(twoNumbers[0]) && Arrays.asList(keys).contains(twoNumbers[1])){
            String rom1 = twoNumbers[0].toString();
            String rom2 = twoNumbers[1].toString();
            if (Arrays.asList(keys).contains(rom1) && Arrays.asList(keys).contains(rom2)) {
                int newArabNum1 = Integer.parseInt(converter.toMap(keys, values).get(twoNumbers[0]).toString());
                int newArabNum2 = Integer.parseInt(converter.toMap(keys, values).get(twoNumbers[1]).toString());
                int semiDone = toCalculate.rawResult(newArabNum1, newArabNum2, woutSpaces);
                done = metamorph.arabToRom(semiDone);
                return done;
            }
        } else {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда (целые числа от 1 до 10 включительно) и один оператор (+, -, /, *)");
                System.exit(0);
            }
        }
        return done;
    }
}

class ArToRom {
    String arabToRom(int toConvert) {
        String[] keys = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        String resultRoman = new String();
        if(toConvert <= 10 && toConvert > 0) {
            resultRoman = keys[toConvert-1].toString();
        } else if (toConvert > 10 && toConvert < 100){
            String[] strProv = Integer.toString(toConvert).split("");
            resultRoman = tens(strProv[0]) + units(strProv[1]); //результат, если больше 10 и меньше 100
        } else if (toConvert == 100) {
            resultRoman = "C";
        } else if (toConvert < 0){
            try {
                throw new ArrayIndexOutOfBoundsException();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("throws Exception //т.к. в римской системе нет отрицательных чисел");
                System.exit(0);
            }
        }
        return resultRoman;
    }

    String tens(String string) {
        String c1 = new String();
        switch(string) {
            case "1": c1 = "X";
                break;
            case "2": c1 = "XX";
                break;
            case "3": c1 = "XXX";
                break;
            case "4": c1 = "XL";
                break;
            case "5": c1 = "L";
                break;
            case "6": c1 = "LX";
                break;
            case "7": c1 = "LXX";
                break;
            case "8": c1 = "LXXX";
                break;
            case "9": c1 = "XC";
                break;
        }
        return c1;
    }

    String units(String string) {
        String c2 = new String();
        switch(string) {
            case "1": c2 = "I";
                break;
            case "2": c2 = "II";
                break;
            case "3": c2 = "III";
                break;
            case "4": c2 = "IV";
                break;
            case "5": c2 = "V";
                break;
            case "6": c2 = "VI";
                break;
            case "7": c2 = "VII";
                break;
            case "8": c2 = "VIII";
                break;
            case "9": c2 = "IX";
                break;
        }
        return c2;
    }
}

class Mapper {

    HashMap toMap(String[] keys, Integer[] values) {
        int keysSize = keys.length;
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < keysSize; i++) {
            map.put(keys[i], values[i]);
        }
        return map;
    }
}
class Check {
    boolean checker (String string) {
        int intValue;
        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

class Result1 {
    int rawResult(int x, int y, String string){
        int rez = 0;
        if (string.contains("+")) {
            rez = (x+y);
        } else if (string.contains("-")) {
            rez = (x-y);
        } else if (string.contains("/")) {
            rez = (x/y);
        } else if (string.contains("*")) {
            rez = (x*y);
        }
        return rez;
    }
}