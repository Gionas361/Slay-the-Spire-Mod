package topstargionas.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Random;

import static topstargionas.TopStarGionasMod.makeID;

public class VengefulPower extends BasePower {
    // makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    public static final String POWER_ID = makeID("VengefulPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public boolean UPVER = false;
    public boolean UNUPGRADEDCHECK;
    public int HITSTAKEN;

    public VengefulPower(AbstractCreature owner, int amount, boolean upgraded) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);

        UPVER = upgraded;
        UNUPGRADEDCHECK = false;
        HITSTAKEN = 0;

        updateDescription();
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        if (!UNUPGRADEDCHECK && !UPVER) {
            UNUPGRADEDCHECK = true;
        } else if (UPVER) {
            HITSTAKEN++;
        }
    }

    public void atStartOfTurn() {
        UNUPGRADEDCHECK = false;

        int timesToTrigger = 0;
        if (!UPVER && UNUPGRADEDCHECK) timesToTrigger = 1;
        if (UPVER && HITSTAKEN > 0) timesToTrigger = HITSTAKEN;

        for (int h = 0; h < timesToTrigger; h++) {
            int randomNum = new Random().nextInt(3);
            switch (randomNum) {
                case 0:
                    if (!UPVER) addToBot(new DrawCardAction(AbstractDungeon.player, (new Random().nextInt(3) + 1)));
                    if (UPVER) addToBot(new DrawCardAction(AbstractDungeon.player, 1));
                    break;
                case 1:
                    if (!UPVER) addToBot(new DrawCardAction(AbstractDungeon.player, (new Random().nextInt(3) + 1)));
                    if (UPVER) addToBot(new GainEnergyAction(1));
                    break;
                case 2:
                    if (!UPVER) addToBot(new DrawCardAction(AbstractDungeon.player, (new Random().nextInt(10) + 6)));
                    if (UPVER) addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 8));
                    break;
            }
        }

    }

    public void updateDescription() {
        this.description = "If received unblocked damage on the enemy turn, do 1 out-of 3: NL Draw 1~3 Cards next turn. NL Recover 1~3 [E] next turn. NL Recover 5~15 Health.";
        if (UPVER) this.description = "Upon taking unblocked damage, do 1 out-of 3: NL Draw 1 Cards next turn. NL Recover 1 [E] next turn. NL Recover 8 Health.";
    }
}