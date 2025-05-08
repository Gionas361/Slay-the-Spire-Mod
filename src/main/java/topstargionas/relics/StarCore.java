package topstargionas.relics;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import topstargionas.character.TheCaretaker;
import topstargionas.powers.BurnTheStars;

import static topstargionas.TopStarGionasMod.makeID;

public class StarCore extends BaseRelic {
    private static final String NAME = "StarCore"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    private static final int HANDADD = 5;

    // Constructor
    public StarCore() {
        super(ID, NAME, TheCaretaker.Meta.CARD_COLOR, RARITY, SOUND);
    }

    // Actual Code
    public void atBattleStart() {
        if (BaseMod.MAX_HAND_SIZE < 15) {
            BaseMod.MAX_HAND_SIZE += HANDADD;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], HANDADD);
    }
}
