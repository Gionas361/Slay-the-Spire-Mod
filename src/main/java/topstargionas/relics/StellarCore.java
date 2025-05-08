package topstargionas.relics;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import topstargionas.character.TheCaretaker;

import static topstargionas.TopStarGionasMod.makeID;

public class StellarCore extends BaseRelic {
    private static final String NAME = "StellarCore"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    private static final int DRAWRELIC = 2;
    private static final int RECENERGY = 1;

    // Constructor
    public StellarCore() {
        super(ID, NAME, TheCaretaker.Meta.CARD_COLOR, RARITY, SOUND);
    }

    // Actual Code


    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0]);
    }

    @Override
    public void atTurnStart() {
        addToBot(new DrawCardAction(DRAWRELIC));
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.upgraded && c.cost > 0) addToTop(new GainEnergyAction(RECENERGY));
    }
}