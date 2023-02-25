package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Imp;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.DwarfToken;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;


public class WndOrbsManage extends Window {

    private static final int WIDTH      = 120;
    private static final int BTN_HEIGHT = 20;
    private static final int GAP        = 2;

    protected RedButton btnorb1;
    protected RedButton btnorb2;

    public WndOrbsManage() {

        super();


        RedButton btnorb1 = new RedButton( Messages.get(this, "orb1") ) {
            @Override
            protected void onClick() {
            }
        };
        RedButton btnorb2 = new RedButton( Messages.get(this, "orb2") ) {
            @Override
            protected void onClick() {
            }
        };

        btnorb1.setRect( 0, GAP - 2 , WIDTH, BTN_HEIGHT );
        btnorb2.setRect( 0, (int)btnorb1.bottom() + GAP, WIDTH, BTN_HEIGHT );
        add( btnorb1 );
        add( btnorb2 );

        resize( WIDTH, 42 );
    }

}