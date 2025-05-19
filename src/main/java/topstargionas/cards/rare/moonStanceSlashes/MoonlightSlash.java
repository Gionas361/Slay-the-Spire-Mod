package topstargionas.cards.rare.moonStanceSlashes;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import topstargionas.cards.BaseCard;
import topstargionas.cards.rare.MoonStance;
import topstargionas.character.TheCaretaker;
import topstargionas.powers.BurnTheStars;
import topstargionas.util.CardStats;

import java.util.LinkedList;
import java.util.List;

public class MoonlightSlash extends BaseCard {
    // makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    public static final String ID = makeID(MoonlightSlash.class.getSimpleName());

    // Card Stats
    private static final CardStats info = new CardStats(
            TheCaretaker.Meta.CARD_COLOR, // The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, // The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.SPECIAL, // Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, // The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            5 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    // This is what actually creates the card
    public MoonlightSlash() {
        super(ID, info); // Pass the required information to the BaseCard constructor.

        setDamage(35, 12);
        exhaust = true;

        // Basic strikes and all strike cards are "tagged",
        // so that the game knows what they are for the purposes of relics like Pandora's Box,
        // or a card like Perfected Strike. These tags are added by calling tags.add.
    }

    // This code basically declares what the card will do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Variable Calc
        boolean check = false;
        int HP = 0;

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo.currentHealth > 0) HP += mo.currentHealth;
            if (mo.currentBlock > 0) HP += mo.currentBlock;
            if (damage > HP) check = true;

            HP = 0;
        }

        // Do attack
        addToBot(new DamageAllEnemiesAction(p, damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        // Energy
        if (check) {
            addToBot(new GainEnergyAction(3));
        }
    }
}
