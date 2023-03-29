package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.CorrosiveGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.MagicalHolster;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPsionicBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.LightOrbSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.LightOrbSprite2;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.IconTitle;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBlacksmith;
import com.watabou.noosa.Game;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class LightOrb2 extends Mob {

        private static final float TIME_TO_ZAP	= 1f;
        private LightOrb2 orb2 = null;
        private static Wand wand = null;
        private int orb2ID = 0;

        {
                spriteClass = LightOrbSprite2.class;

                HP = HT = 1 ;
                defenseSkill = 1000;
                state = HUNTING;
                alignment = Alignment.ALLY;
                intelligentAlly = true;
        }

        {
                immunities.add( ToxicGas.class );
                immunities.add( CorrosiveGas.class );
                immunities.add( Burning.class );
                immunities.add( ScrollOfRetribution.class );
                immunities.add( ScrollOfPsionicBlast.class );
        }

        @Override
        public int damageRoll() {
                return Random.NormalIntRange( 12, 18 );
        }

        @Override
        public int attackSkill( Char target ) {
                return 25;
        }

        @Override
        public int drRoll() {
                return Random.NormalIntRange(0, 8);
        }

        @Override
        public int defenseSkill( Char enemy ) {
                return INFINITE_EVASION;
        }

        @Override
        public boolean interact(Char c) {
                Game.runOnRenderThread(new Callback() {
                        @Override
                        public void call() {
                                menu();
                        }
                });
                return true;
        }

                @Override
        protected boolean canAttack( Char enemy ) {
                return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
        }

        protected boolean doAttack( Char enemy ) {

                if (Dungeon.level.adjacent( pos, enemy.pos )) {

                        return super.doAttack( enemy );

                } else {

                        if (sprite != null && (sprite.visible || enemy.sprite.visible)) {
                                sprite.zap( enemy.pos );
                                return false;
                        } else {
                                zap();
                                return true;
                        }
                }
        }
        protected void zap() {
                spend( 1f );

                Invisibility.dispel(this);
                if (hit( this, enemy, true )) {

                        enemy.damage( Random.NormalIntRange(10, 20), new YogFist.DarkFist.DarkBolt() );

                        Light l = enemy.buff(Light.class);
                        if (l != null){
                                l.weaken(50);
                        }

                        if (!enemy.isAlive() && enemy == Dungeon.hero) {
                                Badges.validateDeathFromEnemyMagic();
                                Dungeon.fail( getClass() );
                                GLog.n( Messages.get(Char.class, "kill", name()) );
                        }

                } else {

                        enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
                }

        }

        public void onZapComplete() {
                zap();
                next();
        }

        public void menu() {
                GameScene.show(new WndOrbsManage(this));
        }


        public String status() {
                if (orb2 == null && orb2ID != 0){
                        try {
                                Actor a = Actor.findById(orb2ID);
                                if (a != null) {
                                        orb2 = (LightOrb2) a;
                                } else {
                                        orb2ID = 0;
                                }
                        } catch ( ClassCastException e ){
                                ShatteredPixelDungeon.reportException(e);
                                orb2ID = 0;
                        }
                }
                        return ((orb2.HP*100) / orb2.HT) + "%";
                }


        private static final String ORBID =       "orbID";
        private static final String WAND =         "wand";

        @Override
        public void storeInBundle( Bundle bundle ) {
                super.storeInBundle(bundle);

                bundle.put( ORBID, orb2ID );

                if (wand != null) bundle.put( WAND, wand );
        }

        @Override
        public void restoreFromBundle( Bundle bundle ) {
                super.restoreFromBundle(bundle);

                orb2ID = bundle.getInt( ORBID );
                if (bundle.contains(WAND)) wand = (Wand) bundle.get( WAND );
        }



        public static void spawnNext( int pos ) {
                for (int n : PathFinder.RIGHT) {
                        spawnAtOrb(pos + n);
                }
        }
        public static LightOrb2 spawnAtOrb(int pos ) {
                if ((!Dungeon.level.solid[pos] || Dungeon.level.passable[pos]) && Actor.findChar( pos ) == null) {

                        LightOrb2 w = new LightOrb2();
                        w.pos = pos;
                        Dungeon.level.occupyCell(w);
                        GameScene.add( w );
                        w.sprite.alpha( 0 );
                        w.sprite.parent.add( new AlphaTweener( w.sprite, 1, 0.5f ) );

                        w.sprite.emitter().burst( ShadowParticle.CURSE, 5 );
                        return w;
                } else {
                        return null;
                }
        }

        public class WndOrbsManage extends Window {

                private static final int WIDTH      = 120;
                private static final int BTN_HEIGHT = 20;
                private static final int GAP        = 2;

                public int pos = 0;

                public CharSprite sprite;

                public WndOrbsManage(LightOrb2 orb2) {

                        super();

                        RedButton btnorb1 = new RedButton( Messages.get(this, "orb1") ) {
                                @Override
                                protected void onClick() {
                                        GameScene.show(new WndOrbOutfit(orb2));
                                }

                        };
                        RedButton move = new RedButton( Messages.get(this, "move") ) {
                                @Override
                                protected void onClick() {

                                }
                        };

//                        RedButton direct = new RedButton( Messages.get(this, "direct") ) {
//                                @Override
//                                protected void onClick() {
//                                        if (orb == null && orbID != 0){
//                                                Actor a = Actor.findById(orbID);
//                                                if (a != null){
//                                                        orb = (LightOrb)a;
//                                                } else {
//                                                        orbID = 0;
//                                                }
//                                        }
//                                        if (orb != null) GameScene.selectCell(ghostDirector);

//                                }


//                        };

                        btnorb1.setRect( 0, GAP - 2 , WIDTH, BTN_HEIGHT );
                        move.setRect( 0, (int)btnorb1.bottom() + GAP, WIDTH, BTN_HEIGHT );
//                        direct.setRect( 0, (int)move.bottom() + GAP, WIDTH, BTN_HEIGHT );
                        add( btnorb1 );
                        add( move );
//                        add( direct );

                        resize( WIDTH, 64 );
                }

        }

        public static class WndOrbOutfit extends Window {

                private static final int WIDTH      = 120;
                private static final int BTN_HEIGHT = 20;
                private static final int GAP        = 2;
                private static final int BTN_SIZE	= 32;

                protected WndBlacksmith.ItemButton btnWand;

                public int pos = 0;

                public CharSprite sprite;

                public WndOrbOutfit(LightOrb2 orb2) {


                        super();

                        IconTitle titlebar = new IconTitle();
                        titlebar.icon( new ItemSprite(ItemSpriteSheet.MAGES_STAFF));
                        titlebar.label( Messages.get(this, "title") );
                        titlebar.setRect( 0, 0, WIDTH, 0 );
                        add( titlebar );

                        RenderedTextBlock message =
                                PixelScene.renderTextBlock(Messages.get(this, "desc"), 6);
                        message.maxWidth( WIDTH );
                        message.setPos(0, titlebar.bottom() + GAP);
                        add( message );

                        btnWand = new WndBlacksmith.ItemButton(){
                                @Override
                                protected void onClick(){
                                        if (orb2.wand != null) {
                                                item(new WndBag.Placeholder(ItemSpriteSheet.ARMOR_HOLDER));
                                                if (!LightOrb2.wand.doPickUp(Dungeon.hero)){
                                                        Dungeon.level.drop( LightOrb2.wand, Dungeon.hero.pos);
                                                }
                                                LightOrb2.wand = null;}

                                        else {
                                                GameScene.selectItem(new WndBag.ItemSelector() {

                                                        @Override
                                                        public String textPrompt() {
                                                                return Messages.get(LightOrb2.WndOrbOutfit.class, "wandprompt");
                                                        }

                                                        @Override
                                                        public Class<?extends Bag> preferredBag(){
                                                                return MagicalHolster.class;
                                                        }

                                                        @Override
                                                        public boolean itemSelectable(Item item) {
                                                                return item instanceof Wand;
                                                        }

                                                        @Override
                                                        public void onSelect(Item item) {
                                                                if (!(item instanceof Wand)) {

                                                                } else if (item.unique) {
                                                                        GLog.w( Messages.get(LightOrb2.WndOrbOutfit.class, "cant_unique"));
                                                                        hide();
                                                                } else if (!item.isIdentified()) {
                                                                        GLog.w( Messages.get(LightOrb2.WndOrbOutfit.class, "cant_unidentified"));
                                                                        hide();
                                                                } else if (item.cursed) {
                                                                        GLog.w( Messages.get(LightOrb2.WndOrbOutfit.class, "cant_cursed"));
                                                                        hide();
                                                                }
                                                                else {
                                                                        item.detach(Dungeon.hero.belongings.backpack);
                                                                        LightOrb2.wand = (Wand) item;
                                                                        item(LightOrb2.wand);
                                                                }

                                                        }
                                                });
                                        }
                                }
                        };
                        if (LightOrb2.wand != null) {
                                btnWand.item(LightOrb2.wand);}
                        else {
                                btnWand.item(new WndBag.Placeholder(ItemSpriteSheet.WAND_HOLDER));
                        }
                        btnWand.setRect( 45, message.bottom() + 5, BTN_SIZE, BTN_SIZE );
                        add( btnWand );
                        resize(WIDTH, 76);
                }

        }



}

