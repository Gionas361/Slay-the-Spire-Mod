package topstargionas.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import topstargionas.cards.BaseCard;
import topstargionas.character.TheCaretaker;
import topstargionas.util.CardStats;

public class MoonlightRebound extends BaseCard {
    // makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    public static final String ID = makeID(MoonlightRebound.class.getSimpleName());

    // Card Stats
    private static final CardStats info = new CardStats(
            TheCaretaker.Meta.CARD_COLOR, // The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, // The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, // Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, // The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    // These will be used in the constructor. Technically you can just use the values directly,
    // but constants at the top of the file are easy to adjust.
    private static final int DRAWMOUNT = 1;
    private static final int UPG_DRAWMOUNT = 2;
    private AbstractPlayer player;

    // This is what actually creates the card
    public MoonlightRebound() {
        super(ID, info); // Pass the required information to the BaseCard constructor.

        setCustomVar("CARDDRAW", DRAWMOUNT, UPG_DRAWMOUNT);

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
        int x = 0;
        int enTotal = 0;
        AbstractCard c = this;

        while (x < customVar("CARDDRAW")) {
            if (!p.discardPile.isEmpty()) addToTop(new DiscardToHandAction(c = p.discardPile.getRandomCard(true)));

            enTotal += c.cost;

            x++;
        }

        x = 0;
        addToBot(new DamageAction(m, new DamageInfo(p, enTotal + 3, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }
}