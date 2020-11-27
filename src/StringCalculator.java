import java.util.List;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

 /*
    сначала конкретно под +-* и /, а потом можно создавать свои операции с приоритетами
    типа приоритет, суф преф или бинарная и прочее
    + поддержка floating point
 */

public class StringCalculator {
    private static Deque<String> stackRPN = new ArrayDeque<>();
    private static List<String> list;

    public static void main(String[] args) {

        String test1 = "12*(45*(67*9)*(78/7/8/9))*67-47+90";
        String test2 = "789*7-9+((87+8)/9)-89*(78/(9+2))";
        String test3 = "12*1+9+1/8";


        stackRPN = ReversePolishNotation.convert(test3);
        double result = calculate();
        System.out.printf("%.4f", result);
    }


    private static double calculate(){
        list = new ArrayList<>(stackRPN);
        Collections.reverse(list);

        while (true){
            int i = getNextOperation();
            if (i == -1)
                break;

            double a = Double.parseDouble(list.get(i - 1));
            double b = Double.parseDouble(list.get(i - 2));

            switch (list.get(i)){
                case "+" :
                    helper(i, a + b);
                    break;
                case "-" :
                    helper(i, b - a);
                    break;
                case "*" :
                    helper(i, a * b);
                    break;
                case "/" :
                    helper(i, b / a);
                    break;
            }
        }

        return Double.parseDouble(list.get(0));
    }

    private static int getNextOperation(){
        /*
         * return index of the first operation in the list
         * or -1 if none is present
         *
         */
        for (int i = 0; i < list.size(); i++){
            if (!CheckIfNumeric.isNumeric(list.get(i)))
                return i;
        }
        return -1;
    }

    private static void helper(int i, double result){
        list.set(i, String.valueOf(result));
        list.remove(i - 1);
        list.remove(i - 2);
    }
}
