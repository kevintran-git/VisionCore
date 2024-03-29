package org.minutebots.vision.targetConverters;

import org.minutebots.vision.camera.CameraSettings;
import org.minutebots.vision.targeting.Target;
import org.opencv.core.MatOfPoint;

import java.util.LinkedList;
import java.util.List;

/**
 * Creates a combined target from targets. Uses the biggest two targets.
 */
public class DoubleTargetConverter implements ContourToTargetConverter {

    @Override
    public List<Target> convertContours(List<MatOfPoint> contours, CameraSettings cameraSettings) {
        List<Target> targets = new LinkedList<>();
        List<Target> singleTargets = new SingleTargetConverter().convertContours(contours, cameraSettings);

        if(singleTargets.size() < 2){
            return targets;
        }

        Target target1 = singleTargets.get(0);
        Target target2 = singleTargets.get(1);

        targets.add(TargetUtils.combineTargets(target1, target2, cameraSettings));

        return targets;
    }


}
