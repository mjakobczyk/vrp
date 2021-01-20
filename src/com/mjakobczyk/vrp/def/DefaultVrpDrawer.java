package com.mjakobczyk.vrp.def;

import com.mjakobczyk.vrp.def.model.DefaultVrpOutput;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpOutput;
import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.service.VrpDrawer;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default implementation of {@link VrpDrawer}.
 */
public class DefaultVrpDrawer implements VrpDrawer {

    private static final Logger LOG = Logger.getLogger(String.valueOf(DefaultVrpDrawer.class));

    @Override
    public void draw(final DefaultVrpOutput defaultVrpOutput) {
        // TODO
    }

    @Override
    public void draw(final DynamicVrpOutput dynamicVrpOutput) {
        final int VRP_Y = 800;
        final int VRP_INFO = 200;
        final int X_GAP = 600;
        final int MARGIN = 30;
        final int MARGIN_NODE = 1;
        final int XXX = VRP_INFO + X_GAP;
        final int YYY = VRP_Y;

        final Random random = new Random();
        final BufferedImage output = new BufferedImage(XXX, YYY, BufferedImage.TYPE_INT_RGB);
        final Graphics2D g = output.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, XXX, YYY);
        g.setColor(Color.BLACK);

        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;

        final List<List<Location>> result = dynamicVrpOutput.getListOfListsOfLocations();
        // 1 because deposit is being count as the first location
        int locationsCount = 1;
        // Find out min and max coordinates
        for (final List<Location> list : result) {
            locationsCount += list.size();
            locationsCount -= 2;

            for (final Location location : list) {
                if (location.getCoordinates().getCoordinateX() > maxX) {
                    maxX = location.getCoordinates().getCoordinateX();
                }
                if (location.getCoordinates().getCoordinateX() < minX) {
                    minX = location.getCoordinates().getCoordinateX();
                }
                if (location.getCoordinates().getCoordinateY() > maxY) {
                    maxY = location.getCoordinates().getCoordinateY();
                }
                if (location.getCoordinates().getCoordinateY() < minY) {
                    minY = location.getCoordinates().getCoordinateY();
                }
            }
        }

        int mX = XXX - 2 * MARGIN;
        int mY = VRP_Y - 2 * MARGIN;

        int A, B;
        if ((maxX - minX) > (maxY - minY)) {
            A = mX;
            B = (int)((double)(A) * (maxY - minY) / (maxX - minX));
            if (B > mY)
            {
                B = mY;
                A = (int)((double)(B) * (maxX - minX) / (maxY - minY));
            }
        } else {
            B = mY;
            A = (int)((double)(B) * (maxX - minX) / (maxY - minY));
            if (A > mX)
            {
                A = mX;
                B = (int)((double)(A) * (maxY - minY) / (maxX - minX));
            }
        }

        // Iterate over all routes
        for (final List<Location> list : result) {
            final int Rval = random.nextInt(256);
            final int Gval = random.nextInt(256);
            final int Bval = random.nextInt(256);

            Color color = new Color(Rval,Gval,Bval);
            g.setColor(color);

            // For every route
            for (int i = 1; i < list.size(); ++i) {
                // In this case single node is a Location
                final Location locationFrom = list.get(i-1);

                int ii1 = (int) ((double) (A) * ((locationFrom.getCoordinates().getCoordinateX() - minX) /
                        (maxX - minX) - 0.5) + (double) mX / 2) + MARGIN;
                int jj1 = (int) ((double) (B) * (0.5 - (locationFrom.getCoordinates().getCoordinateY() - minY) /
                        (maxY - minY)) + (double) mY / 2) + MARGIN;

                final Location locationTo = list.get(i);
                int ii2 = (int) ((double) (A) * ((locationTo.getCoordinates().getCoordinateX() - minX) /
                        (maxX - minX) - 0.5) + (double) mX / 2) + MARGIN;
                int jj2 = (int) ((double) (B) * (0.5 - (locationTo.getCoordinates().getCoordinateY() - minY) /
                        (maxY - minY)) + (double) mY / 2) + MARGIN;

                g.drawLine(ii1, jj1, ii2, jj2);
            }
        }

        g.setColor(Color.BLACK);

        // Iterate over all routes
        for (final List<Location> list : result) {
            // For every route
            for (int i = 0; i < list.size(); ++i) {
                // In this case single node is a Location
                final Location location = list.get(i);

                int ii = (int) ((double) (A) * ((location.getCoordinates().getCoordinateX()  - minX) /
                        (maxX - minX) - 0.5) + (double) mX / 2) + MARGIN;
                int jj = (int) ((double) (B) * (0.5 - (location.getCoordinates().getCoordinateY() - minY) /
                        (maxY - minY)) + (double) mY / 2) + MARGIN;
                if (i != 0) {
                    g.fillOval(ii - 3 * MARGIN_NODE, jj - 3 * MARGIN_NODE, 6 * MARGIN_NODE, 6 * MARGIN_NODE); //2244
                } else {
                    g.fillRect(ii - 3 * MARGIN_NODE, jj - 3 * MARGIN_NODE, 6 * MARGIN_NODE, 6 * MARGIN_NODE);  //4488
                }
            }
        }

        final String textToDraw = "Locations: " +  locationsCount;
        g.drawString(textToDraw, 10, 10);
        // TODO: display total cost as well

        final LocalDateTime ldt = LocalDateTime.now();
        final String time = ldt.getYear() + "-" + ldt.getMonthValue() + "-" + ldt.getDayOfMonth() + "-"
                + ldt.getHour() + "-" + ldt.getMinute() + "-" + ldt.getSecond();
        final String fileName = "results/DVRP-" + time + ".png";

        final File f = new File(fileName);
        try {
            ImageIO.write(output, "PNG", f);
        } catch (IOException ex) {
              LOG.log(Level.SEVERE, "Exception occurred when saving drawing results to the output file.", ex);
        }
    }

}
