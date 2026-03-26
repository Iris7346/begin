package mapper;

import entity.Student;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据访问层实现类：用HashMap模拟数据库，实现StudentMapper接口的方法
 * 职责：仅处理数据存储和读取，不做任何业务校验（校验交给Service层）
 */
public class StudentMapperImpl implements StudentMapper {

    // 用HashMap存储学生数据：key=学号（唯一），value=学生对象
    private final Map<String, Student> studentMap = new HashMap<>();

    @Override
    public boolean add(Student student) {
        // 若学号已存在，新增失败（返回false）；否则存入map（返回true）
        String studentId = student.getStudentId();
        if (studentMap.containsKey(studentId)) {
            return false;
        }
        studentMap.put(studentId, student);
        return true;
    }
    //TODO 请在此处补全操作HashMap数据库所需的方法的实现

    //根据学号查询学生
    @Override
    public Student findByStudentId(String studentId) {
        return studentMap.get(studentId);
        //get()方法：key存在时返回value，不存在返回null
    }

    //根据班级查询学生列表
    @Override
    public List<Student> findByClass(String className){
        //创建一个空列表存放结果（ArrayList可动态增加）
        List<Student> result = new ArrayList<Student>();
        //增强for循环，遍历集合
        for(Student student : studentMap.values()){
            //对于studentMap.values()这个集合中的每一个学生都执行一遍循环体
            if(student.getClassName()!=null && student.getClassName().equals(className)){
                //符合要求，添加到result列表中
                result.add(student);//add()是List接口的方法
            }
        }
        return result;
    }

    //查询所有学生
    @Override
    public List<Student> findAll(){
        //将HashMap中的所有学生对象转换成List返回
        //new ArrayList<>(集合)可以把任何集合转换成ArrayList
        return new ArrayList<>(studentMap.values());
    }

    //修改学生信息
    @Override
    public boolean update(Student student){//方法调用时需要传入一个学生对象
        String studentId = student.getStudentId();
        //学号不存在
        if(!studentMap.containsKey(studentId)){
            return false;
        }
        //学号存在，替换
        studentMap.put(studentId, student);//V put(K key, V value)
        //不需要接收返回值
        return true;
    }

    //删除学生
    @Override
    public boolean delete(String studentId){
        //remove()：如果key存在，删除并返回被删除的value
        //如果key不存在，返回null
        Student removed = studentMap.remove(studentId);//删除该键值对
        //removed不为null则删除成功
        return removed != null;
    }

}