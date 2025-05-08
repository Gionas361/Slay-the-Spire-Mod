package topstargionas.cards;

import org.reflections.Reflections;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.core.AbstractCreature;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

public class CardDispatcher {

    public static void triggerCardEffect(String cardId, AbstractPlayer user, AbstractCreature target, int timesToDo) {
        Reflections reflections = new Reflections("topstargionas.cards");
        Set<Class<?>> cardClasses = reflections.getSubTypesOf(Object.class);

        for (Class<?> cls : cardClasses) {
            try {
                // Look for public static final String ID field
                Field idField = cls.getDeclaredField("ID");
                if (!Modifier.isStatic(idField.getModifiers())) continue;

                String foundId = (String) idField.get(null);
                if (!foundId.equals(cardId)) continue;

                // Try to call CardEffects(AbstractPlayer, AbstractCreature, int)
                Method effectMethod;

                try {
                    effectMethod = cls.getMethod("CardEffects", AbstractPlayer.class, AbstractCreature.class, int.class);
                } catch (NoSuchMethodException e) {
                    System.err.println("Card class found but no CardEffects method: " + cls.getName());
                    return;
                }

                // If static method
                if (Modifier.isStatic(effectMethod.getModifiers())) {
                    effectMethod.invoke(null, user, target, timesToDo);
                } else {
                    // Call on instance
                    Object instance = cls.getDeclaredConstructor().newInstance();
                    effectMethod.invoke(instance, user, target, timesToDo);
                }

                return;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.err.println("Card ID not found: " + cardId);
    }
}