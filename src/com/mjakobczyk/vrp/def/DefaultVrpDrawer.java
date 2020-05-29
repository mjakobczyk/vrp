package com.mjakobczyk.vrp.def;

import com.mjakobczyk.vrp.def.model.DefaultVrpOutput;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpOutput;
import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.service.VrpDrawer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * Default implementation of {@link VrpDrawer}.
 */
public class DefaultVrpDrawer implements VrpDrawer {

    @Override
    public void draw(DefaultVrpOutput defaultVrpOutput) {
        // TODO
    }

    @Override
    public void draw(final DynamicVrpOutput dynamicVrpOutput) {
        int VRP_Y = 800;
        int VRP_INFO = 200;
        int X_GAP = 600;
        int margin = 30;
        int marginNode = 1;

        int XXX = VRP_INFO + X_GAP;
        int YYY = VRP_Y;

        Random random = new Random();

        BufferedImage output = new BufferedImage(XXX, YYY, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = output.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, XXX, YYY);
        g.setColor(Color.BLACK);


        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;

        final List<List<Location>> result = dynamicVrpOutput.getListOfListsOfLocations();
        int locationsCount = 1; // 1 because deposit is count as the first one
        // Find out min and max coordinates
        for (final List<Location> list : result) {
            locationsCount += list.size();
            locationsCount -= 2; // Minus 2

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

        int mX = XXX - 2 * margin;
        int mY = VRP_Y - 2 * margin;

        int A, B;
        if ((maxX - minX) > (maxY - minY))
        {
            A = mX;
            B = (int)((double)(A) * (maxY - minY) / (maxX - minX));
            if (B > mY)
            {
                B = mY;
                A = (int)((double)(B) * (maxX - minX) / (maxY - minY));
            }
        }
        else
        {
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

                int ii1 = (int) ((double) (A) * ((locationFrom.getCoordinates().getCoordinateX() - minX) / (maxX - minX) - 0.5) + (double) mX / 2) + margin;
                int jj1 = (int) ((double) (B) * (0.5 - (locationFrom.getCoordinates().getCoordinateY() - minY) / (maxY - minY)) + (double) mY / 2) + margin;

                final Location locationTo = list.get(i);
                int ii2 = (int) ((double) (A) * ((locationTo.getCoordinates().getCoordinateX() - minX) / (maxX - minX) - 0.5) + (double) mX / 2) + margin;
                int jj2 = (int) ((double) (B) * (0.5 - (locationTo.getCoordinates().getCoordinateY() - minY) / (maxY - minY)) + (double) mY / 2) + margin;

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

                int ii = (int) ((double) (A) * ((location.getCoordinates().getCoordinateX()  - minX) / (maxX - minX) - 0.5) + (double) mX / 2) + margin;
                int jj = (int) ((double) (B) * (0.5 - (location.getCoordinates().getCoordinateY() - minY) / (maxY - minY)) + (double) mY / 2) + margin;
                if (i != 0) {
                    g.fillOval(ii - 3 * marginNode, jj - 3 * marginNode, 6 * marginNode, 6 * marginNode); //2244
                    // Consider drawing ID on every node
//                    String id = Integer.toString(ID);
//                    g.drawString(id, ii + 6 * marginNode, jj + 6 * marginNode); //88
                } else {
                    g.fillRect(ii - 3 * marginNode, jj - 3 * marginNode, 6 * marginNode, 6 * marginNode);  //4488
//                    String id = Integer.toString(n.NodeId);
//                    g.drawString(id, ii + 6 * marginNode, jj + 6 * marginNode); //88
                }
            }
        }

        String cst = "Locations: " +  locationsCount; // TODO: add cost
        g.drawString(cst, 10, 10);

        final LocalDateTime ldt = LocalDateTime.now();
        final String time = ldt.getYear() + "-" + ldt.getMonthValue() + "-" + ldt.getDayOfMonth() + "-"
                + ldt.getHour() + "-" + ldt.getMinute() + "-" + ldt.getSecond();
        final String fileName = "results/DVRP-" + time + ".png";

        File f = new File(fileName);
        try
        {
            ImageIO.write(output, "PNG", f);
        } catch (IOException ex) {
            //  Logger.getLogger(s.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
