package topstargionas.powers;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static topstargionas.TopStarGionasMod.makeID;

public class CoreOverdrivePower extends BasePower {
    // makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    public static final String POWER_ID = makeID("CoreOverdrivePower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    // These will be used in the constructor. Technically you can just use the values directly,
    // but constants at the top of the file are easy to adjust.
    private static final int ENRECOVERY = 1;

    public CoreOverdrivePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void atStartOfTurn() {
        addToBot(new GainEnergyAction(amount));
    }

    public void updateDescription() {
        this.description = "At the start of your turn, recover " + amount + " Energy.";
    }
}