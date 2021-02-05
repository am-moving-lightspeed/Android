package com.oaoaoa.battleships.misc;


import android.annotation.SuppressLint;
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



public class MapManager {

    public static void initMapView(Context context, GridLayout mapView, Map map) {

        Resources       resources = context.getResources();
        Resources.Theme theme     = context.getTheme();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                ImageButton ibButton = new ImageButton(context);
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

                mapView.addView(ibButton);
            }
        }
    }


    @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
    public static void setShip(Context context, View view, Map map) {

        Resources       resources = context.getResources();
        Resources.Theme theme     = context.getTheme();

        String tag = view.getTag()
                         .toString();
        int i = tag.charAt(tag.length() - 2) - '0';
        int j = tag.charAt(tag.length() - 1) - '0';

        if (map.getCell(i, j) == Cell.EMPTY) {
            map.setCell(i, j, Cell.FILLED);
            view.setBackground(resources.getDrawable(R.drawable.cell_filled, theme));
        }
        else if (map.getCell(i, j) == Cell.FILLED) {
            map.setCell(i, j, Cell.EMPTY);
            view.setBackground(resources.getDrawable(R.drawable.cell_empty, theme));
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

            map.setCell(i, j, Cell.LOCKED);
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

        if (i < 0 || i > 9 || j < 0 || j > 9) {
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


    public void performShot(View view) {

        // TODO: implement
    }


}
