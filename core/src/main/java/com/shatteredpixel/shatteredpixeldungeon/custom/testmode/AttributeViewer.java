package com.shatteredpixel.shatteredpixeldungeon.custom.testmode;

import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Rat;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HealthBar;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTitledMessage;
import com.watabou.noosa.ui.Component;

import java.util.ArrayList;

public class AttributeViewer extends TestItem{
    {
        image = ItemSpriteSheet.GOLD;
        defaultAction = AC_INSPECT;
    }

    private static final String AC_INSPECT = "inspect";

    private CellSelector.Listener selector = new CellSelector.Listener() {
        @Override
        public void onSelect( Integer cell ) {
            if( cell == null ) return;

            Char ch = Actor.findChar( cell );
            if( ch == null )
                GLog.w( M.L( AttributeViewer.class, "no_char" ) );
            else
                GameScene.show( new WndMobInfo( ch, ch instanceof Mob ? mobAttribute( ch ) : heroAttribute( ch ) ) );
        }

        @Override
        public String prompt() {
            return M.L( AttributeViewer.class, "select" );
        }
    };

    private String mobAttribute( Char ch ){
        String desc = "";
        if( !( ch instanceof Mob ) )
            return desc;

        desc += ((Mob) ch).description();

        desc += "\n\n";
        desc += M.L( AttributeViewer.class, "health", ch.HP, ch.HT );
        desc += "\n";

        int tries = 500;

        int[] damage = new int[ tries ];
        for ( int i = 0; i < tries; ++i ) {
            damage[ i ] = ch.damageRoll();
        }
        float variance = 0f;
        float average = 0f;
        for ( int i = 0; i < tries; ++i ) {
            average += damage[ i ];
        }
        average /= tries;
        for ( int i = 0; i < tries; ++i ) {
            variance += ( damage[ i ] - average ) * ( damage[ i ] - average );
        }
        variance = (float) Math.sqrt( variance / tries );
        desc += M.L( AttributeViewer.class, "damage", average, variance );
        desc += "\n";

        int[] defense = new int[ tries ];
        for ( int i = 0; i < tries; ++i ) {
            defense[ i ] = ch.drRoll();
        }
        variance = 0f;
        average = 0f;
        for ( int i = 0; i < tries; ++i ) {
            average += defense[ i ];
        }
        average /= tries;
        for ( int i = 0; i < tries; ++i ) {
            variance += ( defense[ i ] - average ) * ( defense[ i ] - average );
        }
        variance = (float) Math.sqrt( variance / tries );
        desc += M.L( AttributeViewer.class, "defense", average, variance );
        desc += "\n";

        try {
            desc += M.L( AttributeViewer.class, "accuracy", ch.attackSkill(null) );
            desc += "\n";
        }catch ( NullPointerException ignored ){

        }

        try {
            desc += M.L( AttributeViewer.class, "evasion", ch.defenseSkill(null) );
            desc += "\n";
        }catch ( NullPointerException ignored ){

        }

        desc += M.L( AttributeViewer.class, "move_speed", ch.speed() );
        desc += "\n";


        desc += M.L( AttributeViewer.class, "attack_delay", ((Mob) ch).attackDelay() );
        desc += "\n";

        desc += M.L( AttributeViewer.class, "view_distance", ch.viewDistance );
        desc += "\n";


        desc += M.L( AttributeViewer.class, "exp", ((Mob) ch).EXP, ((Mob) ch).maxLvl );
        desc += "\n";

        String properties = mobProperties( ch );
        if( !properties.isEmpty() ) {
            desc += M.L(AttributeViewer.class, "properties", mobProperties(ch));
            desc += "\n";
        }

        return desc;
    }

