package sustenappnewslettersapi.component.validation;

public class NotEmpty {
    public static boolean isValid(String value){
        return !value.isEmpty();
    }
}
