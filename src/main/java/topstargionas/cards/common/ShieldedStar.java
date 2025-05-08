package topstargionas.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.vfx.combat.GainPowerEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerBuffEffect;
import topstargionas.cards.BaseCard;
import topstargionas.cards.CustomTags;
import topstargionas.character.TheCaretaker;
import topstargionas.util.CardStats;

public class ShieldedStar extends BaseCard {
    // makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    public static final String ID = makeID(ShieldedStar.class.getSimpleName());

    // Card Stats
    private static final CardStats info = new CardStats(
            TheCaretaker.Meta.CARD_COLOR, // The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, // The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, // Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, // The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    // These will be used in the constructor. Technically you can just use the values directly,
    // but constants at the top of the file are easy to adjust.
    private static final int BLOCK = 9;
    private static final int UPG_BLOCK = 5;
    private static final int DEX = 1;
    private static final int UPG_DEX = 1;
    private AbstractPlayer player;

    // This is what actually creates the card
    public ShieldedStar() {
        super(ID, info); // Pass the required information to the BaseCard constructor.

        setBlock(BLOCK, UPG_BLOCK); // Sets the card's damage and how much it changes when upgraded.
        setCustomVar("DEX", DEX, UPG_DEX);

        // Basic strikes and all strike cards are "tagged",
        // so that the game knows what they are for the purposes of relics like Pandora's Box,
        // or a card like Perfected Strike. These tags are added by calling tags.add.
    }

    public void CardEffects(AbstractPlayer User, AbstractCreature Target, int TimesToDo) {
        int x = 0;
        while (x < TimesToDo) {
            addToBot(new GainBlockAction(User, block));
            addToTop(new ApplyPowerAction(User, User, new DexterityPower(User, customVar("DEX")), customVar("DEX")));
            setCustomVar("DEX", 0);

            x++;
        }
    }

    // This code basically declares what the card will do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // There are three types of damage in the game. NORMAL, THORNS, and HP_LOSS.
        // ALL attacks deal NORMAL damage.
        // Any blockable damage that isn't from an attack is THORNS damage (such as from Thorns).
        // Damage that ignores block is HP_LOSS.
        CardEffects(p, m, 1);
    }
}