package com.techiness.collegecoordinator.helpers;

import com.techiness.collegecoordinator.enums.DepartmentType;
import com.techiness.collegecoordinator.enums.UserType;

public class StringToEnumUtils
{
    public static UserType getUserTypeFromUserId(String userId)
    {
        return UserType.valueOf(userId.substring(userId.indexOf("_")+1));
    }

    public static DepartmentType getDepartmentTypeFromDeptId(String deptId)
    {
        return DepartmentType.valueOf(deptId.substring(deptId.indexOf("#")+1));
    }
}
