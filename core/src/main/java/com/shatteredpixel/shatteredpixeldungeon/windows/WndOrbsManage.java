package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Chrome;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Belongings;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Blacksmith;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.ItemSlot;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Component;


public class WndOrbsManage extends Window {

    private static final int BTN_SIZE	= 36;
    private static final int WIDTH		= 116;

    private ItemButton btnPressed;
    private ItemButton btnItem1;

    public WndOrbsManage( Blacksmith troll, Hero hero ) {

        super();

        IconTitle titlebar = new IconTitle();
        titlebar.icon( troll.sprite() );
        titlebar.label( Messages.titleCase( troll.name() ) );
        titlebar.setRect( 0, 0, WIDTH, 0 );
        add( titlebar );

        RenderedTextBlock message = PixelScene.renderTextBlock( Messages.get(this, "prompt"), 6 );
        message.maxWidth( WIDTH);
        message.setPos(0, titlebar.bottom() );
        add( message );

        btnItem1 = new ItemButton() {
            @Override
            protected void onClick() {
                btnPressed = btnItem1;
                GameScene.selectItem( itemSelector );
            }
        };
        btnItem1.setRect( (WIDTH ) / 2 - BTN_SIZE, message.top() + message.height() , BTN_SIZE, BTN_SIZE );
        add( btnItem1 );
    }

    protected WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

        @Override
        public String textPrompt() {
            return Messages.get(WndBlacksmith.class, "select");
        }

        @Override
        public Class<?extends Bag> preferredBag(){
            return Belongings.Backpack.class;
        }

        @Override
        public boolean itemSelectable(Item item) {
            return item.isUpgradable();
        }

        @Override
        public void onSelect(Item item) {

        }

    };

    public WndOrbsManage() {

    }

    public static class ItemButton extends Component {

        protected NinePatch bg;
        protected ItemSlot slot;

        public Item item = null;

        @Override
        protected void createChildren() {
            super.createChildren();

            bg = Chrome.get( Chrome.Type.RED_BUTTON);
            add( bg );

            slot = new ItemSlot() {
                @Override
                protected void onPointerDown() {
                    bg.brightness( 1.2f );
                    Sample.INSTANCE.play( Assets.Sounds.CLICK );
                }
                @Override
                protected void onPointerUp() {
                    bg.resetColor();
                }
                @Override
                protected void onClick() {
                    ItemButton.this.onClick();
                }
            };
            slot.enable(true);
            add( slot );
        }

        protected void onClick() {}

        @Override
        protected void layout() {
            super.layout();

            bg.x = x;
            bg.y = y;
            bg.size( width, height );

            slot.setRect( x + 2, y + 2, width - 4, height - 4 );
        }

        public void item( Item item ) {
            slot.item( this.item = item );
        }

        public void clear(){
            slot.clear();
        }
    }
}