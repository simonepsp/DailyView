package ch.punkt.mp02.dailyview.widget;

public interface DrawableClickListener {

    public void onClick(DrawablePosition target);

    public static enum DrawablePosition {TOP, BOTTOM, LEFT, RIGHT}
}
