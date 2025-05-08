package topstargionas.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static topstargionas.TopStarGionasMod.makeID;

public class DrawPassiveStartTurn extends BasePower {
    // makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    public static final String POWER_ID = makeID("DrawPassiveStartTurn");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public DrawPassiveStartTurn(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void atStartOfTurn() {
        addToBot(new DrawCardAction(this.owner, amount));
    }

    public void updateDescription() {
        this.description = "At the start and End of your turn, draw " + amount + " cards.";
    }
}