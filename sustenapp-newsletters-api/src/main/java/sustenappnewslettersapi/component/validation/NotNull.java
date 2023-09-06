package sustenappnewslettersapi.component.validation;

public class NotNull {
    public static boolean isValid(Object value){
        return !value.equals(null);
    }
}
