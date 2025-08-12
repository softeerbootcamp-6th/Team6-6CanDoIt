package com.softeer.recommend;

import com.softeer.recommend.comment.PrecipitationComment;
import com.softeer.recommend.comment.SnowAccumulationComment;
import com.softeer.recommend.comment.TemperatureComment;
import com.softeer.recommend.comment.WindSpeedComment;

import java.util.ArrayList;
import java.util.List;

public final class CommentProvider {
    private CommentProvider() {}


    public static String provideComment(
            String precipitation,
            String snowAccumulation,
            double windSpeed,
            double temperatureDiff
    ) {
        List<Comment> comments = new ArrayList<>();
        comments.add(PrecipitationComment.findPrecipitationComment(precipitation));
        comments.add(SnowAccumulationComment.findsnowAccumulationComment(snowAccumulation));
        comments.add(WindSpeedComment.findWindSpeedComment(windSpeed));
        comments.add(TemperatureComment.findTemperatureComment(temperatureDiff));

        comments.sort((c1, c2) -> {
            TotalLevel left = c1.totalLevel();
            TotalLevel right = c2.totalLevel();

            if(left.measurementLevel() == right.measurementLevel()) {
                return Integer.compare(left.typeLevel().getLevel(), right.typeLevel().getLevel());
            }
            return  Integer.compare(right.measurementLevel().getLevel(), left.measurementLevel().getLevel());
        });

        Comment comment = comments.get(0);
        return comment.comment();
    }
}
