public class Calculator {
    public int calculate(int x, int y, Operand operand) throws Exception {
        if (operand == Operand.PLUS) {
            return x + y;
        }
        if (operand == Operand.MINUS) {
            return x - y;
        }
        if (operand == Operand.MULTIPLY) {
            return x * y;
        }
        if (operand == Operand.DIVIDE) {
            return x / y;
        }
        throw new Exception();
    }
}
