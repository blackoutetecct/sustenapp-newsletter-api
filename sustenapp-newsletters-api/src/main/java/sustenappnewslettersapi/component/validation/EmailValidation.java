package sustenappnewslettersapi.component.validation;

public class EmailValidation {
    public static boolean isValid(String value){
        return value.contains("@") && value.contains(".com");
    }
}
