package topstargionas.relics;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import topstargionas.cards.CardDispatcher;
import topstargionas.cards.CustomTags;
import topstargionas.character.TheCaretaker;

import static topstargionas.TopStarGionasMod.makeID;

public class RetriggerImitations extends BaseRelic {
    private static final String NAME = "RetriggerImitations"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.SOLID; //The sound played when the relic is clicked.

    private static final int HANDADD = 5;

    // Constructor
    public RetriggerImitations() {
        super(ID, NAME, TheCaretaker.Meta.CARD_COLOR, RARITY, SOUND);
    }

    // Actual Code
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.hasTag(CustomTags.IMMITATIONS)) {
            //CardDispatcher.triggerCardEffect(c.cardID, AbstractDungeon.player, m, 1);

            c.exhaust = true;
            c.use(AbstractDungeon.player, m);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], HANDADD);
    }
}
