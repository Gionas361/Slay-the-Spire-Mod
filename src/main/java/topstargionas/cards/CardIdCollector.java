package topstargionas.cards;

import org.reflections.Reflections;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class CardIdCollector {

    public static Set<String> getAllCardIds() {
        Set<String> cardIds = new HashSet<>();

        // Scan the base package
        Reflections reflections = new Reflections("topstargionas.cards");

        // Get all classes under that package
        Set<Class<?>> allCardClasses = reflections.getSubTypesOf(Object.class);

        for (Class<?> cls : allCardClasses) {
            try {
                Field idField = cls.getDeclaredField("ID");
                if (java.lang.reflect.Modifier.isStatic(idField.getModifiers())) {
                    String idValue = (String) idField.get(null);  // static field
                    cardIds.add(idValue);
                }
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
                // Skip classes without static ID field
            }
        }

        return cardIds;
    }

    public static void main(String[] args) {
        Set<String> ids = getAllCardIds();
        ids.forEach(System.out::println);
    }
}