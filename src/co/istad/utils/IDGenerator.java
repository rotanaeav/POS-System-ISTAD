package co.istad.utils;

public class IDGenerator {
    public static String generateNextId(String lastId, String prefix) {
        if (lastId == null || lastId.isEmpty()) {
            return prefix + "0001";
        }
        try {
            String getNum = lastId.replace(prefix, "");
            int id = Integer.parseInt(getNum);
            id++;
            return String.format("%s%04d", prefix, id);

        } catch (NumberFormatException e) {
            return prefix + "Error";
        }
    }
}
