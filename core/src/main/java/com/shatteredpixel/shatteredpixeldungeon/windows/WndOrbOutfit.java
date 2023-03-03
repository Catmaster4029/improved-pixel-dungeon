package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;

public class WndOrbOutfit extends Window {

    private static final int WIDTH      = 120;
    private static final int BTN_HEIGHT = 20;
    private static final int GAP        = 2;
    private static final int BTN_SIZE	= 32;

    protected WndBlacksmith.ItemButton btnWand;

    public int pos = 0;

    public CharSprite sprite;

    public WndOrbOutfit() {

        super();

        btnWand = new WndBlacksmith.ItemButton(){
            @Override
            protected void onClick(){

            }
            };

        btnWand.setRect( 75 - BTN_SIZE, GAP, BTN_SIZE, BTN_SIZE );
        add( btnWand );
        btnWand.item(new WndBag.Placeholder(ItemSpriteSheet.WAND_HOLDER));
        resize(WIDTH, (int)(btnWand.bottom() + GAP));
        };



    }



