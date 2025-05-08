package topstargionas.powers;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static topstargionas.TopStarGionasMod.makeID;

public class BurnTheStars extends BasePower {
    public static final String POWER_ID = makeID("BurnTheStars");
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public BurnTheStars(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (this.owner.currentHealth <= amount) this.owner.damage(new DamageInfo(this.owner, this.owner.currentHealth - 1, DamageInfo.DamageType.HP_LOSS));
        else this.owner.damage(new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.HP_LOSS));

        this.amount = amount/2;

        if (amount == 0) {
            addToTop(new RemoveSpecificPowerAction(owner, owner, BurnTheStars.POWER_ID));
        }
    }

    @Override
    public void atStartOfTurn() {
        if (this.owner.currentHealth <= amount) this.owner.damage(new DamageInfo(this.owner, this.owner.currentHealth - 1, DamageInfo.DamageType.HP_LOSS));
        else this.owner.damage(new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.HP_LOSS));

        if (amount > 1) this.amount = amount/2;
        else this.amount = 0;
    }

    public void updateDescription() {
        this.description = "At the end of your turn, take " + amount + " damage, Burn can't kill.";
    }
}