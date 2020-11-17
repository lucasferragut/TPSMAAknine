import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum PointCardinal {
        NORD,
        SUD,
        EST,
        OUEST;

        private static final List<PointCardinal> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        public static PointCardinal randomDirection(List<PointCardinal> lDirections) {
            PointCardinal pc;
            do{
                pc = VALUES.get(RANDOM.nextInt(SIZE));
            }while(lDirections.contains(pc));
            return pc;
        }

        public static int getSize(){
            return SIZE;
        }
}
