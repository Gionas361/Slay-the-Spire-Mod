package topstargionas.cards.rare;

import basemod.BaseMod;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import topstargionas.cards.BaseCard;
import topstargionas.cards.rare.moonStanceSlashes.CrescentMoonSlash;
import topstargionas.cards.rare.moonStanceSlashes.MoonlightSlash;
import topstargionas.character.TheCaretaker;
import topstargionas.powers.BurnTheStars;
import topstargionas.util.CardStats;

public class MoonStance extends BaseCard {
    // makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    public static final String ID = makeID(MoonStance.class.getSimpleName());

    // Card Stats
    private static final CardStats info = new CardStats(
            TheCaretaker.Meta.CARD_COLOR, // The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, // The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, // Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, // The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    // These will be used in the constructor. Technically you can just use the values directly,
    // but constants at the top of the file are easy to adjust.
    private boolean CTP;
    private boolean Unhovered;

    // This is what actually creates the card
    public MoonStance() {
        super(ID, info); // Pass the required information to the BaseCard constructor.

        setCostUpgrade(0);

        Unhovered = false;
        CTP = false;
        this.cardsToPreview = new CrescentMoonSlash();

        // Basic strikes and all strike cards are "tagged",
        // so that the game knows what they are for the purposes of relics like Pandora's Box,
        // or a card like Perfected Strike. These tags are added by calling tags.add.
    }

    @Override
    public void unhover() {
        if (!Unhovered) {
            if (CTP) {
                this.cardsToPreview = new MoonlightSlash();
                if (this.upgraded) this.cardsToPreview.upgrade();
                CTP = false;
            } else if (!CTP) {
                this.cardsToPreview = new CrescentMoonSlash();
                if (this.upgraded) this.cardsToPreview.upgrade();
                CTP = true;
            }
        }

        Unhovered = true;
    }

    // This code basically declares what the card will do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // There are three types of damage in the game. NORMAL, THORNS, and HP_LOSS.
        // ALL attacks deal NORMAL damage.
        // Any blockable damage that isn't from an attack is THORNS damage (such as from Thorns).
        // Damage that ignores block is HP_LOSS.
        AbstractCard MLS = new MoonlightSlash();
        AbstractCard CMS = new CrescentMoonSlash();

        if (this.upgraded) {
            MLS.upgraded = true;
            CMS.upgraded = true;
        }

        if (BaseMod.MAX_HAND_SIZE < p.hand.size() + 2) {
            addToTop(new DiscardAction(p, p, (p.hand.size() + 2) - BaseMod.MAX_HAND_SIZE, true));
        }

        addToBot(new MakeTempCardInHandAction(MLS, 1, true));
        addToBot(new MakeTempCardInHandAction(CMS, 1, true));
    }

    @Override
    public void upgrade() {
        super.upgrade();
        cardsToPreview.upgrade();
    }
}
