package topstargionas.potions.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import topstargionas.TopStarGionasMod;
import topstargionas.character.TheCaretaker;
import topstargionas.potions.BasePotion;
import topstargionas.powers.BurnTheStars;

import static topstargionas.TopStarGionasMod.makeID;

public class AllBurn extends BasePotion {
    public static final String ID = makeID("AllBurn");

    private static final Color LIQUID_COLOR = CardHelper.getColor(32, 0, 45);
    private static final Color HYBRID_COLOR = CardHelper.getColor(32, 0, 45);
    private static final Color SPOTS_COLOR = null;

    public AllBurn() {
        super(ID, 10, PotionRarity.UNCOMMON, PotionSize.SPHERE, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);

        isThrown = true;
        targetRequired = false;
    }

    @Override
    public String getDescription() {
        return DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
    }

    @Override
    public void addAdditionalTips() {
        //Adding a tooltip for Strength
        this.tips.add(new PowerTip(TopStarGionasMod.keywords.get("Burned").PROPER_NAME, TopStarGionasMod.keywords.get("Burned").DESCRIPTION));
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            for (AbstractCreature m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new BurnTheStars(m, potency)));
            }
        }
    }
}