    private String heroAttribute( Char ch ){
        String desc = "";
        if( !( ch instanceof Hero ) )
            return desc;

        desc += "\n\n";
        desc += M.L( AttributeViewer.class, "health", ch.HP, ch.HT );
        desc += "\n";

        int tries = 500;

        int[] damage = new int[ tries ];
        for ( int i = 0; i < tries; ++i ) {
            damage[ i ] = ch.damageRoll();
        }
        float variance = 0f;
        float average = 0f;
        for ( int i = 0; i < tries; ++i ) {
            average += damage[ i ];
        }
        average /= tries;
        for ( int i = 0; i < tries; ++i ) {
            variance += ( damage[ i ] - average ) * ( damage[ i ] - average );
        }
        variance = (float) Math.sqrt( variance / tries );
        desc += M.L( AttributeViewer.class, "damage", average, variance );
        desc += "\n";

        int[] defense = new int[ tries ];
        for ( int i = 0; i < tries; ++i ) {
            defense[ i ] = ch.drRoll();
        }
        variance = 0f;
        average = 0f;
        for ( int i = 0; i < tries; ++i ) {
            average += defense[ i ];
        }
        average /= tries;
        for ( int i = 0; i < tries; ++i ) {
            variance += ( defense[ i ] - average ) * ( defense[ i ] - average );
        }
        variance = (float) Math.sqrt( variance / tries );
        desc += M.L( AttributeViewer.class, "defense", average, variance );
        desc += "\n";

        try {
            desc += M.L( AttributeViewer.class, "accuracy", ch.attackSkill(null) );
            desc += "\n";
        }catch ( NullPointerException ignored ){

        }

        try {
            desc += M.L( AttributeViewer.class, "evasion", ch.defenseSkill(null) );
            desc += "\n";
        }catch ( NullPointerException ignored ){

        }

        desc += M.L( AttributeViewer.class, "move_speed", ch.speed() );
        desc += "\n";

        desc += M.L( AttributeViewer.class, "attack_delay", ((Hero) ch).attackDelay() );
        desc += "\n";

        desc += M.L( AttributeViewer.class, "view_distance", ch.viewDistance );
        desc += "\n";

        return desc;
    }

    private String mobProperties( Char ch ){
        String text = "";
        for( Char.Property p : ch.properties() ) {
            text += Messages.get( AttributeViewer.class, getMobProperties( p ) );
            text += " , ";
        }

        return text.isEmpty() ? text : text.substring( 0, text.length() - 3 );
    }

    private String getMobProperties( Char.Property property ){
        switch ( property ){
            case BOSS:
                return "boss";
            case MINIBOSS:
                return "miniboss";
            case UNDEAD:
                return "undead";
            case DEMONIC:
                return "demonic";
            case INORGANIC:
                return "inorganic";
            case FIERY:
                return "fiery";
            case ICY:
                return "icy";
            case ACIDIC:
                return "acidic";
            case ELECTRIC:
                return "electric";
            case LARGE:
                return "large";
            case IMMOVABLE:
                return "immovable";
            default:
                return "";
        }
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_INSPECT);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if(action.equals(AC_INSPECT)){
            GameScene.selectCell(selector);
        }
    }

    public static class WndMobInfo extends WndTitledMessage {
        public WndMobInfo(){
            super(new CharTitle(new Rat()), null);
        }

        public WndMobInfo(Char ch, String message){
            super(new CharTitle(ch), message);
        }
    }

    public static class CharTitle extends Component {

        private static final int GAP = 2;

        private RenderedTextBlock name;
        private HealthBar health;
        private BuffIndicator buffs;

        public CharTitle(Char ch) {

            name = PixelScene.renderTextBlock(Messages.titleCase(ch.name()), 9);
            name.hardlight(0xFFFF00);
            add(name);

            health = new HealthBar();
            health.level(ch);
            add(health);

            buffs = new BuffIndicator(ch, false);
            add(buffs);
        }

        @Override
        protected void layout() {

            name.maxWidth((int) width - GAP*2);
            name.setPos( GAP, GAP);

            health.setRect( GAP, name.bottom() + GAP, width - GAP * 2, health.height());

            buffs.setPos(
                    name.right() + GAP - 1,
                    name.bottom() - BuffIndicator.SIZE_SMALL - 2
            );

            height = health.bottom();
        }
    }
}
