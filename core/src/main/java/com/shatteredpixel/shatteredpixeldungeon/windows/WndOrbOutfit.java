package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;

public class WndOrbOutfit extends Window {

    private static final int WIDTH      = 120;
    private static final int BTN_HEIGHT = 20;
    private static final int GAP        = 2;

    protected RedButton btnorb1;
    protected RedButton btnorb2;

    public int pos = 0;

    public CharSprite sprite;

    public WndOrbOutfit() {

        super();


        RedButton btnorb1 = new RedButton( Messages.get(this, "orb1") ) {
            @Override
            protected void onClick() {

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

