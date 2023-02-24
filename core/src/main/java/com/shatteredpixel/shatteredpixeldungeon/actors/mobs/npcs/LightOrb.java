package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.journal.Guidebook;
import com.shatteredpixel.shatteredpixeldungeon.journal.Document;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.LightOrbSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class LightOrb extends Mob {

        {
                spriteClass = LightOrbSprite.class;

                HP = HT = 4;
                defenseSkill = 25;

                EXP = 2;
                maxLvl = 7;

                loot = Generator.Category.SEED;
                lootChance = 0.25f;
        }

        @Override
        public int damageRoll() {
                return Random.NormalIntRange( 1, 4 );
        }

        @Override
        public int attackSkill( Char target ) {
                return 10;
        }

        private static int dodges = 0;

        @Override
        public String defenseVerb() {
                dodges++;
                if (dodges >= 2 && !Document.ADVENTURERS_GUIDE.isPageRead(Document.GUIDE_SURPRISE_ATKS)){
                        GLog.p(Messages.get(Guidebook.class, "hint"));
                        GameScene.flashForDocument(Document.ADVENTURERS_GUIDE, Document.GUIDE_SURPRISE_ATKS);
                        dodges = 0;
                }
                return super.defenseVerb();
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

