package topstargionas.relics;

import basemod.devcommands.kill.Kill;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveAllPowersAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.GainPowerEffect;
import topstargionas.character.TheCaretaker;

import static topstargionas.TopStarGionasMod.makeID;

public class BurningNova extends BaseRelic {
    private static final String NAME = "BurningNova"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    private static final int DEATHTURNS = 12;
    private int STR = 1;

    // Constructor
    public BurningNova() {
        super(ID, NAME, TheCaretaker.Meta.CARD_COLOR, RARITY, SOUND);

        this.counter = DEATHTURNS;
    }


    // Actual Code
    @Override
    public String getUpdatedDescription() {
        int tmp = this.counter;
        if (tmp <= 0) tmp = 12;
        return String.format(DESCRIPTIONS[0] + tmp + DESCRIPTIONS[1]);
    }

    public void atBattleStart() {
        this.counter = DEATHTURNS;
    }

    @Override
    public void atTurnStart() {
        if (this.counter <= 6) STR = 2;
        else STR = 1;
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, STR)));
    }

    @Override
    public void onPlayerEndTurn() {
        this.counter -= 1;
        getUpdatedDescription();
        if (this.counter <= 0) {
            addToTop(new RemoveAllPowersAction(AbstractDungeon.player, false));
            addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 999, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.SMASH));
        }
    }
}