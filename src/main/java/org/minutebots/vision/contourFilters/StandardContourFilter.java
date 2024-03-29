package org.minutebots.vision.contourFilters;

import org.minutebots.vision.Range;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.RotatedRect;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * A standard contour filter.
 */
public class StandardContourFilter implements ContourFilter {

    private Range area;
    private Range fullness;
    private Range aspectRatio;
    private double imageArea;

    /**
     * Create a standard contour filter.
     *
     * @param area        The percent of the total image area the target's bounding rectangle takes up.
     * @param fullness    The percent of target's area to the target's bounding rectangle's area.
     * @param aspectRatio The aspect ratio of the target's bounding rectangle (width / height)
     */
    public StandardContourFilter(Range area, Range fullness, Range aspectRatio, double imageArea) {
        this.area = area;
        this.fullness = fullness;
        this.aspectRatio = aspectRatio;
        this.imageArea = imageArea;
    }

    @Deprecated
    public Range getArea() {
        return area;
    }

    @Deprecated
    public void setArea(Range area) {
        this.area = area;
    }

    @Deprecated
    public Range getFullness() {
        return fullness;
    }

    @Deprecated
    public void setFullness(Range fullness) {
        this.fullness = fullness;
    }

    @Deprecated
    public Range getAspectRatio() {
        return aspectRatio;
    }

    @Deprecated
    public void setAspectRatio(Range aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    @Deprecated
    public double getImageArea() {
        return imageArea;
    }

    @Deprecated
    public void setImageArea(double imageArea) {
        this.imageArea = imageArea;
    }

    @Override
    public List<MatOfPoint> filterContours(List<MatOfPoint> contours) {
        List<MatOfPoint> output = new ArrayList<>();
        for (MatOfPoint contour : contours) {

            final RotatedRect boundingRect = Imgproc.minAreaRect(new MatOfPoint2f(contour.toArray()));

            final double rectArea = boundingRect.size.area();

            final double targetAreaPercent = rectArea / imageArea * 100;

            if (!area.inRangeInclusive(targetAreaPercent)) {
                continue;
            }

            final double targetAspect = boundingRect.boundingRect().width / (double) boundingRect.boundingRect().height;

            if (!aspectRatio.inRangeInclusive(targetAspect)) {
                continue;
            }

            final double targetArea = Imgproc.contourArea(contour);

            final double targetFullness = targetArea / rectArea * 100;

            if (!fullness.inRangeInclusive(targetFullness)) {
                continue;
            }

            output.add(contour);
        }
        return output;
    }
}
