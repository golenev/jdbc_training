package utils;

public class StringUtils {
    public static StringBuilder buildSomeString (Object ... parts){
        StringBuilder stringBuilder = new StringBuilder();
        for (Object part : parts) {
            stringBuilder.append(part);
        }
        return stringBuilder;
    }
}
