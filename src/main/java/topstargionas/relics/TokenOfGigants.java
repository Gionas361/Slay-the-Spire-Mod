package topstargionas.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import topstargionas.cards.CustomTags;
import topstargionas.character.TheCaretaker;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;

import static topstargionas.TopStarGionasMod.makeID;

public class TokenOfGigants extends BaseRelic {
    private static final String NAME = "TokenOfGigants"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.


    // Constructor
    public TokenOfGigants() {
        super(ID, NAME, TheCaretaker.Meta.CARD_COLOR, RARITY, SOUND);
    }

    // Actual Code


    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0]);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.hasTag(CustomTags.IMMITATIONS) && !c.hasTag(CustomTags.CLONE)) {

            if (c.damage > 0) addToBot(new DamageAction(m, new DamageInfo(AbstractDungeon.player, c.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            if (c.block > 0) addToBot(new GainBlockAction(AbstractDungeon.player, c.block / 2));



            //addToBot(new UseCardAction(c, AbstractDungeon.getMonsters().getRandomMonster(true)));
        }
    }
}