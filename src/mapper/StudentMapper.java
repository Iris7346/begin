package mapper;

import entity.Student;
import java.util.List;

/**
 * 数据访问层接口：定义学生数据的CRUD操作规范
 * 职责：仅声明数据操作方法，不涉及业务逻辑和实现
 */
public interface StudentMapper {

    /**
     * 新增学生
     * @param student 学生对象（包含学号等信息）
     * @return 新增成功返回true，失败返回false（如学号已存在时返回false）
     */
    boolean add(Student student);

    //TODO 请在此处补全操作HashMap所需的方法

    Student findByStudentId(String studentId);//根据学号查询学生
    //参数：学号，返回值：学生对象（找不到返回null）

    List<Student> findByClass(String className);//根据班级查询学生列表
    //参数：班级名，返回值：该班级的所有学生

    List<Student> findAll();//查询所有学生
    //返回值：所有学生列表

    boolean update(Student student);//修改学生信息
    //参数：要修改的学生对象，返回值：是否修改成功

    boolean delete(String studentId);//删除学生
    //参数：学号，返回值：是否删除成功

}