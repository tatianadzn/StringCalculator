import java.util.ArrayDeque;
import java.util.Deque;

public class ReversePolishNotation {
    private static Deque<String> outputStack = new ArrayDeque<>();
    private static Deque<String> helper = new ArrayDeque<>();


    public static Deque<String> convert(String initString){

        /*
         * parse string and deal with each token
         * according to the main RPN algorithm;
         * then move in reverse everything from helper to the output stack
         *
         */
        outer:
        for(int i = 0; i < initString.length(); i++){
            char c = initString.charAt(i);
            int temp = 0;
            while (c > '0' && c <= '9'){
                temp = temp * 10 + Character.digit(c, 10);

                /*
                 * if string ends, last token;
                 * straight goto moving from helper to output and return;
                 * TODO: rewrite later?
                 *
                 */
                if (i == initString.length() - 1){
                    dealWithToken(String.valueOf(temp));
                    break outer;
                }

                c = initString.charAt(++i);
            }
            if (temp != 0){
                dealWithToken(String.valueOf(temp));
            }
            dealWithToken(String.valueOf(c));
        }

        while (!helper.isEmpty()){
            outputStack.push(helper.pop());
        }


        return outputStack;

    }


    private static void dealWithToken(String string) {
        /*
          * Reverse Polish Notation Algorithm on token
          *
          */
        if (CheckIfNumeric.isNumeric(string)){
            /*
             * if number then push into the output stack
             *
             */
            outputStack.push(string);
        }
        else if(string.equals("(")){
            /*
             * if '(' then push into the helper stack
             *
             */
            helper.push(string);
        }
        else if (string.equals("*") || string.equals("/")){
                /*
                  * if '*' or '/' then while at the top of the helper is '/' or '*'
                  * get them from helper to the output stack;
                  * then push new operation to the helper stack
                  *
                  */
            while (!helper.isEmpty() && (helper.peekFirst().equals("/") || helper.peekFirst().equals("*")))
                outputStack.push(helper.pop());

            helper.push(string);
        }
        else if (string.equals("+") || string.equals("-")){
                /*
                 * if '+' or '-' then while at the top of the helper is '/' or '*' or '+' or '-'
                 * (all except '(' currently)
                 * get them from helper to the output stack;
                 * then push new operation to the helper stack
                 *
                 */
            while (!helper.isEmpty() && !helper.peekFirst().equals("("))
                outputStack.push(helper.pop());

            helper.push(string);
        }
        else if (string.equals(")")){
                /*
                 * if ')' then until at the top of the helper is '('
                 * get operations from helper to the output stack;
                 * then push new operation to the helper stack
                 *
                 */
            try{
                while(!helper.peekFirst().equals("("))
                    outputStack.push(helper.pop());
                helper.pop();
            }catch (Exception e){
                System.out.println("wrong input");
                throw e;
            }
        }
    }
}
