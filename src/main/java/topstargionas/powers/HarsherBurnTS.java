package topstargionas.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import topstargionas.cards.CustomTags;

import static topstargionas.TopStarGionasMod.makeID;

public class HarsherBurnTS extends BasePower {
    public static final String POWER_ID = makeID("HarsherBurnTS");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public HarsherBurnTS(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        AbstractCreature player = action.source;
        AbstractCreature target = action.target;

        boolean burned = false;
        
        if (card.hasTag(CustomTags.BURNETH)) {
            // Apply burneth
            if (card.target == AbstractCard.CardTarget.ALL_ENEMY) {
                for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                    addToBot(new ApplyPowerAction(mo, mo, new BurnTheStars(mo, amount)));
                }
            } else if (card.target == AbstractCard.CardTarget.ENEMY) {
                addToBot(new ApplyPowerAction(target, target, new BurnTheStars(target, amount)));
            } else if (card.target == AbstractCard.CardTarget.SELF_AND_ENEMY) {
                addToBot(new ApplyPowerAction(target, target, new BurnTheStars(target, amount)));
            } else if (card.target == AbstractCard.CardTarget.ALL) {
                for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                    addToBot(new ApplyPowerAction(mo, mo, new BurnTheStars(mo, amount)));
                }
            }
        }
    }

    public void updateDescription() {
        this.description = "Upon inflicting Burneth, inflict an additional " + amount + " Burneth.";
    }
}