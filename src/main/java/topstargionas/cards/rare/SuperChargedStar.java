package topstargionas.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import topstargionas.cards.BaseCard;
import topstargionas.character.TheCaretaker;
import topstargionas.util.CardStats;

import java.util.Objects;

public class SuperChargedStar extends BaseCard {
    public static final String ID = makeID("SuperChargedStar");
    private final static CardStats CARD_STATS = new CardStats(
            TheCaretaker.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            3);

    private static final int DAMAGE = 4;
    private int base = 1;
    private int ADDCARDAMOUNT = 0; //3
    private int CARDMULT = 3; //5

    public SuperChargedStar() {
        super(ID, CARD_STATS);

        setDamage(DAMAGE);
        setMagic(1);
        setCustomVar("XhandSize", base);
        setVarCalculation("XhandSize", (card,m, base)->{
            if (AbstractDungeon.player.getCardCount() > 1) {
                base = AbstractDungeon.player.hand.size();
            }

            return base;
        });

        setCustomVar("XMult", CARDMULT);
    }

    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void upgrade() {
        this.upgradeDamage(1 + this.timesUpgraded);
        ++this.timesUpgraded;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;


        if (this.timesUpgraded != 0) {
            CARDMULT = 5;
            ADDCARDAMOUNT = 3;
            setCustomVar("XMult", CARDMULT);

            this.rawDescription = "Deal !D! damage. NL Can be Upgraded any number of times. NL Deal hand-size (" + customVar("XhandSize") + "+" + ADDCARDAMOUNT + ") X " + CARDMULT + ".";
        }


        this.upgraded = true;
        this.initializeDescription();
        this.initializeTitle();
    }

    @Override
    public void atTurnStart() {
        this.description = this.description;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        addToBot(new DamageAction(m, new DamageInfo(p, ((customVar("XhandSize") + ADDCARDAMOUNT) * CARDMULT), this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SuperChargedStar();
    }
}
