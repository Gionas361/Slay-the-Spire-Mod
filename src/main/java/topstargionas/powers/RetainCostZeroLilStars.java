package topstargionas.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import topstargionas.cards.basic.LittleStar;

import static topstargionas.TopStarGionasMod.makeID;

public class RetainCostZeroLilStars extends BasePower {
    public static final String POWER_ID = makeID("RetainCostZeroLilStars");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public RetainCostZeroLilStars(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof LittleStar) {
                c.retain = true;
            }
        }
    }

    @Override
    public void atStartOfTurn() {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c instanceof LittleStar) {
                c.setCostForTurn(0);
                c.retain = false;
            }
        }

        addToTop(new RemoveSpecificPowerAction(owner, owner, RetainCostZeroLilStars.POWER_ID));
    }

    public void updateDescription() {
        this.description = "All Little Stars are Retained. NL On the next turn every Little Star in the deck will cost 0 for the turn.";
    }
}