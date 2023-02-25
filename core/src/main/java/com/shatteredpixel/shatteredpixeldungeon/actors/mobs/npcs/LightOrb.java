package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AscensionChallenge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Degrade;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Warlock;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.YogFist;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.LightOrbSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class LightOrb extends DirectableAlly {

        private static final float TIME_TO_ZAP	= 1f;

        {
                spriteClass = LightOrbSprite.class;

                HP = HT = 1 ;
                defenseSkill = 1000;
                state = HUNTING;
                alignment = Alignment.ALLY;
                intelligentAlly = true;
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

        public void call() {
                next();
        }



        public static void spawnNext( int pos ) {
                for (int n : PathFinder.NEIGHBOURS2) {
                        spawnAtOrb(pos + n);
                }
        }
        public static LightOrb spawnAtOrb(int pos ) {
                if ((!Dungeon.level.solid[pos] || Dungeon.level.passable[pos]) && Actor.findChar( pos ) == null) {

                        LightOrb w = new LightOrb();
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
}

