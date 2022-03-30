package com.techiness.collegecoordinator.enums;

public enum Grade
{
   O("91 - 100"),A_PLUS("81 - 90"),A("71 - 80"),B_PLUS("61 - 70"),B("51 - 60"),C("41 - 50"),FAIL("0 - 40");

   private final String markRange;

   Grade(String markRange)
   {
      this.markRange = markRange;
   }

   public Grade getExactGradeFromMark(double mark)
   {
      if(mark >= 91 && mark <= 100)
         return O;
      else if(mark >= 81 && mark <= 90)
         return A_PLUS;
      else if(mark >= 71 && mark <= 80)
         return A;
      else if(mark >= 61 && mark <= 70)
         return B_PLUS;
      else if(mark >= 51 && mark <= 60)
         return B;
      else if(mark >= 41 && mark <= 50)
         return C;
      else
         return FAIL;

   }
}
