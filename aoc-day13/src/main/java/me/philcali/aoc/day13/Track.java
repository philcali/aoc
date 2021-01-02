package me.philcali.aoc.day13;

import java.util.Map;

import me.philcali.aoc.common.geometry.Point;
public enum Track {
    CART_FACING_RIGHT('>'),
    CART_FACING_LEFT('<'),
    CART_FACING_UP('^'),
    CART_FACING_DOWN('v'),
    LEFT_CORNER('/'),
    RIGHT_CORNER('\\'),
    HORIZONTAL('-'),
    VERTICAL('|'),
    INTERSECTION('+'),
    OPEN_SPACE(' ');

    private char layout;

    Track(final char layout) {
        this.layout = layout;
    }

    public char layout() {
        return this.layout;
    }

    public static Track fromLayout(final Map<Point, Track> map, final Point currentLocation, final char layout) {
        switch (layout) {
        case '/':
            return LEFT_CORNER;
        case '\\':
            return RIGHT_CORNER;
        case '<':
        case '>':
        case '-':
            return HORIZONTAL;
        case '^':
        case 'v':
        case '|':
            return VERTICAL;
        case '+':
            return INTERSECTION;
        case ' ':
        case '\n':
        default:
            return OPEN_SPACE;
        }
    }
}
