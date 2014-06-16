package com.afollestad.cardsui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;

/**
 * Represents a single card displayed in a {@link CardAdapter}.
 *
 * @author Aidan Follestad (afollestad)
 */
public class Card implements CardBase<Card> {

    private long id;
    private static final long serialVersionUID = 7548618898682727465L;
    private CharSequence title;
    private CharSequence content;
    private boolean isHeader;
    private int mPopupMenu;
    private CardMenuListener<Card> mPopupListener;
    private boolean isClickable = true;
    private Object mTag;
    private Drawable mThumbnail;
    private int mLayout;

    protected Card() {
    }

    protected Card(CharSequence title, CharSequence subtitle, boolean isHeader) {
        this(title, subtitle);
        this.isHeader = isHeader;
    }

    public Card(CharSequence title) {
        this.title = title;
    }

    public Card(CharSequence title, CharSequence content) {
        this(title);
        this.content = content;
    }

    public Card(Context context, int titleRes) {
        this(context.getString(titleRes));
    }

    public Card(Context context, String title, int contentRes) {
        this(title, context.getString(contentRes));
    }

    public Card(Context context, int titleRes, String content) {
        this(context.getString(titleRes), content);
    }

    public Card(Context context, int titleRes, int contentRes) {
        this(context.getString(titleRes), context.getString(contentRes));
    }

    @Override
    public void setSilkId(long id) {
        this.id = id;
    }

    @Override
    public long getSilkId() {
        return id;
    }

    @Override
    public boolean equalTo(Card other) {
        return id == other.id;
    }

    @Override
    public CharSequence getTitle() {
        return title;
    }

    @Override
    public CharSequence getContent() {
        return content;
    }

    @Override
    public boolean isHeader() {
        return isHeader;
    }

    @Override
    public boolean isClickable() {
        return isClickable;
    }

    @Override
    public Object getTag() {
        return mTag;
    }

    @Override
    public int getPopupMenu() {
        return mPopupMenu;
    }

    @Override
    public CardHeader.ActionListener getActionCallback() {
        return null;
    }

    @Override
    public String getActionTitle() {
        return null;
    }

    @Override
    public CardMenuListener getPopupListener() {
        return mPopupListener;
    }

    @Override
    public Drawable getThumbnail() {
        return mThumbnail;
    }

    @Override
    public int getLayout() {
        return mLayout;
    }

    /**
     * Sets whether or not the card is clickable, setting it to false will turn the card's list selector off
     * and the list's OnItemClickListener will not be called.
     * <p/>
     * This <b>does not</b> override the isClickable value set to a {@link CardAdapter}, however.
     */
    public Card setClickable(boolean clickable) {
        isClickable = clickable;
        return this;
    }

    /**
     * Sets a tag of any type that can be used to keep track of cards.
     */
    public Card setTag(Object tag) {
        mTag = tag;
        return this;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
    }

    public void setContent(CharSequence content) {
        this.content = content;
    }

    /**
     * Sets an optional thumbnail drawable that's displayed on the left side of the card.
     *
     * @param drawable The drawable to be displayed as a thumbnail.
     */
    public Card setThumbnail(Drawable drawable) {
        mThumbnail = drawable;
        return this;
    }

    /**
     * Sets a custom layout to be used for this card. This will override custom layouts set to the {@link CardAdapter}.
     *
     * @param layoutRes The resource ID of a layout containing views with the same IDs as the layout contained in this library.
     */
    public Card setLayout(int layoutRes) {
        mLayout = layoutRes;
        return this;
    }

    /**
     * Sets an optional thumbnail image resource that's displayed on the left side of the card.
     *
     * @param context The context used to resolve the drawable resource ID.
     * @param resId   The resource ID of the drawable to use as a thumbnail.
     */
    public Card setThumbnail(Context context, int resId) {
        setThumbnail(context.getResources().getDrawable(resId));
        return this;
    }

    /**
     * Sets an optional thumbnail image that's displayed on the left side of the card.
     *
     * @param context The context used to convert the Bitmap to a Drawable.
     * @param bitmap  The bitmap thumbnail to be displayed.
     */
    public Card setThumbnail(Context context, Bitmap bitmap) {
        setThumbnail(new BitmapDrawable(context.getResources(), bitmap));
        return this;
    }

    /**
     * Sets a unique popup menu for the card, this will override any popup menu set for the {@link CardAdapter}
     * that the card is displayed in.
     * <p/>
     * Setting the menu resource to -1 disables the menu for this card, and will override any popup menu set
     * to the {@link CardAdapter}.
     *
     * @param menuRes  The menu resource ID to use for the card's popup menu.
     * @param listener A listener invoked when an option in the popup menu is tapped by the user.
     */
    public Card setPopupMenu(int menuRes, CardMenuListener<Card> listener) {
        mPopupMenu = menuRes;
        mPopupListener = listener;
        return this;
    }

    public interface CardMenuListener<ItemType> {
        public void onMenuItemClick(ItemType card, MenuItem item);
    }
}