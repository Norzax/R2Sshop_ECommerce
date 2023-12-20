//package com.aclass.r2sshop_ecommerce.configurations;
//
//public class Search {
//    private StringBuilder getSqlSearch(ClassRoomDTO classRoomDTO) {
//        String lang = LocaleContextHolder.getLocale().getLanguage();
//        StringBuilder sql = new StringBuilder(
//                "SELECT cr.id, cr.created_time, cr.created_name,cr.update_time, " +
//                        "cr.update_name, cr.name, cr.code, cr.dept_id, cr.grade_level, " +
//                        "cr.years, cr.specialize, cr.teacher_id, cr.status, ");
//
//        if ("la".equals(lang)) {
//            sql.append("b.name_la as ten_khoi, ");
//        } else if ("en".equals(lang)) {
//            sql.append("b.name_en as ten_khoi, ");
//        } else {
//            sql.append("b.name as ten_khoi, ");
//        }
//
//        sql.append("d.name as ten_khoa, t.full_name as ten_gv, " +
//
//                "s.name as ten_mon, t.code as teacherCode, d.code as deptCode, s.code as subjectCode " +
//                "FROM class_room as cr " +
//                "JOIN grade_level as b ON cr.grade_level = b.id " +
//                "JOIN departments as d ON d.id = cr.dept_id " +
//                "JOIN teachers as t ON t.id= cr.teacher_id " +
//                "LEFT JOIN subjects as s ON s.id=cr.specialize " +
//                " WHERE cr.years = '" +
//                classRoomDTO.getYears() +
//                "' AND 1=1 "
//        );
//
//        if (null != classRoomDTO.getGradeLevel()) {
//            sql.append(" AND cr.grade_level = ");
//            sql.append(classRoomDTO.getGradeLevel());
//        }
//
//        if (null != classRoomDTO.getDeptId()) {
//            sql.append(" AND cr.dept_id = ");
//            sql.append(classRoomDTO.getDeptId());
//        }
//
//        if (StringUtils.isNotBlank(classRoomDTO.getName())) {
//            sql.append(
//                    " AND ( UPPER(cr.name) LIKE UPPER('%" +
//                            validateKeySearch(classRoomDTO.getName()) +
//                            "%') escape '&' " +
//                            "OR UPPER(cr.code) LIKE UPPER('%" +
//                            validateKeySearch(classRoomDTO.getName()) +
//                            "%') escape '&' )"
//            );
//        }
//        return sql;
//    }
//}
