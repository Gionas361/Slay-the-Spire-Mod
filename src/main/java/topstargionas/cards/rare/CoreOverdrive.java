package topstargionas.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import topstargionas.cards.BaseCard;
import topstargionas.character.TheCaretaker;
import topstargionas.util.CardStats;

public class CoreOverdrive extends BaseCard {
    // makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    public static final String ID = makeID(CoreOverdrive.class.getSimpleName());

    // Card Stats
    private static final CardStats info = new CardStats(
            TheCaretaker.Meta.CARD_COLOR, // The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.POWER, // The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, // Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, // The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    // These will be used in the constructor. Technically you can just use the values directly,
    // but constants at the top of the file are easy to adjust.
    private static final int ENRECOVERY = 1;
    private static final int UPG_ENRECOVERY = 1;
    private int FINALREGEN;

    // This is what actually creates the card
    public CoreOverdrive() {
        super(ID, info); // Pass the required information to the BaseCard constructor.

        exhaust = true;
        setCostUpgrade(4);

        if (upgraded) {
            FINALREGEN = UPG_ENRECOVERY + 1;
        } else {
            FINALREGEN = ENRECOVERY;
        }
        setCustomVar("FINALREGEN", ENRECOVERY, UPG_ENRECOVERY);

        // Basic strikes and all strike cards are "tagged",
        // so that the game knows what they are for the purposes of relics like Pandora's Box,
        // or a card like Perfected Strike. These tags are added by calling tags.add.
    }

    // This code basically declares what the card will do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // There are three types of damage in the game. NORMAL, THORNS, and HP_LOSS.
        // ALL attacks deal NORMAL damage.
        // Any blockable damage that isn't from an attack is THORNS damage (such as from Thorns).
        // Damage that ignores block is HP_LOSS.
        addToBot(new ApplyPowerAction(p, p, new topstargionas.powers.CoreOverdrivePower(p, customVar("FINALREGEN"))));
    }
}