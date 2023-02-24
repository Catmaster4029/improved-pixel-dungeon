package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Warlock;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.LightOrb;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShaftParticle;
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

        super.zap( cell );

        MagicMissile.boltFromChar( parent,
                MagicMissile.BEACON,
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

