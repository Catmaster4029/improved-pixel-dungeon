package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroAction;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;

import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;




public class WndOrbsManage extends Window {

    private static final int WIDTH      = 120;
    private static final int BTN_HEIGHT = 20;
    private static final int GAP        = 2;

    protected RedButton btnorb1;
    protected RedButton btnorb2;

    public int pos = 0;

    public CharSprite sprite;

    public WndOrbsManage() {

        super();


        RedButton btnorb1 = new RedButton( Messages.get(this, "orb1") ) {
            @Override
            protected void onClick() {
                GameScene.show(new WndOrbOutfit());
            }

        };
        RedButton move = new RedButton( Messages.get(this, "move") ) {
            @Override
            protected void onClick() {
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {


                    }
                });
            }
        };

        btnorb1.setRect( 0, GAP - 2 , WIDTH, BTN_HEIGHT );
        move.setRect( 0, (int)btnorb1.bottom() + GAP, WIDTH, BTN_HEIGHT );
        add( btnorb1 );
        add( move );

        resize( WIDTH, 42 );
    }

}


