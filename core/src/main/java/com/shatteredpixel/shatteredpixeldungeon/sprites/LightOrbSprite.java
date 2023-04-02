package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.DM100;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.LightOrb;
import com.shatteredpixel.shatteredpixeldungeon.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeon.effects.Lightning;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShaftParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.DamageWand;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfCorrosion;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfCorruption;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfDisintegration;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFireblast;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFrost;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLightning;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLivingEarth;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfPrismaticLight;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfRegrowth;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfTransfusion;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfWarding;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class LightOrbSprite extends MobSprite {

    public LightOrbSprite() {
        super();

        texture( Assets.Sprites.LIGHT_ORB );

        TextureFilm frames = new TextureFilm( texture, 14, 15 );

        idle = new Animation( 5, true );
        idle.frames( frames, 0, 1 );

        run = new Animation( 10, true );
        run.frames( frames, 0, 1 );

        attack = new Animation( 10, false );
        attack.frames( frames, 0, 2, 3 );

        die = new Animation( 8, false );
        die.frames( frames, 0, 4, 5, 6, 7 );

        play( idle );
    }

    @Override
    public void draw() {
        Blending.setLightMode();
        super.draw();
        Blending.setNormalMode();
    }

    @Override
    public void die() {
        super.die();
        emitter().start( ShaftParticle.FACTORY, 0.3f, 4 );
        emitter().start( Speck.factory( Speck.LIGHT ), 0.2f, 3 );
    }



    @Override
    public int blood() {
        return 0xFFFFFF;
    }
    public void zap( int cell ) {

        if (LightOrb.wand instanceof WandOfMagicMissile){
            super.zap( cell );
            MagicMissile.boltFromChar( parent,
                    MagicMissile.MAGIC_MISSILE,
                    this,
                    cell,
                    new Callback() {
                        @Override
                        public void call() {
                            ((LightOrb)ch).onZapComplete();
                        }
                    } );
            Sample.INSTANCE.play( Assets.Sounds.ZAP );
        }
        if (LightOrb.wand instanceof WandOfFrost){
            super.zap( cell );
            MagicMissile.boltFromChar( parent,
                    MagicMissile.FROST,
                    this,
                    cell,
                    new Callback() {
                        @Override
                        public void call() {
                            ((LightOrb)ch).onZapComplete();
                        }
                    } );
            Sample.INSTANCE.play( Assets.Sounds.ZAP );
        }
        if (LightOrb.wand instanceof WandOfFireblast){
            super.zap( cell );
            MagicMissile.boltFromChar( parent,
                    MagicMissile.FIRE,
                    this,
                    cell,
                    new Callback() {
                        @Override
                        public void call() {
                            ((LightOrb)ch).onZapComplete();
                        }
                    } );
            Sample.INSTANCE.play( Assets.Sounds.ZAP );
        }
        if (LightOrb.wand instanceof WandOfDisintegration){
            super.zap( cell );
            parent.add(new Beam.DeathRay(center(), DungeonTilemap.raisedTileCenterToWorld( cell )));
            new Callback() {
                @Override
                public void call() {
                    ((LightOrb)ch).onZapComplete();
                }
            };
            Sample.INSTANCE.play( Assets.Sounds.ZAP );
        }
        if (LightOrb.wand instanceof WandOfCorrosion){
            super.zap( cell );
            MagicMissile.boltFromChar( parent,
                    MagicMissile.CORROSION,
                    this,
                    cell,
                    new Callback() {
                        @Override
                        public void call() {
                            ((LightOrb)ch).onZapComplete();
                        }
                    } );
            Sample.INSTANCE.play( Assets.Sounds.ZAP );
        }
        if (LightOrb.wand instanceof WandOfBlastWave){
            super.zap( cell );
            MagicMissile.boltFromChar( parent,
                    MagicMissile.FORCE,
                    this,
                    cell,
                    new Callback() {
                        @Override
                        public void call() {
                            ((LightOrb)ch).onZapComplete();
                        }
                    } );
            Sample.INSTANCE.play( Assets.Sounds.ZAP );
        }
        if (LightOrb.wand instanceof WandOfCorruption){
            super.zap( cell );
            MagicMissile.boltFromChar( parent,
                    MagicMissile.SHADOW,
                    this,
                    cell,
                    new Callback() {
                        @Override
                        public void call() {
                            ((LightOrb)ch).onZapComplete();
                        }
                    } );
            Sample.INSTANCE.play( Assets.Sounds.ZAP );
        }
        if (LightOrb.wand instanceof WandOfLightning){
            super.zap( cell );
            parent.add( new Lightning.Arc(center(), ch.sprite.center()));
            Sample.INSTANCE.play( Assets.Sounds.ZAP );
        }
        if (LightOrb.wand instanceof WandOfTransfusion){
            super.zap( cell );
            parent.add(new Beam.HealthRay(center(), DungeonTilemap.raisedTileCenterToWorld( cell )));
            new Callback() {
                @Override
                public void call() {
                    ((LightOrb)ch).onZapComplete();
                }
            };
            Sample.INSTANCE.play( Assets.Sounds.ZAP );
            }
        if (LightOrb.wand instanceof WandOfLivingEarth){
            super.zap( cell );
            MagicMissile.boltFromChar( parent,
                    MagicMissile.EARTH,
                    this,
                    cell,
                    new Callback() {
                        @Override
                        public void call() {
                            ((LightOrb)ch).onZapComplete();
                        }
                    } );
            Sample.INSTANCE.play( Assets.Sounds.ZAP );
        }
        if (LightOrb.wand instanceof WandOfRegrowth){
            super.zap( cell );
            MagicMissile.boltFromChar( parent,
                    MagicMissile.FOLIAGE_CONE,
                    this,
                    cell,
                    new Callback() {
                        @Override
                        public void call() {
                            ((LightOrb)ch).onZapComplete();
                        }
                    } );
            Sample.INSTANCE.play( Assets.Sounds.ZAP );
        }
        if (LightOrb.wand instanceof WandOfWarding){
            super.zap( cell );
            MagicMissile.boltFromChar( parent,
                    MagicMissile.WARD,
                    this,
                    cell,
                    new Callback() {
                        @Override
                        public void call() {
                            ((LightOrb)ch).onZapComplete();
                        }
                    } );
            Sample.INSTANCE.play( Assets.Sounds.ZAP );
        }
        if (LightOrb.wand instanceof WandOfPrismaticLight){
            super.zap( cell );
            parent.add(new Beam.LightRay(center(), DungeonTilemap.raisedTileCenterToWorld( cell )));
            new Callback() {
                @Override
                public void call() {
                    ((LightOrb)ch).onZapComplete();
                }
            };
            Sample.INSTANCE.play( Assets.Sounds.ZAP );
        }



        else {
            super.zap( cell );
            MagicMissile.boltFromChar( parent,
                    MagicMissile.RAINBOW,
                    this,
                    cell,
                    new Callback() {
                        @Override
                        public void call() {
                            ((LightOrb)ch).onZapComplete();
                        }
                    } );
            Sample.INSTANCE.play( Assets.Sounds.ZAP );
        }

    }
}

