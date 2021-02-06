package com.oaoaoa.battleships.misc;


import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;

import androidx.core.content.res.ResourcesCompat;

import com.oaoaoa.battleships.R;
import com.oaoaoa.battleships.misc.exceptions.InvalidMapException;
import com.oaoaoa.battleships.models.Cell;
import com.oaoaoa.battleships.models.Map;
import com.oaoaoa.battleships.models.ShipOrientation;

import java.util.Random;



public class MapManager {

    public static void initMapView(final Context context, GridLayout mapView, final Map map) {

        Resources       resources = context.getResources();
        Resources.Theme theme     = context.getTheme();

        mapView.removeAllViews();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                ImageButton ibButton = new ImageButton(context);
                // TODO: Remove tag if redundant
                ibButton.setTag("imageButton_mapEditor_cell" + i + j);
                ibButton.setMaxWidth(32);
                ibButton.setMaxHeight(32);

                if (map.getCell(i, j) == Cell.FILLED) {
                    ibButton.setBackground(
                        ResourcesCompat.getDrawable(resources, R.drawable.cell_filled, theme)
                    );
                }
                else {
                    ibButton.setBackground(
                        ResourcesCompat.getDrawable(resources, R.drawable.cell_empty, theme)
                    );
                }

                ibButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        MapManager.setShip(context, view, map);
                    }
                });

                mapView.addView(ibButton);
            }
        }
    }


    @SuppressWarnings("ConditionalBreakInInfiniteLoop")
    public static Map generateMap() {

        Random random   = new Random();
        Map    mapDraft = new Map();

        ShipOrientation orientation;
        int             x, y;

        try {
            for (int size = 4, shipsAmount = 1; size > 0; size--, shipsAmount++) {

                for (int k = 0; k < shipsAmount; k++) {

                    orientation = randomizeOrientation();
                    while (true) {
                        if (orientation == ShipOrientation.HORIZONTAL) {
                            x = random.nextInt(11 - size);
                            y = random.nextInt(10);
                        }
                        else {
                            x = random.nextInt(10);
                            y = random.nextInt(11 - size);
                        }

                        if (canBeDeployed(mapDraft, orientation, size, x, y)) {
                            break;
                        }
                    }

                    deployShip(mapDraft, orientation, size, x, y);
                    resolveShipArea(mapDraft, orientation, size, x, y);
                }
            }
        }
        catch (InvalidMapException exception) {
            // No exception expected.
            return null;
        }

        return draftToMap(mapDraft);
    }


    public static void setShip(Context context, View view, Map map) {

        Resources       resources = context.getResources();
        Resources.Theme theme     = context.getTheme();

        String tag = view.getTag()
                         .toString();
        int i = tag.charAt(tag.length() - 2) - '0';
        int j = tag.charAt(tag.length() - 1) - '0';

        if (map.getCell(i, j) == Cell.EMPTY) {

            map.setCell(i, j, Cell.FILLED);
            view.setBackground(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.cell_filled,
                    theme
                )
            );
        }
        else if (map.getCell(i, j) == Cell.FILLED) {

            map.setCell(i, j, Cell.EMPTY);
            view.setBackground(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.cell_empty,
                    theme
                )
            );
        }
    }


    /**
     * Method is looking for ships as going along the map left-to-right, top-to-bottom,
     * so any ship's cell (shipCell) that is found is going to be ship's start.
     * Other cells of this exact ship can be found either to the right or below shipCell.
     */
    public static boolean isValid(Map map) {

        // Amount of ships.
        int x4Ships = 1;
        int x3Ships = 2;
        int x2Ships = 3;
        int x1Ships = 4;

        map = new Map(map);

        try {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {

                    if (map.getCell(i, j) == Cell.FILLED) {

                        ShipOrientation shipOrientation = resolveShipOrientation(map, i, j);
                        int             shipSize        = resolveShipSize(map, shipOrientation, i, j);
                        resolveShipArea(map, shipOrientation, shipSize, i, j);

                        if (shipSize == 4) {
                            x4Ships--;
                        }
                        else if (shipSize == 3) {
                            x3Ships--;
                        }
                        else if (shipSize == 2) {
                            x2Ships--;
                        }
                        else {
                            x1Ships--;
                        }
                    }
                }
            }

            if (x4Ships != 0 || x3Ships != 0 || x2Ships != 0 || x1Ships != 0) {
                throw new InvalidMapException();
            }

            return true;
        }
        catch (InvalidMapException exception) {
            return false;
        }
    }


    // region Map generation methods
    private static ShipOrientation randomizeOrientation() {

        return new Random().nextInt(11) >= 5 ?
               ShipOrientation.HORIZONTAL :
               ShipOrientation.VERTICAL;
    }


    private static void deployShip(Map map,
                                   ShipOrientation orientation,
                                   int size,
                                   int i,
                                   int j) {

        int count = 0;
        while (i < 10 &&
               j < 10 &&
               count < size) {

            map.setCell(i, j, Cell.OCCUPIED);
            count++;

            if (orientation == ShipOrientation.HORIZONTAL) {
                j++;
            }
            else {
                i++;
            }
        }
    }


    private static boolean canBeDeployed(Map map,
                                         ShipOrientation orientation,
                                         int size,
                                         int i,
                                         int j) {

        int count = 0;
        while (count < size) {
            count++;

            if (i >= 10 ||
                j >= 10 ||
                map.getCell(i, j) == Cell.LOCKED ||
                map.getCell(i, j) == Cell.OCCUPIED) {

                return false;
            }

            if (orientation == ShipOrientation.HORIZONTAL) {
                j++;
            }
            else {
                i++;
            }
        }

        return true;
    }


    private static Map draftToMap(Map draft) {

        Map map = new Map();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (draft.getCell(i, j) == Cell.OCCUPIED) {
                    map.setCell(i, j, Cell.FILLED);
                }
            }
        }

        return map;
    }
    // endregion


    // region Map validation methods
    private static ShipOrientation resolveShipOrientation(Map map, int i, int j) {

        if (j + 1 < 10 &&
            map.getCell(i, j + 1) == Cell.FILLED) {

            return ShipOrientation.HORIZONTAL;
        }
        else {
            return ShipOrientation.VERTICAL;
        }
    }


    private static int resolveShipSize(Map map, ShipOrientation orientation, int i, int j) {

        int count = 0;
        while (i < 10 &&
               j < 10 &&
               map.getCell(i, j) == Cell.FILLED) {

            map.setCell(i, j, Cell.OCCUPIED);
            count++;

            if (orientation == ShipOrientation.HORIZONTAL) {
                j++;
            }
            else {
                i++;
            }

            // Ship cannot be longer than 4 cells.
            if (count == 4) {
                break;
            }
        }

        return count;
    }


    /**
     * Locks area around the ship, so that no other ship can locate
     * nearby at a distance of 1 cell. If it does, throws an exception to
     * indicate, that the map is invalid.
     */
    private static void resolveShipArea(Map map,
                                        ShipOrientation orientation,
                                        int size,
                                        int i,
                                        int j) throws
                                               InvalidMapException {

        int offset = -1;
        if (orientation == ShipOrientation.HORIZONTAL) {
            while (offset <= size) {

                lockCell(map, i, j + offset);
                lockCell(map, i - 1, j + offset);
                lockCell(map, i + 1, j + offset);
                offset++;
            }
        }
        else {
            while (offset <= size) {

                lockCell(map, i + offset, j);
                lockCell(map, i + offset, j - 1);
                lockCell(map, i + offset, j + 1);
                offset++;
            }
        }
    }


    @SuppressWarnings("UnnecessaryReturnStatement")
    private static void lockCell(Map map, int i, int j) throws
                                                        InvalidMapException {

        if (i < 0 || i > 9 ||
            j < 0 || j > 9 ||
            map.getCell(i, j) == Cell.OCCUPIED) {

            return;
        }
        // Means that another ship is too close.
        else if (map.getCell(i, j) == Cell.FILLED) {
            throw new InvalidMapException();
        }
        else {
            map.setCell(i, j, Cell.LOCKED);
        }
    }
    // endregion


    public void performShot(View view) {

        // TODO: implement
    }


}
