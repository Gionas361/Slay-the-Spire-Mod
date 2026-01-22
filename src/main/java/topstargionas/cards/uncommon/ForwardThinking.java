package topstargionas.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import topstargionas.cards.BaseCard;
import topstargionas.cards.CustomTags;
import topstargionas.character.TheCaretaker;
import topstargionas.powers.BurnTheStars;
import topstargionas.util.CardStats;

public class ForwardThinking extends BaseCard {
    // makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    public static final String ID = makeID(ForwardThinking.class.getSimpleName());

    // Card Stats
    private static final CardStats info = new CardStats(
            TheCaretaker.Meta.CARD_COLOR, // The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, // The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, // Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, // The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    // These will be used in the constructor. Technically you can just use the values directly,
    // but constants at the top of the file are easy to adjust.
    private static final int EN = 2;
    private static final int UPG_EN = 2;
    private AbstractPlayer player;

    // This is what actually creates the card
    public ForwardThinking() {
        super(ID, info); // Pass the required information to the BaseCard constructor.

        setCustomVar("EN", EN, UPG_EN);

        // Basic strikes and all strike cards are "tagged",
        // so that the game knows what they are for the purposes of relics like Pandora's Box,
        // or a card like Perfected Strike. These tags are added by calling tags.add.
        if (this.upgraded) this.tags.add(CustomTags.BURNETH);
    }

    public void CardEffects(AbstractPlayer User, AbstractCreature Target, int TimesToDo) {
        int x = 0;
        while (x < TimesToDo) {
            addToBot(new GainEnergyAction(customVar("EN")));
            if (this.upgraded) addToBot(new ApplyPowerAction(User, User, new BurnTheStars(User, 2)));

            x++;
        }
    }

    private boolean trigger;
    private AbstractPlayer pl;
    private AbstractMonster mo;

    // This code basically declares what the card will do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // There are three types of damage in the game. NORMAL, THORNS, and HP_LOSS.
        // ALL attacks deal NORMAL damage.
        // Any blockable damage that isn't from an attack is THORNS damage (such as from Thorns).
        // Damage that ignores block is HP_LOSS.
        trigger = true;
        pl = p;
        mo = m;
    }

    @Override
    public void atTurnStart() {
        if (trigger) CardEffects(pl, mo, 1);
        trigger = false;
    }
}