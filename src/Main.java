import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static boolean isArabic = true;

    public static void main(String[] args) throws Exception {
        Calculator calculator = new Calculator();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String givenString = br.readLine();

        Pattern operandPattern = Pattern.compile("[+\\-*/]+");
        Matcher operandMatcher = operandPattern.matcher(givenString);

        Operand operand = null;

        while (operandMatcher.find()) {
            operand = checkOperand(givenString.substring(operandMatcher.start(), operandMatcher.end()));
        }

        Pattern NumsPattern = Pattern.compile("\\d+");
        Matcher matcher = NumsPattern.matcher(givenString);
        List<String> numsList = new ArrayList<>();
        while (matcher.find()) {
            numsList.add(givenString.substring(matcher.start(), matcher.end()));
        }
        if (numsList.size() != 2) {
            NumsPattern = Pattern.compile("[VIX]+");
            matcher = NumsPattern.matcher(givenString);
            numsList.clear();
            while (matcher.find()) {
                numsList.add(givenString.substring(matcher.start(), matcher.end()));
            }
            if (numsList.size() != 2) {
                throw new Exception();
            }
            isArabic = false;
        }
        if (!isArabic) {
            List<Integer> nums = compileToArab(numsList);
            int x = nums.get(0);
            int y = nums.get(1);
            if (x < y && operand == Operand.MINUS) {
                throw new Exception();
            }
            int result = calculator.calculate(x, y, operand);
            String romeResult = compileToRome(result);
            System.out.println(romeResult);
        }
        else {
            List<Integer> nums = compileStringArabToInt(numsList);
            int x = nums.get(0);
            int y = nums.get(1);
            int result = calculator.calculate(x, y, operand);
            System.out.println(result);
        }
    }

    private static List<Integer> compileStringArabToInt(List<String> numsList) throws Exception {
        List<Integer> nums = new ArrayList<>();
        for (String num: numsList) {
            if (Integer.parseInt(num) < 1 || Integer.parseInt(num) > 10) {
                throw new Exception();
            }
            nums.add(Integer.parseInt(num));
        }
        return nums;
    }

    private static List<Integer> compileToArab(List<String> numsList) throws Exception {
        List<Integer> nums = new ArrayList<>();
        for (String num: numsList) {
            String romanNumeral = num.toUpperCase();
            int result = 0;

            List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

            int i = 0;

            while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
                RomanNumeral symbol = romanNumerals.get(i);
                if (romanNumeral.startsWith(symbol.name())) {
                    result += symbol.getValue();
                    romanNumeral = romanNumeral.substring(symbol.name().length());
                } else {
                    i++;
                }
            }

            if (result > 10) {
                throw new Exception();
            }

            if (romanNumeral.length() > 0) {
                throw new Exception();
            }

            nums.add(result);
        }
        return nums;
    }

    private static String compileToRome(int num) throws Exception {
        if ((num <= 0) || (num > 4000)) {
            throw new Exception();
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((num > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= num) {
                sb.append(currentSymbol.name());
                num -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }

    private static void checkIfMoreThanTenOrLessThanOne(int x) throws Exception {
        if (x > 10 || x < 1) {
            throw new Exception();
        }
    }

    private static Operand checkOperand(String operand) throws Exception {
        if (operand.equals("+")) {
            return Operand.PLUS;
        }
        if (operand.equals("-")) {
            return Operand.MINUS;
        }
        if (operand.equals("*")) {
            return Operand.MULTIPLY;
        }
        if (operand.equals("/")) {
            return Operand.DIVIDE;
        }
        throw new Exception();
    }
}
