package ru.pl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InputData {
    private Sphere sphere;
    private double[][] line;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Sphere {
        private double[] center;
        private double radius;
    }
}
