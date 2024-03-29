package org.minutebots.vision.filters;

import org.minutebots.vision.Range;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

/**
 * An RGB filter.
 */
public class RGBFilter implements TargetFilter {

    private Range red;
    private Range green;
    private Range blue;

    /**
     * Create an RGB filter.
     * @param red The red value from 0 - 255 inclusive.
     * @param green The green value from 0 - 255 inclusive.
     * @param blue The blue value from 0 - 255 inclusive.
     */
    public RGBFilter(Range red, Range green, Range blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Deprecated
    public Range getRed() {
        return red;
    }

    @Deprecated
    public void setRed(Range red) {
        this.red = red;
    }

    @Deprecated
    public Range getGreen() {
        return green;
    }

    @Deprecated
    public void setGreen(Range green) {
        this.green = green;
    }

    @Deprecated
    public Range getBlue() {
        return blue;
    }

    @Deprecated
    public void setBlue(Range blue) {
        this.blue = blue;
    }

    @Override
    public Mat filter(Mat image) {
        Mat out = new Mat();
        Scalar lower = new Scalar(blue.getLow(), green.getLow(), red.getLow());
        Scalar upper = new Scalar(blue.getHigh(), green.getHigh(), red.getHigh());
        Core.inRange(image, lower, upper, out);
        return out;
    }

}
