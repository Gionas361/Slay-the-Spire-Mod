package topstargionas.cards.rare;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import topstargionas.cards.BaseCard;
import topstargionas.character.TheCaretaker;
import topstargionas.util.CardStats;

public class Infinita extends BaseCard {
    // makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    public static final String ID = makeID(Infinita.class.getSimpleName());

    // Card Stats
    private static final CardStats info = new CardStats(
            TheCaretaker.Meta.CARD_COLOR, // The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, // The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, // Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, // The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    // These will be used in the constructor. Technically you can just use the values directly,
    // but constants at the top of the file are easy to adjust.
    private static final int DMG = 2;
    private static final int UPG_DMG = 3;

    private static final int COSTDIV = 2;
    private static final int UPG_COSTDIV = 1;

    private AbstractPlayer player;

    // This is what actually creates the card
    public Infinita() {
        super(ID, info); // Pass the required information to the BaseCard constructor.

        setCustomVar("RELICDMG", DMG, UPG_DMG);
        setCustomVar("CARDCOST", COSTDIV, UPG_COSTDIV);

        // Basic strikes and all strike cards are "tagged",
        // so that the game knows what they are for the purposes of relics like Pandora's Box,
        // or a card like Perfected Strike. These tags are added by calling tags.add.
    }

    public void CostCalc() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            setCustomVar("RELICAMOUNT", AbstractDungeon.player.relics.size());
            setCostForTurn(AbstractDungeon.player.relics.size() / customVar("CARDCOST"));
        } else {
            setCostForTurn(0);
        }
    }

    @Override
    public void atTurnStart() {
        CostCalc();
    }

    @Override
    public boolean isHoveredInHand(float scale) {
        CostCalc();
        return super.isHoveredInHand(scale);
    }

    // This code basically declares what the card will do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CostCalc();
        addToBot(new DamageAction(m, new DamageInfo(p, (AbstractDungeon.player.relics.size() * customVar("RELICDMG")), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }
}