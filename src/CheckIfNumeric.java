public class CheckIfNumeric {
    public static boolean isNumeric(String string){
        try{
            Double.parseDouble(string);
        } catch (Exception e){
            return false;
        }
        return true;
    }
}
