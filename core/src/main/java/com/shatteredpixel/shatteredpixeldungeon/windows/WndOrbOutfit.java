package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
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

            }
        };

        btnWand.setRect( 45, message.bottom() + 5, BTN_SIZE, BTN_SIZE );
        add( btnWand );
        btnWand.item(new WndBag.Placeholder(ItemSpriteSheet.WAND_HOLDER));
        resize(WIDTH, 76);
        }
    }



