package com.techiness.collegecoordinator.enums;

import com.techiness.collegecoordinator.utils.Range;

import java.util.stream.Stream;

public enum Grade
{
   O("91 - 100"),A_PLUS("81 - 90"),A("71 - 80"),B_PLUS("61 - 70"),B("51 - 60"),C("41 - 50"),FAIL("0 - 40");

   private final String markRange;

   Grade(String markRange)
   {
      this.markRange = markRange;
   }

   public String getMarkRange()
   {
      return markRange;
   }

   public static String[] getStringArrayOfValues()
   {
      return Stream.of(values()).map(Enum::name).toArray(String[]::new);
   }

   public static Grade getExactGradeFromMark(double mark)
   {
      Range markRange = new Range(91, 100, true);
      if(markRange.isNumberInRange(mark))
         return O;
      else if(markRange.resetBounds(81, 90).isNumberInRange(mark))
         return A_PLUS;
      else if(markRange.resetBounds(71, 80).isNumberInRange(mark))
         return A;
      else if(markRange.resetBounds(61, 70).isNumberInRange(mark))
         return B_PLUS;
      else if(markRange.resetBounds(51, 60).isNumberInRange(mark))
         return B;
      else if(markRange.resetBounds(41, 50).isNumberInRange(mark))
         return C;
      else
         return FAIL;
   }

   public static double getGradePointFromGrade(Grade grade)
   {
      switch (grade)
      {
         case O:
            return 10;
         case A_PLUS:
            return 9;
         case A:
            return 8;
         case B_PLUS:
            return 7;
         case B:
            return 6;
         case C:
            return 5;
         case FAIL:
            return 0;
      }
      return -1;
   }
}
